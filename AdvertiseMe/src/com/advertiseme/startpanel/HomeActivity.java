/*
 * Copyright (C) 2012 Wglxy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.advertiseme.startpanel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



public class HomeActivity extends StartPanelActivity 
{

/**
 * onCreate - called when the activity is first created.
 * Called when the activity is first created. 
 * This is where you should do all of your normal static set up: create views, bind data to lists, etc. 
 * This method also provides you with a Bundle containing the activity's previously frozen state, if there was one.
 * 
 * Always followed by onStart().
 *
 */
private TextView txtFullName,txtUserStatusMessage,txtAdvertiseMeName,txtUserLocation;
private ImageView imgProfilePicture,imgUserLoginStatus;

private static final String PREF_PROFILE = "userProfile";
private static final String PREF_USERID = "userID";
public static final String PREFS_FULLNAME = "fullName";
private static final String PREF_USERNAME= "userName";
private static final String PREF_LOCATION = "userLocation";
private static final String PREF_STATUS_MSG= "statusMessage";
private static final String PREF_PROFILE_IMG="userImage";

@Override
protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    overridePendingTransition(R.anim.incoming, R.anim.outgoing);
    setContentView(R.layout.start_panel);
    setupStart();
}
    

	void setupStart(){
		txtFullName=(TextView)findViewById(R.id.txtFullName);
		txtUserStatusMessage=(TextView)findViewById(R.id.txtUserStatusMessage);
		txtAdvertiseMeName=(TextView)findViewById(R.id.txtAdvertiseMeName);
		txtUserLocation=(TextView)findViewById(R.id.txtUserLocation);		
		imgProfilePicture=(ImageView)findViewById(R.id.imgProfilePicture);
		imgUserLoginStatus=(ImageView)findViewById(R.id.imgUserLoginStatus);
		
		
		 SharedPreferences pref = getSharedPreferences(PREF_PROFILE,MODE_PRIVATE);		
		 txtFullName.setText(pref.getString(PREFS_FULLNAME, null));
		 txtUserLocation.setText(pref.getString(PREF_LOCATION, null));
		 txtUserStatusMessage.setText(pref.getString(PREF_STATUS_MSG, null));
		 txtAdvertiseMeName.setText(pref.getString(PREF_USERNAME, null));
		
		 setProfileImage(pref.getString(PREF_PROFILE_IMG, null));
	}
	
public void onClickUserName(View view){
	
	  startActivity (new Intent(getApplicationContext(), Feature7Activity.class));
}
/**
 * onDestroy
 * The final call you receive before your activity is destroyed. 
 * This can happen either because the activity is finishing (someone called finish() on it, 
 * or because the system is temporarily destroying this instance of the activity to save space. 
 * You can distinguish between these two scenarios with the isFinishing() method.
 *
 */

@Override
protected void onDestroy ()
{
   super.onDestroy ();
}

/**
 * onPause
 * Called when the system is about to start resuming a previous activity. 
 * This is typically used to commit unsaved changes to persistent data, stop animations 
 * and other things that may be consuming CPU, etc. 
 * Implementations of this method must be very quick because the next activity will not be resumed 
 * until this method returns.
 * Followed by either onResume() if the activity returns back to the front, 
 * or onStop() if it becomes invisible to the user.
 *
 */

@Override
protected void onPause ()
{
   super.onPause ();
}

/**
 * onRestart
 * Called after your activity has been stopped, prior to it being started again.
 * Always followed by onStart().
 *
 */

@Override
protected void onRestart ()
{
   super.onRestart ();
}

/**
 * onResume
 * Called when the activity will start interacting with the user. 
 * At this point your activity is at the top of the activity stack, with user input going to it.
 * Always followed by onPause().
 *
 */

@Override
protected void onResume ()
{
   super.onResume ();
}

/**
 * onStart
 * Called when the activity is becoming visible to the user.
 * Followed by onResume() if the activity comes to the foreground, or onStop() if it becomes hidden.
 *
 */

@Override
protected void onStart ()
{
   super.onStart ();
}

/**
 * onStop
 * Called when the activity is no longer visible to the user
 * because another activity has been resumed and is covering this one. 
 * This may happen either because a new activity is being started, an existing one 
 * is being brought in front of this one, or this one is being destroyed.
 *
 * Followed by either onRestart() if this activity is coming back to interact with the user, 
 * or onDestroy() if this activity is going away.
 */

@Override
protected void onStop ()
{
   super.onStop ();
}

void setProfileImage(String profileJpgPath){
	try {
if(profileJpgPath!="default"){
BitmapFactory.Options options = new BitmapFactory.Options();
// options.inSampleSize = 2;
Bitmap bm = BitmapFactory.decodeFile(profileJpgPath, options);
imgProfilePicture.setImageBitmap(bm);

imgProfilePicture.setAdjustViewBounds(true);
imgProfilePicture.setMinimumHeight(200);
imgProfilePicture.setMinimumWidth(200);
imgProfilePicture.setMaxHeight(200);
imgProfilePicture.setMaxWidth(200);
}else{
	imgProfilePicture.setBackgroundResource(R.drawable.img_default_user);
}
	} catch (Exception e) {
		// TODO: handle exception
	}
}
} // end class
