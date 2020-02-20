/*
 * Copyright (C) 2011 Wglxy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.advertiseme.search;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.advertiseme.message.MessageBaseAdapter;
import com.advertiseme.myadvertiseme.ActiveListActivity;
import com.advertiseme.myadvertiseme.ActiveListingActivity;
import com.advertiseme.myadvertiseme.BestOfferListActivity;
import com.advertiseme.myadvertiseme.ExpiredListActivity;
import com.advertiseme.myadvertiseme.OfferListActivity;
import com.advertiseme.myadvertiseme.ScheduleListActivity;
import com.advertiseme.myadvertiseme.WishListActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.support.ViewPagerIndicator;
import com.advertiseme.startpanel.AboutActivity;
import com.advertiseme.startpanel.Feature2Activity;
import com.advertiseme.startpanel.HomeActivity;
import com.advertiseme.startpanel.R;
import com.advertiseme.user.UserProfileActivity;
import com.advertiseme.webservicecall.WebServiceCall;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchPeopleActivity extends FragmentActivity implements
		OnClickListener {
	PagerAdapter mPagerAdapter;
	ViewPager mViewPager;
	static ViewPagerIndicator mIndicator;
	// static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	// static SimpleDateFormat readableDateFormat = new
	// SimpleDateFormat("yyyy - MM/dd");
	static LinearLayout lnContact, lnNetwork;
	static int tabIndex;
	static Context cntx;
	static View view;
	static String userID;
	
	SharedPreferences pref ;
	
	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	static EditText txtSearchPeople; 
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchpeople);
		setTitleFromActivityLabel(R.id.title_text);
		cntx = getApplicationContext();
		
		pref = getSharedPreferences(PREF_PROFILE,MODE_PRIVATE);
		userID=pref.getString(PREF_USERID, null);
			
		tabIndex = 0;
		// Create our custom adapter to supply pages to the viewpager.
		mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mPagerAdapter);

		// Start at a custom position
		mViewPager.setCurrentItem(0);

		// Find the indicator from the layout
		mIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);

		// Set the indicator as the pageChangeListener
		mViewPager.setOnPageChangeListener(mIndicator);

		// Initialize the indicator. We need some information here:
		// * What page do we start on.
		// * How many pages are there in total
		// * A callback to get page titles
		mIndicator.init(0, mPagerAdapter.getCount(), mPagerAdapter);
		Resources res = getResources();
		Drawable prev = res.getDrawable(R.drawable.icn_back_indicator);
		Drawable next = res.getDrawable(R.drawable.icn_next_indicator);
		mIndicator.setFocusedTextColor(new int[] { 255, 255, 255 });

		// Set images for previous and next arrows.
		mIndicator.setArrows(prev, next);

		mIndicator.setOnClickListener(new OnIndicatorClickListener());
		
		
		txtSearchPeople = (EditText) findViewById(R.id.txtSearchPeople);
		txtSearchPeople.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	        	
	        	if(tabIndex==0){
	        		
	        		getSearchResultContact(userID,txtSearchPeople.getText().toString());
	        	}
	        	
	        
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){}
	    });

	}

	class OnIndicatorClickListener implements
			ViewPagerIndicator.OnClickListener {

		public void onCurrentClicked(View v) {
			Toast.makeText(SearchPeopleActivity.this, "Hello",
					Toast.LENGTH_SHORT).show();
			
		}

		public void onNextClicked(View v) {
			mViewPager.setCurrentItem(Math.min(mPagerAdapter.getCount() - 1,
					mIndicator.getCurrentPosition() + 1));
			tabIndex=1;
		}

		public void onPreviousClicked(View v) {
			mViewPager.setCurrentItem(Math.max(0,
					mIndicator.getCurrentPosition() - 1));
			tabIndex=0;
		}

	}

	class PagerAdapter extends FragmentPagerAdapter implements
			ViewPagerIndicator.PageInfoProvider {
		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public Fragment getItem(int pos) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, pos - getCount() / 2);
			return ItemFragment.newInstance(cal.getTime());
		}

		public int getCount() {
			return 2;
		}

		public String getTitle(int pos) {
			String heading = "";
			switch (pos) {
			case 0:
				heading = "Contacts";
				break;
			case 1:
				heading = "Network";
				break;
			}
			return heading;
		}
	}

	public static class ItemFragment extends ListFragment implements
			OnClickListener {
		Date date;

		static ItemFragment newInstance(Date date) {
			ItemFragment f = new ItemFragment();

			// Supply num input as an argument.
			Bundle args = new Bundle();

			// args.putString("date", sdf.format(date));
			f.setArguments(args);

			return f;
		}

		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			try {

				// this.date = sdf.parse(getArguments().getString("date"));

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
						
			View v  = inflater.inflate(R.layout.content_view_searchpeople,
					container, false);
			view = v;
			View tv = v.findViewById(R.id.text);
			lnContact = (LinearLayout) v.findViewById(R.id.lnSearchContact);
			lnNetwork = (LinearLayout) v.findViewById(R.id.lnSearchContact_);
			
				ArrayList<ListItemDetails> itemSearchPeople;
				
				final ListView lvSearchPeople = (ListView) v
						.findViewById(R.id.lvSearchContact);
				lvSearchPeople.setVisibility(android.view.View.VISIBLE);
				itemSearchPeople = getContactList(userID,"");
				lvSearchPeople.setAdapter(new SearchPeopleBaseAdapter(cntx,
						itemSearchPeople));
				lvSearchPeople
						.setOnItemClickListener(new OnItemClickListener() {
							public void onItemClick(AdapterView<?> a, View v,
									int position, long id) {
								Object o = lvSearchPeople
										.getItemAtPosition(position);
								ListItemDetails obj_itemDetails = (ListItemDetails) o;
								
								String userID=obj_itemDetails.getItemId();
								Intent intent=new Intent(cntx,
										UserProfileActivity.class);
								intent.putExtra("USER_ID", userID);
								intent.putExtra("USER_TYPE", 0);
								startActivity(intent);
							}
						});
				
		
				ArrayList<ListItemDetails> itemSearchNetwork;
				
				final ListView lvSearchNetwork = (ListView) v
						.findViewById(R.id.lvSearchContact_);
				itemSearchNetwork = getNetworkList(userID,"");
				lvSearchNetwork.setAdapter(new SearchNetworkBaseAdapter(cntx,
						itemSearchNetwork));
				lvSearchNetwork
						.setOnItemClickListener(new OnItemClickListener() {
							public void onItemClick(AdapterView<?> a, View v,
									int position, long id) {
								Object o = lvSearchNetwork
										.getItemAtPosition(position);
								ListItemDetails obj_itemDetails = (ListItemDetails) o;
								// ACTION TO PERFORM
								String userID=obj_itemDetails.getItemId();
								Intent intent=new Intent(cntx,
										UserProfileActivity.class);
								intent.putExtra("USER_ID", userID);
								intent.putExtra("USER_TYPE", 2);
								startActivity(intent);
							}
						});
				
						
			
			if (tabIndex == 0) {
				lnNetwork.setVisibility(android.view.View.GONE);
				tabIndex++;	
			}else{
				lnContact.setVisibility(android.view.View.GONE);
				tabIndex=0;
				}
			
			return v;
		}

		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			// setListAdapter(new ArrayAdapter<String>(getActivity(),
			// android.R.layout.simple_list_item_1, list));
		}

		public void onListItemClick(ListView l, View v, int position, long id) {
			Log.i("FragmentList", "Item clicked: " + id);
		}

		public void onClick(View view) {
			// TODO Auto-generated method stub

		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public void onClickWishList(View v) {

		startActivity(new Intent(getApplicationContext(),
				WishListActivity.class));
	}

	public void onClickActiveList(View v) {

		startActivity(new Intent(getApplicationContext(),
				ActiveListActivity.class));
	}

	public void onClickOfferList(View v) {

		startActivity(new Intent(getApplicationContext(),
				OfferListActivity.class));
	}

	public void onClickActiveListing(View v) {

		startActivity(new Intent(getApplicationContext(),
				ActiveListingActivity.class));
	}

	public void onClickPendingList(View v) {

		startActivity(new Intent(getApplicationContext(),
				BestOfferListActivity.class));
	}

	public void onClickExpiredList(View v) {

		startActivity(new Intent(getApplicationContext(),
				ExpiredListActivity.class));
	}

	public void onClickScheduledList(View v) {

		startActivity(new Intent(getApplicationContext(),
				ScheduleListActivity.class));
	}

	public void onClickSearch(View v) {
		startActivity(new Intent(getApplicationContext(),
				Feature2Activity.class));
	}
	
	static void getSearchResultContact(String userID,String searchQuery){
				
		ArrayList<ListItemDetails> itemSearchPeople;
		
		final ListView lvSearchPeople = (ListView) view
				.findViewById(R.id.lvSearchContact);
		
		itemSearchPeople = getContactList(userID,searchQuery);
		if(itemSearchPeople.isEmpty()){
			itemSearchPeople.clear();
		}
		lvSearchPeople.setAdapter(new SearchPeopleBaseAdapter(cntx,
				itemSearchPeople));
		lvSearchPeople
				.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> a, View v,
							int position, long id) {
						Object o = lvSearchPeople
								.getItemAtPosition(position);
						ListItemDetails obj_itemDetails = (ListItemDetails) o;
						
						String userID=obj_itemDetails.getItemId();
						Intent intent=new Intent(cntx,
								UserProfileActivity.class);
						intent.putExtra("USER_ID", userID);
				
						
					}
				});
	}
	static void getSearchResultNetWork(String userID,String searchQuery){
		
		ArrayList<ListItemDetails> itemSearchPeople;
		
		final ListView lvSearchPeople = (ListView) view
				.findViewById(R.id.lvSearchContact_);
		
		itemSearchPeople = getNetworkList(userID,searchQuery);
		if(itemSearchPeople.isEmpty()){
			itemSearchPeople.clear();
		}
		lvSearchPeople.setAdapter(new SearchPeopleBaseAdapter(cntx,
				itemSearchPeople));
		lvSearchPeople
				.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> a, View v,
							int position, long id) {
						Object o = lvSearchPeople
								.getItemAtPosition(position);
						ListItemDetails obj_itemDetails = (ListItemDetails) o;
						
						String userID=obj_itemDetails.getItemId();
						Intent intent=new Intent(cntx,
								UserProfileActivity.class);
						intent.putExtra("USER_ID", userID);
						
					}
				});
	}
	
	public void onClickAbout(View v) {
		 startActivity(new Intent(getApplicationContext(),
		 AboutActivity.class));
	
	}

	public void onClickHome(View v) {
		goHome(this);
	}

	public void goHome(Context context) {
		final Intent intent = new Intent(context, HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	public void setTitleFromActivityLabel(int textViewId) {
		TextView tv = (TextView) findViewById(textViewId);
		if (tv != null)
			tv.setText(getTitle());
	} // end setTitleText

	
	private static ArrayList<ListItemDetails> getContactList(String userID,String searchQuery) {

		WebServiceCall webServiceCall=new WebServiceCall();
		
		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList=null;
		ArrayList<String> rowList=null;
		
				
		resultList=webServiceCall.getUserConnectionList(userID,searchQuery);
		if(resultList!=null){
			
		if(resultList.size()>0){
			
		for(int i=0;i<resultList.size();i++){
			
				rowList=resultList.get(i);
				
				ListItemDetails item_details = new ListItemDetails();
				item_details.setItemID(rowList.get(0));
				item_details.setTextHeader(rowList.get(5));
				item_details.setTextNormal(rowList.get(4));
				item_details.setTextFooter(getLocationString(rowList.get(1),rowList.get(2),rowList.get(3)));
				item_details.setPreviewURL(rowList.get(6));												
				resultsList.add(item_details);
				
		}
		
		}
		}
		
		/*
		ListItemDetails item_details = new ListItemDetails();
		item_details.setTextHeader("Dinusha87");
		item_details.setTextNormal("Peradeniya,Central,Sri Lanka");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("NipunaDev");
		item_details.setTextNormal("Anuradhapura");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Dinusha87");
		item_details.setTextNormal("Peradeniya,Central,Sri Lanka");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("NipunaDev");
		item_details.setTextNormal("Anuradhapura");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Dinusha87");
		item_details.setTextNormal("Peradeniya,Central,Sri Lanka");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("NipunaDev");
		item_details.setTextNormal("Anuradhapura");
		resultsList.add(item_details);*/

		return resultsList;
	}
	
	
	static String getLocationString(String strCity,String strState,String strCountry){
		String defaultText="";
		String locationString="";
		
		if(!strCity.equalsIgnoreCase(defaultText)){
			locationString=locationString+strCity+",";
		}
		if(!strState.equalsIgnoreCase(defaultText)){
			locationString=locationString+strState+",";
		}
		if(!strCountry.equalsIgnoreCase(defaultText)){
			locationString=locationString+strCountry;
		}
		
		return locationString;
	}
	private static ArrayList<ListItemDetails> getNetworkList(String userID,String searchQuery) {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		WebServiceCall webServiceCall=new WebServiceCall();		
		
		ArrayList<ArrayList> resultList=null;
		ArrayList<String> rowList=null;
		
				
		resultList=webServiceCall.getNetwokConnectionList(userID,searchQuery);
		if(resultList!=null){
			
		if(resultList.size()>0){
			
		for(int i=0;i<resultList.size();i++){
			
				rowList=resultList.get(i);
				
				ListItemDetails item_details = new ListItemDetails();
				item_details.setItemID(rowList.get(0));
				item_details.setTextHeader(rowList.get(5));
				item_details.setTextNormal(rowList.get(4));
				item_details.setTextFooter(getLocationString(rowList.get(1),rowList.get(2),rowList.get(3)));
				item_details.setPreviewURL(rowList.get(6));												
				resultsList.add(item_details);
				
		}
		
		}
		}
		/*ListItemDetails item_details = new ListItemDetails();
		item_details.setTextHeader("Dinusha87");
		item_details.setTextNormal("Peradeniya,Central,Sri Lanka");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("NipunaDev");
		item_details.setTextNormal("Anuradhapura");
		resultsList.add(item_details);*/

		return resultsList;
	}

}