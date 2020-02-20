package com.advertiseme.nearby;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.location.Location;

import com.advertiseme.startpanel.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Projection;


/**
 * Fixes bug with some phone's location overlay class (ie Droid X).
 * Essentially, it attempts to use the default MyLocationOverlay class,
 * but if it fails, we override the drawMyLocation method to provide
 * an icon and accuracy circle to mimic showing user's location.  Right
 * now the icon is a static image.  If you want to have it animate, modify
 * the drawMyLocation method.
 */
public class FixedMyLocationOverlay extends MyLocationOverlay {
	
	private boolean bugged = false;
	
	private Drawable drawable;
	private Paint accuracyPaint;
	private Point center;
	private Point left;
	private int width;
	private int height;
	
	public static GeoPoint GEO_MY_LOCATION;
	public static int CIRCLE_RADIUS_METERS=5000; // 5KMs
	
	public FixedMyLocationOverlay(Context context, MapView mapView) {
		super(context, mapView);
	}
	
	@Override
	protected void drawMyLocation(Canvas canvas, MapView mapView,
			Location lastFix, GeoPoint myLocation, long when) {
		if(bugged) {
			try {
				super.drawMyLocation(canvas, mapView, lastFix, myLocation, when);
			} catch (Exception e) {
				// we found a buggy phone, draw the location icons ourselves
				bugged = true;
			}
		}
		
		if(!bugged) {
			if(drawable == null) {
				
				accuracyPaint = new Paint();
				accuracyPaint.setAntiAlias(true);
				accuracyPaint.setStrokeWidth(2.0f);
				
				drawable = mapView.getContext().getResources().getDrawable(R.drawable.ic_maps_indicator_current_position);
				width = drawable.getIntrinsicWidth();
				height = drawable.getIntrinsicHeight();
				center = new Point();
				left = new Point();
			}
			
			Projection projection = mapView.getProjection();
			double latitude = lastFix.getLatitude();
			double longitude = lastFix.getLongitude();
			float accuracy = lastFix.getAccuracy();
			
			float[] result = new float[1];
			
			Location.distanceBetween(latitude, longitude, latitude, longitude + 2, result);
			float longitudeLineDistance = result[0];
			
			       
			GeoPoint leftGeo = new GeoPoint((int)(latitude*1e6), (int)((longitude-accuracy/longitudeLineDistance)*1e6));
			projection.toPixels(leftGeo, left);
			projection.toPixels(myLocation, center);
			int radius = center.x - left.x;
			radius=metersToRadius(myLocation.getLatitudeE6() / 1000000,mapView,CIRCLE_RADIUS_METERS);
			
			accuracyPaint.setColor(0xff6666ff);
			accuracyPaint.setStyle(Style.STROKE);
			canvas.drawCircle(center.x, center.y, radius, accuracyPaint);
			
			accuracyPaint.setColor(0x186666ff);
			accuracyPaint.setStyle(Style.FILL);
			canvas.drawCircle(center.x, center.y, radius, accuracyPaint);
			
			drawable.setBounds(center.x - width/2, center.y - height/2, center.x + width/2, center.y + height/2);
			drawable.draw(canvas);
		}
	}
	// Function to return the subsequent pixel value meters in the projection
	public int metersToRadius(double latitude,MapView mapView,float meters) {
		return (int) (mapView.getProjection().metersToEquatorPixels(meters) * (1 / Math
				.cos(Math.toRadians(latitude))));
	}
	@Override
	public synchronized boolean draw(Canvas canvas, MapView mapView,
			boolean shadow, long when) {
		if(drawable == null) {
			
			accuracyPaint = new Paint();
			accuracyPaint.setAntiAlias(true);
			accuracyPaint.setStrokeWidth(2.0f);
			
			drawable = mapView.getContext().getResources().getDrawable(R.drawable.ic_maps_indicator_current_position);
			width = drawable.getIntrinsicWidth();
			height = drawable.getIntrinsicHeight();
			center = new Point();
			
		}
		
		Projection projection = mapView.getProjection();
		projection.toPixels(GEO_MY_LOCATION, center);
		int radius=metersToRadius(GEO_MY_LOCATION.getLatitudeE6() / 1000000,mapView,CIRCLE_RADIUS_METERS);
		
		accuracyPaint.setColor(0xff6666ff);
		accuracyPaint.setStyle(Style.STROKE);
		canvas.drawCircle(center.x, center.y, radius, accuracyPaint);
		
		accuracyPaint.setColor(0x186666ff);
		accuracyPaint.setStyle(Style.FILL);
		canvas.drawCircle(center.x, center.y, radius, accuracyPaint);
		
		drawable.setBounds(center.x - width/2, center.y - height/2, center.x + width/2, center.y + height/2);
		drawable.draw(canvas);
		return true;
	}
}
