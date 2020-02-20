package com.advertiseme.recent;

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
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.advertiseme.advertisement.AdvertBaseAdapter;
import com.advertiseme.advertisement.AdvertisementActivity;
import com.advertiseme.advertisement.CutomerReviewActivity;
import com.advertiseme.message.MessageActivity;
import com.advertiseme.message.MessageInboxActivity;
import com.advertiseme.startpanel.AboutActivity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

public class UserActivityActivity extends StartPanelActivity implements
		OnClickListener {

	ListView lvActivity;
	ArrayList<ListItemDetails> itemActivityList;

	Button btnDepartment;
	TextView txtHeading;

	WebServiceCall webServiceCall;
	SharedPreferences pref;

	String userID;

	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_activity);
		setTitleFromActivityLabel(R.id.title_text);

		setupStart();
	}

	void setupStart() {

		webServiceCall = new WebServiceCall();

		pref = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);

		try {
			userID = getIntent().getExtras().getSerializable("USER_ID")
					.toString();

		} catch (Exception e) {
			// TODO: handle exception
		}

		txtHeading = (TextView) findViewById(R.id.txtSearchHeading);
		
		itemActivityList = getAdvertList(userID, 1);

		lvActivity = (ListView) findViewById(R.id.lvActivityList);
		lvActivity.setAdapter(new UserActivityBaseAdapter(this, itemActivityList));
		lvActivity.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvActivity.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;
				
				gotoNextActivity(obj_itemDetails.getItemId(),obj_itemDetails.getConditionOption());
				
			}
		});

		
	}

	void gotoNextActivity(String referenceID,int activityType){
		
		Intent intent=null;
		
		switch(activityType){
		
		case 1:
			intent=new Intent(getApplicationContext(),CutomerReviewActivity.class);
			intent.putExtra("ADVERT_ID", referenceID);
		break;
		
		}
		
		startActivity(intent);
	}
	
	private ArrayList<ListItemDetails> getAdvertList(String qString, int typeFlg) {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;

		WebServiceCall webServiceCall = new WebServiceCall();

		resultList = webServiceCall.getUserActivityList(userID);
		

		if (resultList != null) {
			txtHeading.setText("Recent activities " + " (" + resultList.size()
					+ ") ");
			if (resultList.size() > 0) {

				for (int i = 0; i < resultList.size(); i++) {

					rowList = resultList.get(i);

					ListItemDetails item_details = new ListItemDetails();
					item_details.setItemID(getRelavantReferenceID(rowList.get(2),rowList.get(3),rowList.get(4)));
					item_details.setConditionOption(Integer.parseInt(rowList.get(5)));
					item_details.setTextHeader(rowList.get(9)+" "+rowList.get(6));
					item_details.setTextNormal(rowList.get(10));
					item_details.setTextFooter(rowList.get(8));
				
					resultsList.add(item_details);

				}
			}
		}
		return resultsList;
	}
	String getRelavantReferenceID(String advertID,String sellerID,String connectionID){
		
		String referenceID="";
		
		if(!advertID.equalsIgnoreCase("")){
			referenceID=advertID;
		}else if(!sellerID.equalsIgnoreCase("")){
			referenceID=sellerID;			
		}else if(!connectionID.equalsIgnoreCase("")){
			referenceID=connectionID;			
		}
		return referenceID;
		
	}
	

		public void onClick(View view) {



	}

	
}
