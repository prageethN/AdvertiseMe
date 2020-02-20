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

package com.advertiseme.message;

import java.util.ArrayList;

import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


/**
 * This is the activity for feature 5 in the dashboard application. It displays
 * some text and provides a way to get back to the home activity.
 * 
 */

public class AddReceiptantActivity extends StartPanelActivity {

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
	ListView lvAddRecieptant;
	ArrayList<ListItemDetails> itemAddRecieptant;

	WebServiceCall webServiceCall;
	SharedPreferences pref ;
	
	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addrecieptant);
		setTitleFromActivityLabel(R.id.title_text);
		setupStart();
	}

	void setupStart() {

		webServiceCall=new WebServiceCall();
		
		itemAddRecieptant= getContactList();
		lvAddRecieptant = (ListView) findViewById(R.id.lvAddRecieptant);
		lvAddRecieptant.setAdapter(new AddReceiptantBaseAdapter(this,
				itemAddRecieptant));
		
		
	}
	
	protected void onLongListItemClick(View view, int position, long id) {
		Object o = lvAddRecieptant.getItemAtPosition(position);
		ListItemDetails obj_itemDetails = (ListItemDetails) o;
		String rowText = obj_itemDetails.getTextHeader();
		
	}
	private ArrayList<ListItemDetails> getContactList() {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ListItemDetails item_details = new ListItemDetails();
		item_details.setTextHeader("prageethmax87");
		item_details.setTextNormal("Kandy,Central,Sri Lanka");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("dinusha87");
		item_details.setTextNormal("peradeniya,Sri Lanka");
		resultsList.add(item_details);

		return resultsList;
	}
	
public void onClickDone(View v){
	this.finish();
}
public void onClickCancel(View v){
	this.finish();
}
} // end class
