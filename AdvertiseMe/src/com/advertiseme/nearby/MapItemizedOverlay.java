package com.advertiseme.nearby;

import java.util.ArrayList;
import java.util.List;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.advertiseme.advertisement.AdvertisementActivity;
import com.advertiseme.startpanel.R;

public class MapItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	public static  List<GeoPoint> arrGeoPoint = new ArrayList<GeoPoint>();
	public static MapView mapView;
	public static GeoPoint GEO_MY_LOCATION;
	private Context mContext;
	public static MyMapLocationActivity context;
	private ProgressDialog dialog;
	private  static int  geoPointIndex;
	
	public MapItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	public MapItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;

	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

	static String advertID="";
	
	@Override
	protected boolean onTap(final int index) {

		mapView.getController().animateTo(arrGeoPoint.get(index));
		//mapView.getController().setZoom(16);
		
		OverlayItem item = mOverlays.get(index);
		String itemTitle = item.getTitle();
		String itemDescription = item.getSnippet();		
					
		advertID=itemTitle;
		//Toast.makeText(mContext, "Item Title : "+itemTitle+"\n"+"Item Address"+itemDescription, Toast.LENGTH_LONG).show();
		viewPopupWindow(index,itemTitle,itemDescription);		
		
		return true;

	}
	
	private void viewPopupWindow(int index,String itemTitle,String itemDescription ){
		
		
		geoPointIndex=index;
		
		Point point = new Point();
		Projection projection = mapView.getProjection();
		projection.toPixels(arrGeoPoint.get(index), point);
		
		int popupWidth = 350;
		int popupHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
		// Inflate the popup_layout.xml
		LinearLayout viewGroup = (LinearLayout) context
				.findViewById(R.id.popup);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.popup_window, viewGroup);

		// Creating the PopupWindow
		final PopupWindow popup = new PopupWindow(context);
		popup.setContentView(layout);
		popup.setWidth(popupWidth);
		popup.setHeight(popupHeight);
		popup.setFocusable(true);

		// Some offset to align the popup a bit to the right, and a bit down,
		// relative to button's position.
		int OFFSET_X = 30;
		int OFFSET_Y = 100;

		// Clear the default translucent background
		popup.setBackgroundDrawable(new BitmapDrawable());

		// Displaying the popup at the specified location, + offsets.
		popup.showAtLocation(layout, Gravity.NO_GRAVITY, point.x + OFFSET_X, point.y
				+ OFFSET_Y);
		
		TextView txtTitle = (TextView) layout.findViewById(R.id.txtTitle);
		txtTitle.setText(itemDescription);
		
		TextView txtDescription = (TextView) layout.findViewById(R.id.txtDescription);
		txtDescription.setText("Go and check the item");
		
		Button windowClose = (Button) layout.findViewById(R.id.btnWindowClose);
		windowClose.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				popup.dismiss();
			}
		});
		Button btnNavigate = (Button) layout.findViewById(R.id.btnNavigate);
		btnNavigate.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				navigateToLocation();
			}
		});
		Button btnViewItem = (Button) layout.findViewById(R.id.btnViewItem);
		btnViewItem.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				nextActivity();
			}
		});
	}
	
	private void nextActivity(){
		
		dialog = ProgressDialog.show(mContext, "", "Loading...", true);
		dialog.setCancelable(true);
		Thread logoTimer = new Thread() {
			@Override
			public void run() {
				try {
					sleep(1000);
					
					final Intent intent = new Intent(mContext,
							AdvertisementActivity.class);					
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("ADVERT_ID", advertID);
					dialog.dismiss();
					mContext.startActivity(intent);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {

				}
			}

		};
		logoTimer.start();
	}
private void navigateToLocation(){
	
		
	dialog = ProgressDialog.show(mContext, "", "Loading...", true);
	dialog.setCancelable(true);
	Thread logoTimer = new Thread() {
		@Override
		public void run() {
			try {

				sleep(2000);
				dialog.dismiss();
				
				double _fromLatitude=GEO_MY_LOCATION.getLatitudeE6() / 1000000.0;
				double _fromLongitude=GEO_MY_LOCATION.getLongitudeE6() / 1000000.0;
				
				double _toLatitude=arrGeoPoint.get(geoPointIndex).getLatitudeE6()/1000000.0;
				double _toLongitude=arrGeoPoint.get(geoPointIndex).getLongitudeE6()/1000000.0;;
				
				String uri = "http://maps.google.com/maps?saddr="
						+ _fromLatitude + "," + _fromLongitude + "&daddr="
						+ _toLatitude + "," + _toLongitude;
				Intent intentNav = new Intent(
						android.content.Intent.ACTION_VIEW,
						Uri.parse(uri));
				intentNav.setClassName(
						"com.google.android.apps.maps",
						"com.google.android.maps.MapsActivity");
				mContext.startActivity(intentNav);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

			}
		}

	};
	logoTimer.start();
}
	protected boolean isOnline() {
	Boolean isOnline = false;
	ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

	if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
			|| connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING) {
		isOnline = true;
		
		// Do something in here when we are connected
	} else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
			|| connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
		Toast.makeText(mContext, "This service requires an active data service,Please check your internet settings", 3000)
				.show();
	}
	return isOnline;
}
	
}

/*Point point = new Point();
Projection projection = mapView.getProjection();
projection.toPixels(arrGeoPoint.get(index), point);

MapView.LayoutParams params = new MapView.LayoutParams(LayoutParams.WRAP_CONTENT,     
LayoutParams.WRAP_CONTENT, arrGeoPoint.get(index), point.x, point.y, MapView.LayoutParams.BOTTOM_CENTER);
params.mode = MapView.LayoutParams.MODE_MAP;            

LinearLayout viewGroup = (LinearLayout) context
.findViewById(R.id.popup);
LayoutInflater layoutInflater = (LayoutInflater) context
.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
View layout = layoutInflater.inflate(R.layout.popup_window, viewGroup);
mapView.addView(layout, params);*/