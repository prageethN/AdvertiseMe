package com.advertiseme.startpanel;



import com.advertiseme.deal.TodayDealActivity;
import com.advertiseme.recomendation.RecommendationActivity;
import com.advertiseme.search.SavedSearchActivity;
import com.advertiseme.search.SearchPeopleActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View;
import android.view.MotionEvent;

public class SlideMenuActivity extends Activity implements android.view.View.OnClickListener{
	
	ImageButton btnSlideBack;
	LinearLayout lnStartPanel;
	ImageView imgEvent,imgSavedSearch,imgUserImg;
	TextView txtLocation,txtName;
	
	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	public static final String PREFS_FULLNAME = "fullName";
	private static final String PREF_USERNAME= "userName";
	private static final String PREF_LOCATION = "userLocation";
	private static final String PREF_STATUS_MSG= "statusMessage";
	private static final String PREF_PROFILE_IMG="userImage";
	
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	   	    setContentView (R.layout.activity_slidemenu);
	   	 setupStart();
	}
	
	void setupStart(){
		
		btnSlideBack=(ImageButton)findViewById(R.id.btnSlideBack);
		btnSlideBack.setOnClickListener(this);
		lnStartPanel=(LinearLayout)findViewById(R.id.lnStartPanel);
		lnStartPanel.setOnClickListener(this);
		imgEvent=(ImageView)findViewById(R.id.imgEvent);
		imgSavedSearch=(ImageView)findViewById(R.id.imgSavedSearch);
		imgUserImg=(ImageView)findViewById(R.id.imgUserImg);
		
		imgEvent.setImageResource(R.drawable.bg_new);
		imgSavedSearch.setImageResource(R.drawable.bg_new);
		
		txtLocation=(TextView)findViewById(R.id.txtUserLoc);
		txtName=(TextView)findViewById(R.id.txtUserFullName);
		
		SharedPreferences pref = getSharedPreferences(PREF_PROFILE,MODE_PRIVATE);
		txtName.setText(pref.getString(PREFS_FULLNAME, null));
		txtLocation.setText(pref.getString(PREF_LOCATION, null));
		 
		setProfileImage(pref.getString(PREF_PROFILE_IMG, null));
	}

	public void onClick(View view) {
		onBackPressed();

		
	}
	 @Override
	 public void onBackPressed() {
		 super.onBackPressed();
		 overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
	 }
	 
	 @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	 @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	 public void onClickRecommendation (View v)
	 {
	 	startActivity (new Intent(getApplicationContext(), RecommendationActivity.class));
	 }
	 public void onClickSavedSearch (View v)
	 {
	 	startActivity (new Intent(getApplicationContext(), SavedSearchActivity.class));
	 }
	 public void onClickFindPeople (View v)
	 {
	 	startActivity (new Intent(getApplicationContext(), SearchPeopleActivity.class));
	 }
	 public void onClickTodayDeal (View v)
	 {
	 	startActivity (new Intent(getApplicationContext(), TodayDealActivity.class));
	 }
	 
	 void setProfileImage(String profileJpgPath){
			try {
		if(profileJpgPath!="default"){
		BitmapFactory.Options options = new BitmapFactory.Options();
		// options.inSampleSize = 2;
		Bitmap bm = BitmapFactory.decodeFile(profileJpgPath, options);
		imgUserImg.setImageBitmap(bm);

		imgUserImg.setAdjustViewBounds(true);
		imgUserImg.setMinimumHeight(200);
		imgUserImg.setMinimumWidth(200);
		imgUserImg.setMaxHeight(200);
		imgUserImg.setMaxWidth(200);
		}else{
			imgUserImg.setBackgroundResource(R.drawable.img_default_user);
		}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
}
