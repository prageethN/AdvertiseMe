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

package com.advertiseme.startpanel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.adveriseme.category.CategoryBaseAdapter;
import com.adveriseme.category.CategoryListActivity;
import com.advertiseme.advertisement.AdvertListActivity;
import com.advertiseme.advertisement.AdvertisementActivity;
import com.advertiseme.deal.DealBaseAdapter;
import com.advertiseme.deal.DealbyCategoryActivity;
import com.advertiseme.myadvertiseme.ActiveListActivity;
import com.advertiseme.myadvertiseme.ActiveListingActivity;
import com.advertiseme.myadvertiseme.BestOfferListActivity;
import com.advertiseme.myadvertiseme.ExpiredListActivity;
import com.advertiseme.myadvertiseme.OfferListActivity;
import com.advertiseme.myadvertiseme.ScheduleListActivity;
import com.advertiseme.myadvertiseme.WishListActivity;
import com.advertiseme.search.SearchNetworkBaseAdapter;
import com.advertiseme.search.SearchPeopleActivity;
import com.advertiseme.search.SearchPeopleBaseAdapter;
import com.advertiseme.search.SearchPeopleActivity.ItemFragment;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.support.ViewPagerIndicator;
import com.advertiseme.webservicecall.WebServiceCall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This is the activity for feature 6 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */

public class Feature6Activity extends FragmentActivity implements
		OnClickListener {
	PagerAdapter mPagerAdapter;
	ViewPager mViewPager;
	static ViewPagerIndicator mIndicator;
	static LinearLayout lnDealList, lnCategoryList;
	static int tabIndex;
	static Context cntx;
	static View view;

	static WebServiceCall webServiceCall;
	SharedPreferences pref;

	String userID;

	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feature6);
		setTitleFromActivityLabel(R.id.title_text);
		
		cntx = getApplicationContext();
		
		webServiceCall = new WebServiceCall();

		pref = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);
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

	}

	class OnIndicatorClickListener implements
			ViewPagerIndicator.OnClickListener {

		public void onCurrentClicked(View v) {
			Toast.makeText(Feature6Activity.this, "Hello",
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
				heading = "Daily Deals";
				break;
			case 1:
				heading = "Category";
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
			View v  = inflater.inflate(R.layout.content_view_deal,
					container, false);
			view = v;
			View tv = v.findViewById(R.id.text);
			lnDealList = (LinearLayout) v.findViewById(R.id.lnDealList);
			lnCategoryList = (LinearLayout) v.findViewById(R.id.lnCategoryList);
			
				ArrayList<ListItemDetails> itemSearchPeople;
				
				final ListView lvSearchPeople = (ListView) v
						.findViewById(R.id.lvDealList);
				itemSearchPeople = getDealList();
				lvSearchPeople.setAdapter(new DealBaseAdapter(cntx,
						itemSearchPeople));
				lvSearchPeople
						.setOnItemClickListener(new OnItemClickListener() {
							public void onItemClick(AdapterView<?> a, View v,
									int position, long id) {
								Object o = lvSearchPeople
										.getItemAtPosition(position);
								ListItemDetails obj_itemDetails = (ListItemDetails) o;								
								Intent intent= new Intent(cntx,
										AdvertisementActivity.class);
								intent.putExtra("ADVERT_ID", obj_itemDetails.getItemId());
								startActivity(intent);
							}
						});
				
		
				ArrayList<ListItemDetails> itemSearchNetwork;
				
				final ListView lvSearchNetwork = (ListView) v
						.findViewById(R.id.lvCategoryList);
				itemSearchNetwork =  getCategoryList();
				lvSearchNetwork.setAdapter(new CategoryBaseAdapter(cntx,
						itemSearchNetwork));
				lvSearchNetwork
						.setOnItemClickListener(new OnItemClickListener() {
							public void onItemClick(AdapterView<?> a, View v,
									int position, long id) {
								Object o = lvSearchNetwork
										.getItemAtPosition(position);
								ListItemDetails obj_itemDetails = (ListItemDetails) o;
								
								Intent intent= new Intent(cntx,
										DealbyCategoryActivity.class);		
								intent.putExtra("CATEGORY_ID", obj_itemDetails.getItemId());	
								intent.putExtra("TYPE_FLG", getCategoryTypeFlg(position));
								startActivity(intent);
							}
						});
				
						
			
			if (tabIndex == 0) {
				lnCategoryList.setVisibility(android.view.View.GONE);
				tabIndex++;	
			}else{
				lnDealList.setVisibility(android.view.View.GONE);
				tabIndex=0;
				}
			
			return v;
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

	
	public static  ArrayList<ListItemDetails> getDealList() {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList=null;
		ArrayList<String> rowList=null;
		
		WebServiceCall webServiceCall=new WebServiceCall();
		
		resultList=webServiceCall.getDailyDealList("userID");
		
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
	public  static ArrayList<ListItemDetails> getCategoryList() {

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
		/*
		ListItemDetails item_details = new ListItemDetails();
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