package com.advertiseme.nearby;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.advertiseme.startpanel.AboutActivity;
import com.advertiseme.startpanel.Feature2Activity;
import com.advertiseme.startpanel.HomeActivity;
import com.advertiseme.startpanel.R;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


public class MyMapLocationActivity extends MapActivity{

	private Button btnFashion;
	private MapView mapView;
	TextView txtRadious;
	private MyLocationOverlay myLocationOverlay;
	private List<Overlay> mapOverlays,overlay;
	private MapController mapController;
	Point p;
	private int PROGRESS=5;
	private int TYPE=1;
	private List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
	private List<String> arrPlaceAddress = new ArrayList<String>();
	private List<String> arrPlaceName = new ArrayList<String>();

	private boolean isFound;
	
	WebServiceCall WebServiceCall;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // main.xml contains a MapView
        setContentView(R.layout.activity_locationdisplay);
        setTitleFromActivityLabel (R.id.title_text);
        setupStart();
     
    }
    
    private void setupStart(){
    	
    	
    	WebServiceCall=new WebServiceCall();
    	
    	// extract MapView from layout
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		
		MapItemizedOverlay.mapView=mapView;
		MapItemizedOverlay.context=MyMapLocationActivity.this;
		
		mapView.getOverlays().clear();	
		
		// create an overlay that shows our current location
		myLocationOverlay = new FixedMyLocationOverlay(this, mapView);
		
		// add this overlay to the MapView and refresh it
		//mapView.getOverlays().add(myLocationOverlay);
		//mapView.postInvalidate();
		//mapOverlays = mapView.getOverlays();
		
				
		// call convenience method that zooms map on our location
		zoomToMyLocation();
		refreshCircleOverlay();
		// popup controls 
		Button btn_show = (Button) findViewById(R.id.btnSetRadious);
		btn_show.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {

				// Open popup window
				if (p != null)
					showPopup(MyMapLocationActivity.this, p);
			}
		});
		
		//getLocations();
    }
    
    private void refreshCircleOverlay(){
    	
    	final ProgressDialog	dialog = ProgressDialog.show(this, "", "Loading places, please wait...", true);
    	dialog.setCancelable(true);
    	Thread logoTimer = new Thread() {
			@Override
			public void run() {
				try {
					sleep(2000);
					dialog.dismiss();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {

				}
			}

		};
		logoTimer.start();
		
		mapView.getOverlays().clear();	
		// create an overlay that shows our current location
		myLocationOverlay = new FixedMyLocationOverlay(MyMapLocationActivity.this, mapView);
		
		// add this overlay to the MapView and refresh it
		mapView.getOverlays().add(myLocationOverlay);		
		mapView.postInvalidate();
		mapOverlays = mapView.getOverlays();
		displayLocations();
		
    }
    @Override
	protected void onResume() {
		super.onResume();
		// when our activity resumes, we want to register for location updates
		myLocationOverlay.enableMyLocation();
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		// when our activity pauses, we want to remove listening for location updates
		myLocationOverlay.disableMyLocation();
	}
	
	/**
	 * This method zooms to the user's location with a zoom level of 10.
	 */
	private void zoomToMyLocation() {
		GeoPoint myLocationGeoPoint = myLocationOverlay.getMyLocation();
		
		if(myLocationGeoPoint != null) {
			
			FixedMyLocationOverlay.GEO_MY_LOCATION=myLocationGeoPoint;
			MapItemizedOverlay.GEO_MY_LOCATION=myLocationGeoPoint;
			
			mapView.getController().animateTo(myLocationGeoPoint);
			mapView.getController().setZoom(15);
		}
		else {
			//Toast.makeText(this, "Cannot determine location", Toast.LENGTH_SHORT).show();
			animateToCurrentLocation();
		}
	}
	
	private void animateToCurrentLocation(){
	
	mapController = mapView.getController();
	LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	
	try {
		Location loc = manager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		double lt = loc.getLatitude();
		double lo = loc.getLongitude();

		GeoPoint initGeoPoint = new GeoPoint((int) (lt * 1000000),
				(int) (lo * 1000000));
		mapController.animateTo(initGeoPoint);	
		mapController.setZoom(15);
		FixedMyLocationOverlay.GEO_MY_LOCATION=initGeoPoint;
		MapItemizedOverlay.GEO_MY_LOCATION=initGeoPoint;
	} catch (Exception ex) {
		
		System.out.println("Could not current location");
	}
}
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	
// ------------------------------------ Location display ------------------------
	
	private void getLocations() {
		
		// Call webservice to recieve places
		String[] arrPlaces=null;
		switch(TYPE){
		case 0:
			String[] arrTemp = {
					"Softlogic Holdings,6.798425,79.889550,Nokia N9",
					"Apple Store,6.795320,79.884510,iPhone 4S- White",
					"House for sale near Katubadda,6.793110,79.881450,Katubadda" };
			arrPlaces=arrTemp;break;
		case 1:
			String[] arrTemp1;
			
			
			ArrayList<ArrayList> resultList=new ArrayList<ArrayList>();
			ArrayList<String> rowList=null;
			resultList = WebServiceCall.getGeoLocationInfo("0000");
			
			if(resultList!=null){
			arrTemp1=new String[resultList.size()];
			
			for(int i=0;i<resultList.size();i++){
				rowList=resultList.get(i);
				arrTemp1[i]=rowList.get(0)+","+rowList.get(2)+","+rowList.get(3)+","+rowList.get(5)+" from "+rowList.get(6);				
			}
			
			arrPlaces=arrTemp1;break;
			}else{
				String[] arrTemp3 = {
						"Softlogic Holdings,6.798425,79.889550,Nokia N9",
						"Apple Store,6.795320,79.884510,iPhone 4S- White",
						"House for sale near Katubadda,6.793110,79.881450,Katubadda" };
				arrPlaces=arrTemp3;break;
			}
		}
		geoPoints.clear();
		for (int i = 0; i < arrPlaces.length; i++) {
			String[] _temp = arrPlaces[i].split(",");

			String coordinates[] = { _temp[1], _temp[2] };
			
			double lat = Double.parseDouble(coordinates[0]);
			double lng = Double.parseDouble(coordinates[1]);

			GeoPoint gpoint = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));

			geoPoints.add(gpoint);
			MapItemizedOverlay.arrGeoPoint.add(gpoint);
			arrPlaceAddress.add(_temp[3]);
			arrPlaceName.add(_temp[0]);
		}
	}
	
	private void displayLocations() {

		getLocations();
		
		int _index = 0;
		
		mapOverlays = mapView.getOverlays();
		
		Drawable drawable = getResources().getDrawable(
				R.drawable.locationmarker);
		MapItemizedOverlay itemizedOverlay = new MapItemizedOverlay(drawable,
				this);
try{
		for (GeoPoint point : geoPoints) {

			// Create a location because Location has a distanceTo method that
			// we can
			// use for buffering. Notice that distanceTo calculate distance in
			// meter
			Location gpsLocation = new Location("current location");

			// Get our current gps point and use it to create a location
			GeoPoint currentLocation = FixedMyLocationOverlay.GEO_MY_LOCATION;
			
			if(currentLocation!=null){
			
				
			
			double lat = (currentLocation.getLatitudeE6() / 1000000.0);
			double lng = (currentLocation.getLongitudeE6() / 1000000.0);
			gpsLocation.setLatitude(lat);
			gpsLocation.setLongitude(lng);

			Location pointLocation = new Location("point");
			pointLocation.setLatitude(point.getLatitudeE6() / 1000000.0);
			pointLocation.setLongitude(point.getLongitudeE6() / 1000000.0);

			// Calculate the distance between current location and point
			// location
			if (gpsLocation.distanceTo(pointLocation) < 
					((PROGRESS*1000)-2)) {
				isFound = true;
				OverlayItem overlayitem = new OverlayItem(point,
						arrPlaceName.get(_index), arrPlaceAddress.get(_index));
				itemizedOverlay.addOverlay(overlayitem);
				_index++;
				if (isFound)
					mapOverlays.add(itemizedOverlay);
			}else{
				Toast.makeText(this, "Your current location could not be retrieve at this time", Toast.LENGTH_LONG);
			}
		}
		}
}catch(Exception ex){
	Toast.makeText(this, "Your current location could not be retrieve at this time", Toast.LENGTH_LONG);
}

	}
// ------------------------------------ PopUp controls -------------------------
	
	// Get the x and y position after the button is draw on screen
	// (It's important to note that we can't get the position in the onCreate(),
	// because at that stage most probably the view isn't drawn yet, so it will
	// return (0, 0))
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

		int[] location = new int[2];
		Button button = (Button) findViewById(R.id.btnSetRadious);

		// Get the x, y location and store it in the location[] array
		// location[0] = x, location[1] = y.
		button.getLocationOnScreen(location);

		// Initialize the Point with x, and y positions
		p = new Point();
		p.x = location[0];
		p.y = location[1];
	}

	// The method that displays the popup.
	private void showPopup(final Activity context, Point p) {
		
		int popupWidth = ViewGroup.LayoutParams.FILL_PARENT;
		int popupHeight = 300;

		// Inflate the popup_layout.xml
		LinearLayout viewGroup = (LinearLayout) context
				.findViewById(R.id.popup);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);

		// Creating the PopupWindow
		final PopupWindow popup = new PopupWindow(context);
		popup.setContentView(layout);
		popup.setWidth(popupWidth);
		popup.setHeight(popupHeight);
		popup.setFocusable(true);

		// Some offset to align the popup a bit to the right, and a bit down,
		// relative to button's position.
		int OFFSET_X = 30;
		int OFFSET_Y = 30;

		// Clear the default translucent background
		popup.setBackgroundDrawable(new BitmapDrawable());

		// Displaying the popup at the specified location, + offsets.
		popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y
				+ OFFSET_Y);

		// Getting a reference to seekbar, and set action.

		txtRadious = (TextView) layout.findViewById(R.id.txtRadious);
		SeekBar seekBarRadious = (SeekBar) layout.findViewById(R.id.sbRadious);
		seekBarRadious.setProgress(PROGRESS);
		txtRadious.setText(PROGRESS+"km");
		seekBarRadious
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						txtRadious.setText(progress+"km");
						PROGRESS=progress;
						FixedMyLocationOverlay.CIRCLE_RADIUS_METERS=progress*1000;
					}

					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}
				});
		// Getting a reference to Close button, and close the popup when
		// clicked.
		Button close = (Button) layout.findViewById(R.id.close);
		close.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				popup.dismiss();
			}
		});
		Button done = (Button) layout.findViewById(R.id.btnDone);
		done.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				refreshCircleOverlay();
				popup.dismiss();
			}
		});
	}

	public void onClickCategory(View view) {
		// TODO Auto-generated method stub
		if(view==findViewById(R.id.btnFashion)){
			TYPE=1;
			refreshCircleOverlay();
		}
	}
	public void onClickZoom(View view){
		if(view==findViewById(R.id.btnZoomIn)){
			mapView.getController().zoomIn();
		}
		if(view==findViewById(R.id.btnZoomOut)){
			mapView.getController().zoomOut();
		}
	}
	public void setTitleFromActivityLabel (int textViewId)
	{
	    TextView tv = (TextView) findViewById (textViewId);
	    if (tv != null) tv.setText (getTitle ());
	}
	public void onClickHome (View v)
	{
	    goHome (this);
	}
	public void goHome(Context context) 
	{
	    final Intent intent = new Intent(context, HomeActivity.class);
	    intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    context.startActivity (intent);
	}
	public void onClickAbout (View v)
	{
		startActivity (new Intent(getApplicationContext(), AboutActivity.class));
	}
	public void onClickSearch (View v)
	{
	    startActivity (new Intent(getApplicationContext(), Feature2Activity.class));
	}

}