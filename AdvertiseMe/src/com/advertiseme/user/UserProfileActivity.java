/*
 * Copyright (C) 2011 Wglxy.com
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

package com.advertiseme.user;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.advertiseme.favoriteseller.FavoriteSellerBaseAdapter;
import com.advertiseme.recent.UserActivityActivity;
import com.advertiseme.recent.UserViewsActivity;
import com.advertiseme.search.SearchPeopleBaseAdapter;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This is the activity for feature 5 in the dashboard application. It displays
 * some text and provides a way to get back to the home activity.
 * 
 */

public class UserProfileActivity extends StartPanelActivity {

	/**
	 * onCreate
	 * 
	 * Called when the activity is first created. This is where you should do
	 * all of your normal static set up: create views, bind data to lists, etc.
	 * This method also provides you with a Bundle containing the activity's
	 * previously frozen state, if there was one.
	 * 
	 * Always followed by onStart().
	 * 
	 * @param savedInstanceState
	 *            Bundle
	 */
	ListView lvContactRequest;
	ArrayList<ListItemDetails> itemContactRequest;
	
	EditText text,txtStatusMessage;
	ImageView profilePicture;
	LinearLayout lnFullName, lnEmail, lnChangePic, lnAddress,lnCity, lnCountry, lnState,
			lnLanguage, lnMobilePhone, lnHomePhone, lnOfficePhone, lnHomePage,
			lnFacebook, lnGooglePlus, lnTwitter, lnSkype;
	TextView txtFullName, txtEmail, txtPassword,txtAddress, txtCity,
			txtCountry, txtState, txtLanguage, txtMobilePhone, txtHomePhone,
			txtOfficePhone, txtHomePage, txtFacebook, txtGooglePlus,
			txtTwitter, txtSkype,txtNameHeading,txtLocation,txtLogoutUser,
			txtUserName,txtAction,txtActivityCount,txtViewCount,txtContactCount,txtContactRequestCount;;

	String userID,userType="-1",loginUserID;
	
	WebServiceCall webServiceCall;
	
	SharedPreferences pref;
	
	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		setTitleFromActivityLabel(R.id.title_text);
		setupStart();
	}

	void setupStart() {
		
		pref = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		loginUserID = pref.getString(PREF_USERID, null);
		
		userID = getIntent().getExtras().getSerializable("USER_ID")
		.toString();
		
		try {
			userType = getIntent().getExtras().getSerializable("USER_TYPE")		
			.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
					
		webServiceCall=new WebServiceCall();
		
		TabHost tabHost = (TabHost) findViewById(R.id.tbHost);
		tabHost.setup();

		View tabView = createTabView(this, "Account");
		TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab 1").setIndicator(
				tabView);
		spec1.setContent(R.id.lnAccount);
		tabHost.addTab(spec1);

		tabView = createTabView(this, "Profile");
		TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab 2").setIndicator(
				tabView);
		spec2.setContent(R.id.lnProfile);
		tabHost.addTab(spec2);

		tabHost.setCurrentTab(1);
		
		profilePicture=(ImageView)findViewById(R.id.imgProfileImage);
				
		txtNameHeading=(TextView)findViewById(R.id.txtNameHeading);
		txtLogoutUser=(TextView)findViewById(R.id.text2);
		txtLocation=(TextView)findViewById(R.id.txtLocation);
		txtStatusMessage=(EditText)findViewById(R.id.text1);
		txtFullName = (TextView) findViewById(R.id.txtFullName);
		txtEmail = (TextView) findViewById(R.id.txtEmail);
		txtAddress=(TextView) findViewById(R.id.txtAddress);
		txtCity = (TextView) findViewById(R.id.txtCity);
		txtCountry = (TextView) findViewById(R.id.txtCountry);
		txtState = (TextView) findViewById(R.id.txtState);
		txtLanguage = (TextView) findViewById(R.id.txtLanguage);
		txtHomePage = (TextView) findViewById(R.id.txtHomePage);
		txtMobilePhone= (TextView) findViewById(R.id.txtMobilePhone);
		txtHomePhone = (TextView) findViewById(R.id.txtHomePhone);	
		txtOfficePhone = (TextView) findViewById(R.id.txtOfficePhone);				
		txtHomePage = (TextView) findViewById(R.id.txtHomePage);		
		txtFacebook = (TextView) findViewById(R.id.txtFacebook);
		txtGooglePlus = (TextView) findViewById(R.id.txtGooglePlus);
		txtTwitter = (TextView) findViewById(R.id.txtTwitter);
		txtSkype = (TextView) findViewById(R.id.txtSkype);
		txtUserName=(TextView)findViewById(R.id.txtUserName);
		
		txtAction=(TextView)findViewById(R.id.txtAction);
		
		txtActivityCount=(TextView)findViewById(R.id.txtActivityCount);
		txtViewCount=(TextView)findViewById(R.id.txtViewCount);
		txtContactCount=(TextView)findViewById(R.id.txtContactCount);
		txtContactRequestCount=(TextView)findViewById(R.id.txtContactRequestCount);
		
		setUserProfileDetail(userID);
		getUserAcountCount();
		setUserAccountDetail();
	}
	private void setUserProfileDetail(String userID){
		
		String[] resultList= webServiceCall.getUserProfileDetail(userID);
		
		
		txtNameHeading.setText(resultList[1].replace("anyType{}", "Not specified"));
		txtStatusMessage.setText(resultList[2].replace("anyType{}", "Not specified"));
		txtLogoutUser.setText(resultList[19].replace("anyType{}", "Not specified"));
		txtUserName.setText(resultList[19].replace("anyType{}", "Not specified"));
		txtFullName.setText(resultList[1].replace("anyType{}", "Not specified"));
		txtEmail.setText(resultList[3].replace("anyType{}", "Not specified"));
		txtAddress.setText(resultList[4].replace("anyType{}", "Not specified"));
		txtCity.setText(resultList[5].replace("anyType{}", "Not specified"));
		txtCountry.setText(resultList[6].replace("anyType{}", "Not specified"));
		txtState.setText(resultList[7].replace("anyType{}", "Not specified"));
		txtLanguage.setText(resultList[8].replace("anyType{}", "Not specified"));
		txtMobilePhone.setText(resultList[9].replace("anyType{}", "Not specified"));
		txtHomePhone.setText(resultList[10].replace("anyType{}", "Not specified"));
		txtOfficePhone.setText(resultList[11].replace("anyType{}", "Not specified"));
		txtHomePage.setText(resultList[12].replace("anyType{}", "Not specified"));
		txtFacebook.setText(resultList[13].replace("anyType{}", "Not specified"));
		txtGooglePlus.setText(resultList[14].replace("anyType{}", "Not specified"));
		txtTwitter.setText(resultList[15].replace("anyType{}", "Not specified"));
		txtSkype.setText(resultList[16].replace("anyType{}", "Not specified"));

		txtLocation.setText(getLocationString());
		Bitmap bimage = getBitmapFromURL(resultList[18]);
		profilePicture.setImageBitmap(bimage);
		
	}
	private void getUserAcountCount() {
		
		String[] resultList = webServiceCall.getUserAcountCount(userID);
		txtActivityCount.setText(resultList[0]);
		txtViewCount.setText(resultList[1]);
		txtContactCount.setText(resultList[3]);
		txtContactRequestCount.setText(resultList[2]);
		
	}
	private void setUserAccountDetail(){
	
	setActionType();
	}

	private void setActionType(){
	
	int actiionType=Integer.parseInt(userType);
	
	switch(actiionType){
	
	case 0:
		txtAction.setText("Remove from connections");break;	
		
	case 1:
		txtAction.setText("Accept connection request");break;
	case 2:
		txtAction.setText("Send a connection request");break;
	}
}

	public void onClickAction(View v){
	
	int actiionType=Integer.parseInt(userType);
	switch(actiionType){
	
	case 0:
		removeFromContact();
		break;	
		
	case 1:
		saveContact();
		break;
	case 2:
		saveContactRequest();
		break;
		}
	}
	public void onClickActivity(View view){
		
		Intent intent=new Intent(getApplicationContext(),UserActivityActivity.class);
		intent.putExtra("USER_ID", userID);
		startActivity(intent);
	}
	public void onClickView(View view){
		
		Intent intent=new Intent(getApplicationContext(),UserViewsActivity.class);
		intent.putExtra("USER_ID", userID);
		startActivity(intent);
	}
	public void onClickMyContact(View view){
	
		
		Intent intent=new Intent(getApplicationContext(),
				MyContactActivity.class);
		intent.putExtra("USER_ID", userID);
		startActivity(intent);
	}


	public static Bitmap getBitmapFromURL(String src) {
	try {
		Log.e("src", src);
		URL url = new URL(src);
		HttpURLConnection connection = (HttpURLConnection) url
				.openConnection();
		connection.setDoInput(true);
		connection.connect();
		InputStream input = connection.getInputStream();
		Bitmap myBitmap = BitmapFactory.decodeStream(input);
		Log.e("Bitmap", "returned");
		return myBitmap;
	} catch (IOException e) {
		e.printStackTrace();
		Log.e("Exception", e.getMessage());
		return null;
	}
	}
	String getLocationString(){
	String defaultText="Not specified";
	String locationString="";
	String strCity=txtCity.getText().toString(),strState=txtState.getText().toString(),strCountry=txtCountry.getText().toString();
	if(!strCity.equalsIgnoreCase(defaultText)){
		locationString=locationString+strCity+",";
	}
	if(!strState.equalsIgnoreCase(defaultText)){
		locationString=locationString+strState+",";
	}
	if(!strCountry.equalsIgnoreCase(defaultText)){
		locationString=locationString+strCountry;
	}
	
	return locationString;
}
	private static View createTabView(Context context, String tabText) {
		View view = LayoutInflater.from(context).inflate(R.layout.custom_tab,
				null, false);
		TextView tv = (TextView) view.findViewById(R.id.tabTitleText);
		tv.setText(tabText);
		return view;
	}
	
	void saveContactRequest( ){
		
		ArrayList paraList =new ArrayList<String>();
			
		paraList.add(userID);
		paraList.add(loginUserID);
				
		Boolean isSuccess=webServiceCall.saveContactRequest(paraList);
		
		if(isSuccess){		
			txtAction.setText("Contact request sent");
			toast("Contact request sent");		
						
		}else{
			toast("Could not complete your request, please try again");
		}
		}
	void saveContact( ){
		
		ArrayList paraList =new ArrayList<String>();
			
		paraList.add(loginUserID);
		paraList.add(userID);
				
		Boolean isSuccess=webServiceCall.saveContact(paraList);
		
		if(isSuccess){		
			txtAction.setText("Remove from connections");
			toast("Contact request Accepted");		
						
		}else{
			toast("Could not complete your request, please try again");
		}
		}
	void removeFromContact( ){
	
		ArrayList paraList =new ArrayList<String>();
			
		paraList.add(loginUserID);
		paraList.add(userID);
				
		Boolean isSuccess=webServiceCall.removeFromContact(paraList);
		
		if(isSuccess){		
			txtAction.setText("Send a connection request");
			toast("Successfully removed from contacts");		
						
		}else{
			toast("Could not complete your request, please try again");
		}
		}
} // end class
