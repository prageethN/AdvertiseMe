package com.advertiseme.deal;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.adveriseme.category.CategoryListActivity;
import com.advertiseme.advertisement.AdvertListActivity;
import com.advertiseme.advertisement.AdvertisementActivity;
import com.advertiseme.data.DatabaseHandler;
import com.advertiseme.startpanel.Feature2Activity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

public class DealbyCategoryActivity extends StartPanelActivity{
	
	ListView lvDeal;
	ArrayList<ListItemDetails> itemDealList;
	
	TextView txtItemCount;
	
	WebServiceCall webServiceCall;
	DatabaseHandler db;
	SharedPreferences pref;

	String userID,categoryID,typeFlg;

	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_dealbycategory_list);
	    setTitleFromActivityLabel (R.id.title_text);
	    setupStart();
	}
	
	void setupStart(){
		
		webServiceCall = new WebServiceCall();
		db = new DatabaseHandler(this);
		
		pref = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);
		
		if (getIntent().getExtras().getSerializable("CATEGORY_ID")!=null){
			categoryID= getIntent().getExtras().getSerializable("CATEGORY_ID")
			.toString();
			
		}
		if (getIntent().getExtras().getSerializable("TYPE_FLG")!=null){
			typeFlg= getIntent().getExtras().getSerializable("TYPE_FLG")
			.toString();
			
		}
		
		txtItemCount=(TextView)findViewById(R.id.txtItemCount);
		
		itemDealList= getDealList();
		lvDeal = (ListView) findViewById(R.id.lvDealList);
		lvDeal.setAdapter(new DealBaseAdapter(this,
				itemDealList));
		lvDeal.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvDeal.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;

				Intent intent= new Intent(DealbyCategoryActivity.this,
						AdvertisementActivity.class);
				intent.putExtra("ADVERT_ID", obj_itemDetails.getItemId());
				intent.putExtra("PREVIEW_URL", obj_itemDetails.getPreviewURL());
				
				startActivity(intent);
			}
		});
		
	}
	private ArrayList<ListItemDetails> getDealList() {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList=new ArrayList<ArrayList>();
		ArrayList<String> rowList=null;
		
		webServiceCall=new WebServiceCall();
		
		resultList=webServiceCall.getDealListbyCategory(getQueryString(Integer.parseInt(typeFlg)),typeFlg);
		if(resultList!=null){
			
			for(int i=0;i<resultList.size();i++){
				
					rowList=resultList.get(i);
					
					ListItemDetails item_details = new ListItemDetails();
					item_details.setItemID(rowList.get(0));
					item_details.setTextHeader(rowList.get(1));
					item_details.setTextNormal(rowList.get(4)+"% off");
					item_details.setTextFooter(rowList.get(3));				
					item_details.setTextExtra(rowList.get(2));
					item_details.setPreviewURL(rowList.get(5));
					resultsList.add(item_details);
				
			}
			txtItemCount.setText("You have "+resultList.size()+" items in this category");
			}
		/*ListItemDetails item_details = new ListItemDetails();
		item_details.setTextHeader("Sony Bluetooth Smart watch MN2 for Android Scratch water proof Resistant -US");
		item_details.setTextNormal("50% off");
		item_details.setTextFooter("LKR 10,000");
		item_details.setTextExtra("LKR 20,000");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Facebook Inc. (312)");
		item_details.setTextNormal("50% off");
		item_details.setTextFooter("LKR 10,000");
		item_details.setTextExtra("LKR 20,000");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Toyota coparation (10)");
		item_details.setTextNormal("50% off");
		item_details.setTextFooter("LKR 10,000");
		item_details.setTextExtra("LKR 20,000");	
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Geometry and text drawn with this style will be both filled,Geometry and text drawn with this style will be both filled");
		item_details.setTextNormal("50% off");
		item_details.setTextFooter("LKR 10,000");
		item_details.setTextExtra("LKR 20,000");
		resultsList.add(item_details);*/

		return resultsList;
	}
String getQueryString(int typeFlg){
		
		String qString="";
		if(typeFlg==0){
			
			qString=getFavoriteSellerList();
		}
		if(typeFlg==1){
			qString=categoryID;
		}
		if(typeFlg==2){
			qString=categoryID;
			
		}
		return qString;
	}
String getFavoriteSellerList(){
	String qString="";
	
	ArrayList<String> arrList=new ArrayList<String>();
	
	ArrayList<ArrayList<String>> resultList;
	ArrayList<String> rowList;
	
	resultList=db.getFavoriteSeller(userID);
	
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
}
