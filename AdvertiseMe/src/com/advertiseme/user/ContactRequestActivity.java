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

import java.util.ArrayList;

import com.advertiseme.favoriteseller.FavoriteSellerBaseAdapter;
import com.advertiseme.search.SearchPeopleBaseAdapter;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This is the activity for feature 5 in the dashboard application. It displays
 * some text and provides a way to get back to the home activity.
 * 
 */

public class ContactRequestActivity extends StartPanelActivity {

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
	TextView txtContactRequestCount;
	
	ListView lvContactRequest;
	ArrayList<ListItemDetails> itemContactRequest;

	WebServiceCall webServiceCall;
	SharedPreferences pref ;
	
	String userID;
	
	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contactrequest);
		setTitleFromActivityLabel(R.id.title_text);
		setupStart();
	}

	void setupStart() {

		webServiceCall=new WebServiceCall();
		
		pref = getSharedPreferences(PREF_PROFILE,MODE_PRIVATE);
		userID=pref.getString(PREF_USERID, null);
		
		txtContactRequestCount=(TextView)findViewById(R.id.txtContactRequestCount);
		txtContactRequestCount.setText("Contacts ("+ 0+")");
		
		itemContactRequest= getContactList();
		lvContactRequest = (ListView) findViewById(R.id.lvContactRequest);
		lvContactRequest.setAdapter(new SearchPeopleBaseAdapter(this,
				itemContactRequest));
		lvContactRequest.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvContactRequest.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;
				
				String userID=obj_itemDetails.getItemId();
				Intent intent=new Intent(getApplicationContext(),
						UserProfileActivity.class);
				intent.putExtra("USER_ID", userID);
				intent.putExtra("USER_TYPE", 1);
				startActivity(intent);
			}
		});
		
		
	}
	
private  ArrayList<ListItemDetails> getContactList() {

	ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();
	ArrayList<ArrayList> resultList=null;
	ArrayList<String> rowList=null;
	
			
	resultList=webServiceCall.getConnectionRequestList(userID);
	
	if(resultList!=null){
		
	if(resultList.size()>0){
		
	for(int i=0;i<resultList.size();i++){
		
			rowList=resultList.get(i);
			
			ListItemDetails item_details = new ListItemDetails();
			item_details.setItemID(rowList.get(0));
			item_details.setTextHeader(rowList.get(5));
			item_details.setTextNormal(rowList.get(4));
			item_details.setTextFooter(getLocationString(rowList.get(1),rowList.get(2),rowList.get(3)));
			item_details.setPreviewURL(rowList.get(6));												
			resultsList.add(item_details);
			
	}
	
	}
	txtContactRequestCount.setText("Contact requests ("+ resultsList.size()+")");
	}
	
	/*ListItemDetails item_details = new ListItemDetails();
	item_details.setTextHeader("Dinusha87");
	item_details.setTextNormal("Peradeniya,Central,Sri Lanka");
	resultsList.add(item_details);

	item_details = new ListItemDetails();
	item_details.setTextHeader("NipunaDev");
	item_details.setTextNormal("Anuradhapura");
	resultsList.add(item_details);

	item_details = new ListItemDetails();
	item_details.setTextHeader("Dinusha87");
	item_details.setTextNormal("Peradeniya,Central,Sri Lanka");
	resultsList.add(item_details);

	item_details = new ListItemDetails();
	item_details.setTextHeader("NipunaDev");
	item_details.setTextNormal("Anuradhapura");
	resultsList.add(item_details);

	item_details = new ListItemDetails();
	item_details.setTextHeader("Dinusha87");
	item_details.setTextNormal("Peradeniya,Central,Sri Lanka");
	resultsList.add(item_details);

	item_details = new ListItemDetails();
	item_details.setTextHeader("NipunaDev");
	item_details.setTextNormal("Anuradhapura");
	resultsList.add(item_details);*/

	return resultsList;
}
static String getLocationString(String strCity,String strState,String strCountry){
	String defaultText="";
	String locationString="";
	
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


} // end class
