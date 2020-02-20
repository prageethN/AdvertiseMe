package com.advertiseme.advertisement;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.advertiseme.data.DatabaseHandler;
import com.advertiseme.message.MessageActivity;
import com.advertiseme.message.MessageInboxActivity;
import com.advertiseme.startpanel.AboutActivity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;


public class AdvertListActivity extends StartPanelActivity implements OnClickListener{
	
	ListView lvAdvert;
	ArrayList<ListItemDetails> itemAdvertList;
	
	Button btnDepartment;
	ImageButton btnSaveSearch;
	TextView txtSearchQuery;
	String searchQuery="",sellerID="",categoryID="",typeFlg="";
	
	DatabaseHandler db;
	SharedPreferences pref;
	
	String userID;
	
	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_advert_list);
	    setTitleFromActivityLabel (R.id.title_text);
	  
	    setupStart();
	}
	
	void setupStart(){
		
		
		db = new DatabaseHandler(this);

		pref = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);
		
		if (getIntent().getExtras().getSerializable("SEARCH_QUERY")!=null){
			searchQuery = getIntent().getExtras().getSerializable("SEARCH_QUERY")
			.toString();
		}
		if (getIntent().getExtras().getSerializable("SELLER_ID")!=null){
			sellerID= getIntent().getExtras().getSerializable("SELLER_ID")
			.toString();
			
		}
		if (getIntent().getExtras().getSerializable("CATEGORY_ID")!=null){
			categoryID= getIntent().getExtras().getSerializable("CATEGORY_ID")
			.toString();
			
		}
		if (getIntent().getExtras().getSerializable("TYPE_FLG")!=null){
			typeFlg= getIntent().getExtras().getSerializable("TYPE_FLG")
			.toString();
			
		}
		txtSearchQuery=(TextView)findViewById(R.id.txtSearchHeading);
		
		btnDepartment=(Button)findViewById(R.id.btnDepartment);
		btnDepartment.setOnClickListener(this);
		btnSaveSearch=(ImageButton)findViewById(R.id.btnSaveSearch);
		
		if(!searchQuery.equalsIgnoreCase("")){
			itemAdvertList= getAdvertList(searchQuery,0);
		}else if(!sellerID.equalsIgnoreCase("")){
			itemAdvertList= getAdvertList(sellerID,1);
		}else if(!categoryID.equalsIgnoreCase("") && !typeFlg.equalsIgnoreCase("")){
			itemAdvertList= getAdvertList(categoryID,2);
		}
		
		lvAdvert = (ListView) findViewById(R.id.lvAdvertList);
		lvAdvert.setAdapter(new AdvertBaseAdapter(this,
				itemAdvertList));
		lvAdvert.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvAdvert.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;

				Intent intent= new Intent(AdvertListActivity.this,
						AdvertisementActivity.class);
				intent.putExtra("ADVERT_ID", obj_itemDetails.getItemId());
				intent.putExtra("PREVIEW_URL", obj_itemDetails.getPreviewURL());
				
				startActivity(intent);
			}
		});
		
		
		
		
		
	}
	private ArrayList<ListItemDetails> getAdvertList(String qString,int type) {
		
		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();
		
		ArrayList<ArrayList> resultList=new ArrayList<ArrayList>();
		ArrayList<String> rowList=null;
		
		WebServiceCall webServiceCall=new WebServiceCall();
		
		switch(type){
		case 0:
			resultList=webServiceCall.getAdvertSearchResult(qString);
			if(resultList!=null){
				txtSearchQuery.setText(qString+" ("+resultList.size()+") ");break;
			}else{
				txtSearchQuery.setText(qString+" (0) ");break;
			}
			
		case 1:
			btnSaveSearch.setVisibility(android.view.View.GONE);
			resultList=webServiceCall.getAdvertListBySeller(sellerID);
			if(resultList!=null){
				txtSearchQuery.setText("Items by seller "+" ("+resultList.size()+") ");break;
			}else{
				txtSearchQuery.setText("Items by seller (0) ");break;
			}
					
		case 2:
			btnSaveSearch.setVisibility(android.view.View.GONE);
			resultList=webServiceCall.getAdvertListbyCategory(getQueryString(Integer.parseInt(typeFlg)),typeFlg);
			if(resultList!=null){
				txtSearchQuery.setText("Items by category "+" ("+resultList.size()+") ");
			}else{
				txtSearchQuery.setText("Items by category (0) ");
			}
			
			btnDepartment.setVisibility(android.view.View.GONE);break;
			
		}
		
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
		
		}
		
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
	private String[] getDepartmenttList(){
		
		String arrDepartmentList[]=new String[5];
		arrDepartmentList[0]="Computers and tablets (10)";
		arrDepartmentList[1]="Cell phones and accessories (21)";
		arrDepartmentList[2]="Cameras and accessories (102)";
		arrDepartmentList[3]="Computers and tablets (1)";
		arrDepartmentList[4]="Electronics (4)";
		
		return arrDepartmentList;
	}
	
	protected Dialog onCreateDialog(int id) {
		
		 return new AlertDialog.Builder(AdvertListActivity.this)
         .setTitle("Choose Department")
         .setItems(getDepartmenttList(), new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int which) {

                 /* User clicked so do some stuff */
                 String[] items = getDepartmenttList();
                 new AlertDialog.Builder(AdvertListActivity.this)
                         .setMessage("You selected: " + items[which])
                         .show();
             }
         })
         .create();
		 
	 }

	public void onClickSaveSearch(View view){	
			
		db.addSavedSearchItem(userID,searchQuery);
		btnSaveSearch.setVisibility(android.view.View.INVISIBLE);
		toast("Successfully saved to saved search list");
	}
	public void onClick(View view) {
		
		 showDialog(0);
		
	}
	public void onClickMoreOption (View v)
	{
		startActivity (new Intent(getApplicationContext(), AboutActivity.class));
	}
}
