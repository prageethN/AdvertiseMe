package com.adveriseme.category;

import java.util.ArrayList;

import com.advertiseme.advertisement.AdvertListActivity;
import com.advertiseme.deal.DealbyCategoryActivity;
import com.advertiseme.startpanel.Feature2Activity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CategoryListActivity extends StartPanelActivity{

	ListView lvCategory;
	ArrayList<ListItemDetails> itemCategoryList;
	
	WebServiceCall webServiceCall;
	SharedPreferences pref;

	String userID;

	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_categorylist);
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
		
		itemCategoryList= getCategoryList();
		lvCategory = (ListView) findViewById(R.id.lvCategory);
		lvCategory.setAdapter(new CategoryBaseAdapter(this,
				itemCategoryList));
		lvCategory.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvCategory.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;

				
				Intent intent= new Intent(CategoryListActivity.this,
						AdvertListActivity.class);		
				intent.putExtra("CATEGORY_ID", obj_itemDetails.getItemId());	
				intent.putExtra("TYPE_FLG", getCategoryTypeFlg(position));
				startActivity(intent);
			}
		});
		
	}
	
	int getCategoryTypeFlg(int position){
		
		int typeFlg=-1;
		switch (position) {
		case 0:
			typeFlg=0;
			break;
		case 1:
			typeFlg=1;
			break;
		default:
			typeFlg=2;
			break;
		}
		return typeFlg;
	}
	private ArrayList<ListItemDetails> getCategoryList() {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;

		resultList = webServiceCall.getAdvertCategoryList();

		if (resultList != null) {

			if (resultList.size() > 0) {

				for (int i = 0; i < resultList.size(); i++) {

					rowList = resultList.get(i);

					ListItemDetails item_details = new ListItemDetails();
					item_details.setItemID(rowList.get(0));
					item_details.setTextHeader(rowList.get(1));
														 	
					resultsList.add(item_details);

				}

			}
			
		}
		
		/*ListItemDetails item_details = new ListItemDetails();
		item_details.setTextHeader("Customer favorites");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Staff picks");	
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Best deals from Toyota Lanka");		
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Computers and tablets");
		resultsList.add(item_details);
		
		item_details = new ListItemDetails();
		item_details.setTextHeader("Cell phones and accessories");	
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Cameras and accessories");		
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Electronics");
		resultsList.add(item_details);*/

		return resultsList;
	}
}
