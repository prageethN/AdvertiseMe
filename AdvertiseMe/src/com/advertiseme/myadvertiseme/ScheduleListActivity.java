package com.advertiseme.myadvertiseme;

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

import com.advertiseme.advertisement.AdvertisementActivity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

public class ScheduleListActivity extends StartPanelActivity{

	ListView lvScheduleList;
	ArrayList<ListItemDetails> itemScheduleList;
	TextView txtSellerOffer,txtScheduledListCount;
	String rowText,rowStatus="";
	int rowPosition;
	
	WebServiceCall webServiceCall;
	SharedPreferences pref;

	String userID;

	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_sceduledlist);
	    setTitleFromActivityLabel (R.id.title_text);
	    setupStart();
	}
	
	void setupStart(){
		
		webServiceCall = new WebServiceCall();

		pref = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);
		
		try {
			userID = getIntent().getExtras().getSerializable("USER_ID")
			.toString();

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		txtScheduledListCount=(TextView)findViewById(R.id.txtScheduledListCount);
		txtScheduledListCount.setText("You have 0 items in the scheduled list");
		
		
		itemScheduleList= getOfferList();
		lvScheduleList = (ListView) findViewById(R.id.lvScheduledList);
		lvScheduleList.setAdapter(new ScheduleBaseAdapter(this,
				itemScheduleList));
		lvScheduleList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {				
				Object o = lvScheduleList.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;

				Intent intent= new Intent(ScheduleListActivity.this,
						AdvertisementActivity.class);
				intent.putExtra("ADVERT_ID", obj_itemDetails.getItemId());
				intent.putExtra("PREVIEW_URL", obj_itemDetails.getPreviewURL());
				
				startActivity(intent);
			}
		});
		lvScheduleList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {				
				onLongListItemClick(view, position, id);
				return false;
			}
		});
	}
	protected void onLongListItemClick(View view, int position, long id) {
		
		Object o = lvScheduleList.getItemAtPosition(position);
		ListItemDetails obj_itemDetails = (ListItemDetails) o;
		rowText = obj_itemDetails.getTextHeader();
		rowPosition=position;
		showDialog(1);
	
	}
protected Dialog onCreateDialog(int id) {
		
		
	
	switch (id){
	
	case 0:
	String arrOptions[]={"Product Details","Delete"};
	return new AlertDialog.Builder(ScheduleListActivity.this)
    .setTitle("Options")
    .setItems(arrOptions, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {

            /* User clicked so do some stuff */                
            if(which==0){
            	
             }if(which==1){
            	 removeFromList(rowText,rowPosition);
             }
        }
    })
    .create();
	
	case 1:
	String arrOptions2[]={"Product Details"};
	return new AlertDialog.Builder(ScheduleListActivity.this)
    .setTitle("Options")
    .setItems(arrOptions2, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {

            /* User clicked so do some stuff */                
            if(which==0){
            	
             }if(which==1){
            	 removeFromList(rowText,rowPosition);
             }
        }
    })
    .create();
	}	
	
		return null; 
	 }
void removeFromList(String listItemText, int position) {
		
		final int positionIndex = position;
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		
		
		alertDialog.setTitle("Romove from offers?");
		alertDialog.setMessage(listItemText);
		
		alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				itemScheduleList.remove(positionIndex);
				lvScheduleList.setAdapter(new WishListBaseAdapter(
						ScheduleListActivity.this, itemScheduleList));
				Toast.makeText(ScheduleListActivity.this, "Successfully Removed from the list", Toast.LENGTH_LONG).show();
				
			}
		});

		alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		alertDialog.show();

	}
public void onClickMoreOption (View v)
{
		
		showDialog(1);

	}
	private ArrayList<ListItemDetails> getOfferList() {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;

		resultList = webServiceCall.getScheduledAdvertList(userID);

		if (resultList != null) {

			if (resultList.size() > 0) {

				for (int i = 0; i < resultList.size(); i++) {

					rowList = resultList.get(i);

					ListItemDetails item_details = new ListItemDetails();
					item_details.setItemID(rowList.get(0));
					item_details.setTextHeader(rowList.get(1));
					item_details.setTextNormal(rowList.get(2));
					item_details.setTextFooter("From "+rowList.get(3));
					item_details.setTextExtra(rowList.get(4)+" months");
					item_details.setPreviewURL(rowList.get(5));
									 	
					resultsList.add(item_details);

				}

			}
			txtScheduledListCount.setText("You have "+resultsList.size() +" items in the scheduled list");
		}
		
		/*ListItemDetails item_details = new ListItemDetails();
		item_details.setTextHeader("Sony Bluetooth Smart watch MN2 for Android Scratch water proof Resistant -US");
		item_details.setTextNormal("LKR 10,000");
		item_details.setTextFooter("From 26,Sept 2012");
		item_details.setTextExtra("3 months");		
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Facebook Inc. (312)");
		item_details.setTextNormal("LKR 2.4 m");
		item_details.setTextFooter("From 20,Sept 2012");
		item_details.setTextExtra("2 weeks");			
		resultsList.add(item_details);*/

		return resultsList;
	}
}
