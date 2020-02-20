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

public class ExpiredListActivity extends StartPanelActivity{
	
	ListView lvExpiredList;
	ArrayList<ListItemDetails> itemExpiredList;
	TextView txtOptions,txtExpiredListCount;
	String rowText;
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
		setContentView (R.layout.activity_expiredlist);
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
		
		txtExpiredListCount=(TextView)findViewById(R.id.txtExpiredListCount);
		txtExpiredListCount.setText("You have 0 items in the expired list");
		
		itemExpiredList= getActiveListing();
		lvExpiredList = (ListView) findViewById(R.id.lvExpiredList);
		lvExpiredList.setAdapter(new ExpiredListBaseAdapter(this,
				itemExpiredList));
		
		lvExpiredList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {				
				Object o = lvExpiredList.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;

				Intent intent= new Intent(ExpiredListActivity.this,
						AdvertisementActivity.class);
				intent.putExtra("ADVERT_ID", obj_itemDetails.getItemId());
				intent.putExtra("PREVIEW_URL", obj_itemDetails.getPreviewURL());
				
				startActivity(intent);
			}
		});
		lvExpiredList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {				
				onLongListItemClick(view, position, id);
				return false;
			}
		});
	}
	protected void onLongListItemClick(View view, int position, long id) {
		Object o = lvExpiredList.getItemAtPosition(position);
		ListItemDetails obj_itemDetails = (ListItemDetails) o;
		rowText = obj_itemDetails.getTextHeader();
		rowPosition=position;
		showDialog(0);
	}
protected Dialog onCreateDialog(int id) {
		
		String arrOptions[]={"Product Details"};
		 return new AlertDialog.Builder(ExpiredListActivity.this)
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
		 
	 }
void removeFromList(String listItemText, int position) {
		
		final int positionIndex = position;
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		
		
		alertDialog.setTitle("Romove from active listing?");
		alertDialog.setMessage(listItemText);
		
		alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				itemExpiredList.remove(positionIndex);
				lvExpiredList.setAdapter(new WishListBaseAdapter(
						ExpiredListActivity.this, itemExpiredList));
				Toast.makeText(ExpiredListActivity.this, "Successfully Removed from the list", Toast.LENGTH_LONG).show();
				
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
	
	showDialog(0);
	
}
	private ArrayList<ListItemDetails> getActiveListing() {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;

		resultList = webServiceCall.getSellerActiveList(userID,"1");

		if (resultList != null) {

			if (resultList.size() > 0) {

				for (int i = 0; i < resultList.size(); i++) {

					rowList = resultList.get(i);

					ListItemDetails item_details = new ListItemDetails();
					item_details.setItemID(rowList.get(0));
					item_details.setTextHeader(rowList.get(1));
					item_details.setTextNormal(rowList.get(2));
					item_details.setTextFooter(rowList.get(5)+" hits," +rowList.get(4)+" comments");
					item_details.setItemRating(Float.parseFloat(rowList.get(3)));
					item_details.setTextExtra(rowList.get(4));
					item_details.setTextStatus(rowList.get(7));
					item_details.setPreviewURL(rowList.get(6));
									 	
					resultsList.add(item_details);

				}

			}
			txtExpiredListCount.setText("You have "+resultsList.size() +" items in the expired list");
		}
		/*ListItemDetails item_details = new ListItemDetails();
		item_details.setTextHeader("Sony Bluetooth Smart watch MN2 for Android Scratch water proof Resistant -US");
		item_details.setTextNormal("LKR 10,000");
		item_details.setTextFooter("250 hits, 21 comments");
		item_details.setItemRating(4.5f);
		item_details.setTextExtra("21,Sept 2012");		
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Facebook Inc. (312)");
		item_details.setTextNormal("LKR 20,000");
		item_details.setTextFooter("10 hits, 2 comments");
		item_details.setItemRating(4.1f);
		item_details.setTextExtra("20,Sept 2012");		
		resultsList.add(item_details);*/

		return resultsList;
	}
}
