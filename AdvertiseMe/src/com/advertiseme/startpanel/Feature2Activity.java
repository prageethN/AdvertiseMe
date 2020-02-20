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

package com.advertiseme.startpanel;

import com.adveriseme.category.CategoryListActivity;
import com.advertiseme.advertisement.AdvertListActivity;
import com.advertiseme.advertisement.AdvertisementActivity;
import com.advertiseme.startup.LoadingDialogActivity;
import com.advertiseme.webservicecall.WebServiceCall;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * This is the activity for feature 2 in the dashboard application. It displays
 * some text and provides a way to get back to the home activity.
 * 
 */

public class Feature2Activity extends StartPanelActivity implements
		OnClickListener {

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
	LinearLayout lnScanCode, lnCategory;
	EditText txtSearchQuery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feature2);
		setTitleFromActivityLabel(R.id.title_text);
		setupStart();
	}

	void setupStart() {

		lnScanCode = (LinearLayout) findViewById(R.id.lnScanCode);
		lnScanCode.setOnClickListener(this);
		lnCategory = (LinearLayout) findViewById(R.id.lnCategory);
		lnCategory.setOnClickListener(this);
		
		txtSearchQuery=(EditText)findViewById(R.id.txtSearchQuery);
	}

	public void onClick(View view) {
		if (view == lnCategory) {
			startActivity(new Intent(Feature2Activity.this,
					CategoryListActivity.class));
		}
		if(view==lnScanCode){
			startQRCodeReader();
		}

	}

	public void startQRCodeReader() {
		try {
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);

		} catch (Exception ex) {

			Toast.makeText(this, "Error in QR scanning", 10);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String content = intent.getStringExtra("SCAN_RESULT");
				Intent intnt = new Intent(Feature2Activity.this,
						AdvertisementActivity.class);
				String advertId=getAdvertisementID(content);
				if(!advertId.equalsIgnoreCase("error")){
					intnt.putExtra("ADVERT_ID", advertId);
					startActivity(intnt);
				}else{
					toast("Invalid QR Code");
				}
								
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Error in QR reading", 10).show();
			}
		}
	}
	public void onClickGo(View view){

		String searchQuery=txtSearchQuery.getText().toString();
		if(isValidSearchQuery(searchQuery)){
			//Intent intent= new Intent(Feature2Activity.this,
				//	AdvertListActivity.class);
			Intent intent= new Intent(Feature2Activity.this,
			AdvertListActivity.class);
			
			intent.putExtra("SEARCH_QUERY", searchQuery);		
			startActivity(intent);
		}else{
			
			toast("Please enter a search query");
		}
		
		
	}
	
	Boolean isValidSearchQuery(String searchQuery){
		if(searchQuery.equalsIgnoreCase("")){
			return false;
		}else{
			return true;
		}
		
	}
	private String getAdvertisementID(String refCode){
		
		WebServiceCall webServiceCall=new WebServiceCall();
		return webServiceCall.getAdvertisementID(refCode);
	}
	public void onClickSearch (View v)
	{
	   
	}

} // end class
