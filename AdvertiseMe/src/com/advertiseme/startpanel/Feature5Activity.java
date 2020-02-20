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

import java.util.ArrayList;

import com.advertiseme.advertisement.AdvertListActivity;
import com.advertiseme.data.DatabaseHandler;
import com.advertiseme.favoriteseller.FavoriteSellerBaseAdapter;
import com.advertiseme.startup.LoadingDialogActivity;
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

public class Feature5Activity extends StartPanelActivity {

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
	TextView txtFavoriteSellerCount;
		
	ListView lvFavoriteSellerList;
	ArrayList<ListItemDetails> itemFavoriteSellerList;

	WebServiceCall webServiceCall;
	DatabaseHandler db;
	SharedPreferences pref;
	
	String userID;
	
	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feature5);
		setTitleFromActivityLabel(R.id.title_text);
		setupStart();
	}

	void setupStart() {

		txtFavoriteSellerCount=(TextView)findViewById(R.id.txtFavoriteSellerCount);
		
		webServiceCall=new WebServiceCall();
		db = new DatabaseHandler(this);
		
		pref = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);
		
		itemFavoriteSellerList= getSellerList();
		lvFavoriteSellerList = (ListView) findViewById(R.id.lvFavSellerList);
		lvFavoriteSellerList.setAdapter(new FavoriteSellerBaseAdapter(this,
				itemFavoriteSellerList));
		lvFavoriteSellerList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvFavoriteSellerList.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;
				
				Intent intent= new Intent(Feature5Activity.this,
						AdvertListActivity.class);
				intent.putExtra("SELLER_ID", obj_itemDetails.getItemId());
				startActivity(intent);
			   
			}
		});
		lvFavoriteSellerList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				onLongListItemClick(view, position, id);
				return false;
			}
		});
		
	}
	
	protected void onLongListItemClick(View view, int position, long id) {
		Object o = lvFavoriteSellerList.getItemAtPosition(position);
		ListItemDetails obj_itemDetails = (ListItemDetails) o;
		String rowText = obj_itemDetails.getTextHeader();
		String sellerID = obj_itemDetails.getItemId();
		removeFromList(rowText, position,sellerID);
	}
	private ArrayList<ListItemDetails> getSellerList() {

		
		Double positiveFeedback = 0.0;
		String[] resultList;
		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();
		ArrayList<String> arrFavSellerList= getFavoriteSellerList();
		
		if(arrFavSellerList!=null){
		
		txtFavoriteSellerCount.setText("You have "+arrFavSellerList.size() +" sellers listed");	
			
		for (int i=0;i<arrFavSellerList.size();i++){
			
		resultList = webServiceCall.getSellerDetail(arrFavSellerList.get(i));
		
		if (Double.parseDouble(resultList[2]) > 0) {
			positiveFeedback = (Double.parseDouble(resultList[3]) / Double
					.parseDouble(resultList[2])) * 100;
		}
		ListItemDetails item_details = new ListItemDetails();
		item_details.setItemID(arrFavSellerList.get(i));
		item_details.setTextHeader(resultList[0]+"("+resultList[2]+")");
		item_details.setTextNormal(positiveFeedback + "% positive feedback");
		item_details.setTextExtra(resultList[4]);
		item_details.setPreviewURL(resultList[6]);
		resultsList.add(item_details);
		
		}
		}
	/*	item_details = new ListItemDetails();
		item_details.setItemID("0001");
		item_details.setTextHeader("Facebook Inc. (312)");
		item_details.setTextNormal("96.6 positive feedback");
		item_details.setImageNumber(1);
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setItemID("0001");
		item_details.setTextHeader("Toyota coparation (10)");
		item_details.setTextNormal("91.7 positive feedback");
		item_details.setImageNumber(1);
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setItemID("0001");
		item_details.setTextHeader("Micro cars (185)");
		item_details.setTextNormal("99.7 positive feedback");
		item_details.setImageNumber(1);
		resultsList.add(item_details);*/

		return resultsList;
	}
	void removeFromList(String listItemText, int position,final String sellerID) {
		
		final int positionIndex = position;
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		
		
		alertDialog.setTitle("Romove from favorite sellers?");
		alertDialog.setMessage(listItemText);
		
		alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				db.deleteFavoriteSeller(userID,sellerID);
				itemFavoriteSellerList.remove(positionIndex);
				lvFavoriteSellerList.setAdapter(new FavoriteSellerBaseAdapter(
						Feature5Activity.this, itemFavoriteSellerList));
				Toast.makeText(Feature5Activity.this, "Successfully Removed from the list", Toast.LENGTH_LONG).show();
				
			}
		});

		alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		alertDialog.show();

	}

ArrayList<String> getFavoriteSellerList(){
	
	ArrayList<String> arrList=new ArrayList<String>();
	
	ArrayList<ArrayList<String>> resultList;
	ArrayList<String> rowList;
	
	resultList=db.getFavoriteSeller(userID);
	
	if(resultList!=null){
		
	for(int i=0;i<resultList.size();i++){
		
		rowList=resultList.get(i);
		arrList.add(rowList.get(2));		
		
	}
	
	}
	arrList.add("0096");
	return arrList;
	
}
} // end class
