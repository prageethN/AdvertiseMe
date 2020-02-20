package com.advertiseme.seller;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.advertiseme.advertisement.ReviewBaseAdapter;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

public class SellerFeedbackActivity extends StartPanelActivity {
	
	ListView lvFeedback;
	ArrayList<ListItemDetails> itemFeedbackList;
	
	WebServiceCall webServiceCall;
	SharedPreferences pref ;
	
	String sellerID,userID;
	
	SharedPreferences prefUser;	

	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	int flgUserRate;
	
	private static final String PREF_ADVERT_PROFILE = "advertProfile";
	private static final String PREF_SELLERID = "sellerID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView (R.layout.activity_seller_feedback);
	    setTitleFromActivityLabel (R.id.title_text);
	    setStartUp();
	}
	
	void setStartUp(){

	    webServiceCall=new WebServiceCall();
		pref = getSharedPreferences(PREF_ADVERT_PROFILE,MODE_PRIVATE);		
		sellerID= pref.getString(PREF_SELLERID, null);
		
		prefUser = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		userID = prefUser.getString(PREF_USERID, null);
		
		itemFeedbackList= getFeedbackList();
		lvFeedback = (ListView) findViewById(R.id.lvFeedbackList);
		lvFeedback.setAdapter(new SellerFeedbackBaseAdapter(this,
				itemFeedbackList));
		lvFeedback.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvFeedback.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;
				
			}
		});
				
	}
	private ArrayList<ListItemDetails> getFeedbackList() {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList=null;
		ArrayList<String> rowList=null;
				
		resultList=webServiceCall.getSellerFeedbackList(sellerID);
		
		if(resultList.size()>0){
			
		for(int i=0;i<resultList.size();i++){
			
				rowList=resultList.get(i);
				
				ListItemDetails item_details = new ListItemDetails();				
				item_details.setTextHeader(rowList.get(2));
				item_details.setTextNormal(rowList.get(0));
				item_details.setTextFooter(rowList.get(3));
				item_details.setConditionOption(Integer.parseInt(rowList.get(1)));
				resultsList.add(item_details);
			
		}
		}
		
		return resultsList;
	}
	

}
