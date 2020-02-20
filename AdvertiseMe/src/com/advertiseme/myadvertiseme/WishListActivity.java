package com.advertiseme.myadvertiseme;

import java.util.ArrayList;

import android.R.color;
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

import com.advertiseme.advertisement.AdvertBaseAdapter;
import com.advertiseme.advertisement.AdvertListActivity;
import com.advertiseme.advertisement.AdvertisementActivity;
import com.advertiseme.data.DatabaseHandler;
import com.advertiseme.favoriteseller.FavoriteSellerBaseAdapter;
import com.advertiseme.startpanel.AboutActivity;
import com.advertiseme.startpanel.Feature5Activity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

public class WishListActivity extends StartPanelActivity{

	ListView lvWishList;
	ArrayList<ListItemDetails> itemWishList;
	TextView txtOptions,txtWishListCount;
	String rowText,advertID;
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
		setContentView (R.layout.activity_wishlist);
	    setTitleFromActivityLabel (R.id.title_text);
	    setupStart();
	}
	void setupStart(){
		
		webServiceCall = new WebServiceCall();
		db = new DatabaseHandler(this);

		pref = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);
		
		try {
			userID = getIntent().getExtras().getSerializable("USER_ID")
			.toString();

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		txtWishListCount=(TextView)findViewById(R.id.txtWishListCount);
		txtWishListCount.setText("You have 0 items in the wish list");
		
		itemWishList= getWishList();
		lvWishList = (ListView) findViewById(R.id.lvWishList);
		lvWishList.setAdapter(new WishListBaseAdapter(this,
				itemWishList));
		
		lvWishList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {				
				Object o = lvWishList.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;

				Intent intent= new Intent(WishListActivity.this,
						AdvertisementActivity.class);
				intent.putExtra("ADVERT_ID", obj_itemDetails.getItemId());
				intent.putExtra("PREVIEW_URL", obj_itemDetails.getPreviewURL());
				
				startActivity(intent);
			}
		});
		lvWishList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {				
				onLongListItemClick(view, position, id);
				return false;
			}
		});
	}
	protected void onLongListItemClick(View view, int position, long id) {
		Object o = lvWishList.getItemAtPosition(position);
		ListItemDetails obj_itemDetails = (ListItemDetails) o;
		advertID= obj_itemDetails.getItemId();
		rowText = obj_itemDetails.getTextHeader();
		rowPosition=position;
		showDialog(0);
	}
	private ArrayList<ListItemDetails> getWishList() {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList=null;
		ArrayList<String> rowList=null;
		
		webServiceCall=new WebServiceCall();
		
		resultList=webServiceCall.getAdvertListByUserPreference(getWishListItems());
				
				
		if(resultList!=null){
			
		for(int i=0;i<resultList.size();i++){
			
				rowList=resultList.get(i);
				
				ListItemDetails item_details = new ListItemDetails();
				item_details.setItemID(rowList.get(0));
				item_details.setTextHeader(rowList.get(1));
				item_details.setTextNormal(rowList.get(2));
				item_details.setTextFooter(rowList.get(3));
				item_details.setItemRating(Float.parseFloat(rowList.get(4)));
				item_details.setTextExtra("("+rowList.get(5)+")");
				item_details.setPreviewURL(rowList.get(6));
				resultsList.add(item_details);
			
		}
		txtWishListCount.setText("You have "+resultList.size()+" items in the wish list");
		}
		/*
		ListItemDetails item_details = new ListItemDetails();
		item_details.setTextHeader("Sony Bluetooth Smart watch MN2 for Android Scratch water proof Resistant -US");
		item_details.setTextNormal("SONY");
		item_details.setTextFooter("LKR 10,000");
		item_details.setItemRating(4.5f);
		item_details.setTextExtra("(124)");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Apple iPhone 4S- 16GB- White");
		item_details.setTextNormal("Apple");
		item_details.setTextFooter("LKR 89,999");
		item_details.setItemRating(4.9f);
		item_details.setTextExtra("(121)");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Toyota Coparation");
		item_details.setTextNormal("Toyota");
		item_details.setTextFooter("LKR 4.8 m");
		item_details.setItemRating(4.5f);
		item_details.setTextExtra("(15)");
		resultsList.add(item_details);

		
		item_details.setTextHeader("Sony Bluetooth Smart watch MN2 for Android Scratch water proof Resistant -US");
		item_details.setTextNormal("Apple");
		item_details.setTextFooter("LKR 10,000");
		item_details.setItemRating(4.5f);
		item_details.setTextExtra("(124)");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Facebook Inc. (312)");
		item_details.setTextNormal("Toyota");
		item_details.setTextFooter("LKR 2.4 m");
		item_details.setItemRating(4.5f);
		item_details.setTextExtra("(15)");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Toyota");
		item_details.setTextNormal("Toyota");
		item_details.setTextFooter("LKR 2.4 m");
		item_details.setItemRating(4.5f);
		item_details.setTextExtra("(15)");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Toyota");
		item_details.setTextNormal("Toyota");
		item_details.setTextFooter("LKR 2.4 m");
		item_details.setItemRating(4.5f);
		item_details.setTextExtra("(15)");
		resultsList.add(item_details);*/
		return resultsList;
	}
	
	String getWishListItems(){
		String qString="";
		
		ArrayList<String> arrList=new ArrayList<String>();
		
		ArrayList<ArrayList<String>> resultList;
		ArrayList<String> rowList;
		
		resultList=db.getWatchList(userID);
		
		if(resultList!=null){
			
		for(int i=0;i<resultList.size();i++){
			
			rowList=resultList.get(i);
			
			if(i==0){
				qString=qString+rowList.get(2);
								
			}else{
				qString=qString+","+rowList.get(2);
			}
		}
		
		}
		return qString;
		
	}
	protected Dialog onCreateDialog(int id) {
		
		String arrOptions[]={"Product Details","Delete"};
		 return new AlertDialog.Builder(WishListActivity.this)
        .setTitle("Options")
        .setItems(arrOptions, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                /* User clicked so do some stuff */                
                if(which==0){
                	startActivity(new Intent(WishListActivity.this,
    						AdvertisementActivity.class));
                 }if(which==1){
                	 removeFromList(rowText,rowPosition,advertID);
                 }
            }
        })
        .create();
		 
	 }
void removeFromList(String listItemText, int position,final String advertID) {
		
		final int positionIndex = position;
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		
		
		alertDialog.setTitle("Romove from wish list?");
		alertDialog.setMessage(listItemText);
		
		alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				db.deleteFromWatchList(userID, advertID);
				itemWishList.remove(positionIndex);
				lvWishList.setAdapter(new WishListBaseAdapter(
						WishListActivity.this, itemWishList));
				Toast.makeText(WishListActivity.this, "Successfully Removed from the list", Toast.LENGTH_LONG).show();
				
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
}
