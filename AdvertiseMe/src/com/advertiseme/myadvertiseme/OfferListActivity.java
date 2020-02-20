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

import com.advertiseme.deal.BuyerDealActivity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

public class OfferListActivity extends StartPanelActivity{

	ListView lvOfferList;
	ArrayList<ListItemDetails> itemOfferList;
	TextView txtSellerOffer,txtOfferListCount;
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
		setContentView (R.layout.activity_offerlist);
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
		
		txtOfferListCount=(TextView)findViewById(R.id.txtOfferListCount);
		txtOfferListCount.setText("You have 0 items in offer list");
		
		itemOfferList= getOfferList();
		lvOfferList = (ListView) findViewById(R.id.lvOfferList);
		lvOfferList.setAdapter(new OfferBaseAdapter(this,
				itemOfferList));
		lvOfferList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {				
				Object o = lvOfferList.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;
				// ACTION TO PERFORM
				Intent intent=new Intent(getApplicationContext(),
						BuyerDealActivity.class);
				intent.putExtra("USER_TYPE", "0"); // buyer
				intent.putExtra("OFFER_ID", obj_itemDetails.getItemId());
				intent.putExtra("ADVERT_ID", obj_itemDetails.getTextStatus()); 
				intent.putExtra("ADVERT_NAME", obj_itemDetails.getTextHeader()); 
				intent.putExtra("SELLER_NAME", obj_itemDetails.getTextNormal()); 
				intent.putExtra("ITEM_PRICE", obj_itemDetails.getTextFooter()); 
				startActivity(intent);
			}
		});
		lvOfferList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {				
				onLongListItemClick(view, position, id);
				return false;
			}
		});
	}
	protected void onLongListItemClick(View view, int position, long id) {
		txtSellerOffer=(TextView)view.findViewById(R.id.text4);
		rowStatus=txtSellerOffer.getText().toString();
		Object o = lvOfferList.getItemAtPosition(position);
		ListItemDetails obj_itemDetails = (ListItemDetails) o;
		rowText = obj_itemDetails.getTextHeader();
		rowPosition=position;
		if(rowStatus.equalsIgnoreCase("Agreed")){
			showDialog(0);
		}else{
			showDialog(1);
		}
		
	}
protected Dialog onCreateDialog(int id) {
		
		
	
	switch (id){
	
	case 0:
	String arrOptions[]={"Product Details","Delete"};
	return new AlertDialog.Builder(OfferListActivity.this)
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
	return new AlertDialog.Builder(OfferListActivity.this)
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
				itemOfferList.remove(positionIndex);
				lvOfferList.setAdapter(new WishListBaseAdapter(
						OfferListActivity.this, itemOfferList));
				Toast.makeText(OfferListActivity.this, "Successfully Removed from the list", Toast.LENGTH_LONG).show();
				
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
	
	if(rowStatus.equalsIgnoreCase("Agreed")){
		showDialog(0);
	}else{
		showDialog(1);
	}
	
}
	private ArrayList<ListItemDetails> getOfferList() {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;

		resultList = webServiceCall.getBuyerOfferList(userID);

		if (resultList != null) {

			if (resultList.size() > 0) {

				for (int i = 0; i < resultList.size(); i++) {

					rowList = resultList.get(i);

					ListItemDetails item_details = new ListItemDetails();
					item_details.setItemID(rowList.get(0));
					item_details.setTextHeader(rowList.get(9));
					item_details.setTextNormal(rowList.get(10));
					item_details.setTextFooter(rowList.get(11));
					item_details.setTextExtra(rowList.get(3));
					item_details.setTextAdditional(rowList.get(4));
					item_details.setConditionOption(Integer.parseInt(rowList.get(5)));
					item_details.setTextStatus(rowList.get(2));
					item_details.setPreviewURL(rowList.get(12));
									 	
					resultsList.add(item_details);

				}

			}
			txtOfferListCount.setText("You have "+resultsList.size() +" items in offer list");
		}
		/*ListItemDetails item_details = new ListItemDetails();
		item_details.setTextHeader("Sony Bluetooth Smart watch MN2 for Android Scratch water proof Resistant -US");
		item_details.setTextNormal("Apple");
		item_details.setTextFooter("LKR 10,000");
		item_details.setTextExtra("LKR 9,000");
		item_details.setTextAdditional("LKR 8,000");
		item_details.setConditionOption(1);
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Facebook Inc. (312)");
		item_details.setTextNormal("Toyota");
		item_details.setTextFooter("LKR 2.4 m");
		item_details.setTextExtra("LKR 2.2");
		item_details.setTextAdditional("LKR 8,000");
		item_details.setConditionOption(0);		
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Google Inc. (10)");
		item_details.setTextNormal("Toyota");
		item_details.setTextFooter("LKR 2.4 m");
		item_details.setTextExtra("LKR 2.2");
		item_details.setTextAdditional("LKR 2.25");
		item_details.setConditionOption(2);	
		
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Toyota coparation (10)");
		item_details.setTextNormal("Toyota");
		item_details.setTextFooter("LKR 2.4 m");
		item_details.setTextExtra("(15)");
		item_details.setTextExtra("LKR 2.2");
		item_details.setTextAdditional("LKR 2.25");
		item_details.setConditionOption(0);	
		resultsList.add(item_details);*/

		return resultsList;
	}
}
