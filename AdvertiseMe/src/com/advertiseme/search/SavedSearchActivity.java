package com.advertiseme.search;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
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

import com.advertiseme.advertisement.AdvertListActivity;
import com.advertiseme.advertisement.AdvertisementActivity;
import com.advertiseme.data.DatabaseHandler;
import com.advertiseme.startpanel.Feature2Activity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

public class SavedSearchActivity extends StartPanelActivity {

	ListView lvSavedSearch;
	ArrayList<ListItemDetails> itemSavedSearch;
	TextView txtOptions;
	String rowText;
	int rowPosition;

	WebServiceCall webServiceCall;
	DatabaseHandler db;
	SharedPreferences pref;

	String userID;

	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_savedsearch);
		setTitleFromActivityLabel(R.id.title_text);
		setupStart();
	}

	void setupStart() {

		webServiceCall = new WebServiceCall();
		db = new DatabaseHandler(this);

		pref = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);
		
		itemSavedSearch = getActiveList();
		lvSavedSearch = (ListView) findViewById(R.id.lvSavedSearch);
		lvSavedSearch.setAdapter(new SavedSearchBaseAdapter(this,
				itemSavedSearch));
		lvSavedSearch.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvSavedSearch.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;
				String searchQuery=obj_itemDetails.getTextHeader();
				Intent intent= new Intent(SavedSearchActivity.this,
						AdvertListActivity.class);		
				intent.putExtra("SEARCH_QUERY", searchQuery);		
				startActivity(intent);
			}
		});
		lvSavedSearch
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> adapterView,
							View view, int position, long id) {
						onLongListItemClick(view, position, id);
						return false;
					}
				});
	}

	protected void onLongListItemClick(View view, int position, long id) {
		Object o = lvSavedSearch.getItemAtPosition(position);
		ListItemDetails obj_itemDetails = (ListItemDetails) o;
		rowText = obj_itemDetails.getTextHeader();
		rowPosition = position;
		showDialog(0);
	}

	private ArrayList<ListItemDetails> getActiveList() {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;

		resultList = getSavedSearchList();

		if (resultList != null) {

			if (resultList.size() > 0) {

				for (int i = 0; i < resultList.size(); i++) {

					rowList = resultList.get(i);

					ListItemDetails item_details = new ListItemDetails();
					item_details.setTextHeader(rowList.get(1));
					item_details.setConditionStatus(isNotifyEnabled(Integer.parseInt(rowList.get(2))));
					item_details.setConditionOption(getNewResultAvailableFlg());
									 	
					resultsList.add(item_details);

				}

			}
			
		}
		/*ListItemDetails item_details = new ListItemDetails();
		item_details
				.setTextHeader("Sony Bluetooth Smart watch ");
		item_details.setConditionStatus(true);
		item_details.setConditionOption(1);
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Apple iPhone 4S");
		item_details.setConditionStatus(false);
		item_details.setConditionOption(0);
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Toyota corolla 121");
		item_details.setConditionStatus(true);
		item_details.setConditionOption(1);
		resultsList.add(item_details);*/

		return resultsList;
	}

	public Boolean isNotifyEnabled(int notifyFlg){
		
		Boolean notifyEnbaled=false;
		
		switch (notifyFlg) {
		case 1:
			notifyEnbaled=true;
			break;
		}
		return notifyEnbaled;
	}
	
	public int getNewResultAvailableFlg(){
		
		int resultFlg=0,randomNo;
		randomNo=(int)Math.random()*10;
		
		if(randomNo>5){
			resultFlg=1;
		}
		
		return resultFlg;
	}
	protected Dialog onCreateDialog(int id) {

		String arrOptions[] = { "Produt Detail", "Remove" };
		return new AlertDialog.Builder(SavedSearchActivity.this)
				.setTitle("Options")
				.setItems(arrOptions, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						/* User clicked so do some stuff */
						if (which == 0) {

						}
						if (which == 1) {
							removeFromList(rowText, rowPosition,rowText);
						}
					}
				}).create();

	}

	void removeFromList(String listItemText, int position,final String qString) {

		final int positionIndex = position;
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		alertDialog.setTitle("Romove from active list?");
		alertDialog.setMessage(listItemText);

		alertDialog.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						db.deleteFromSavedSearchList(userID, qString);
						itemSavedSearch.remove(positionIndex);
						lvSavedSearch.setAdapter(new SavedSearchBaseAdapter(
								SavedSearchActivity.this, itemSavedSearch));
						Toast.makeText(SavedSearchActivity.this,
								"Item removed from the list", Toast.LENGTH_LONG)
								.show();

					}
				});

		alertDialog.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		alertDialog.show();

	}

	public void onClickMoreOption(View v) {
		showDialog(0);

	}

	public void onClickDepartment(View v) {
		showDialog(1);

	}
	ArrayList<ArrayList> getSavedSearchList(){

		ArrayList<ArrayList> resultList;				
		resultList=db.getSavedSearch(userID);	
		
		return resultList;
		
	}
}
