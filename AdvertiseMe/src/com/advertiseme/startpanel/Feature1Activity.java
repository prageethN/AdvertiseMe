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

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.advertiseme.data.DatabaseHandler;
import com.advertiseme.myadvertiseme.ActiveListActivity;
import com.advertiseme.myadvertiseme.ActiveListingActivity;
import com.advertiseme.myadvertiseme.BestOfferListActivity;
import com.advertiseme.myadvertiseme.ExpiredListActivity;
import com.advertiseme.myadvertiseme.OfferListActivity;
import com.advertiseme.myadvertiseme.ScheduleListActivity;
import com.advertiseme.myadvertiseme.WishListActivity;
import com.advertiseme.support.ViewPagerIndicator;
import com.advertiseme.startpanel.R;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Feature1Activity extends FragmentActivity implements
		OnClickListener {
	PagerAdapter mPagerAdapter;
	ViewPager mViewPager;
	static ViewPagerIndicator mIndicator;	
	static LinearLayout lnWtching, lnSelling;
	static TextView txtWishList,txtBuyerActiveList,txtBuyerOfferList,txtSellerActiveList,
	txtSellerOfferList,txtExpiredList,txtScheduledList;
	static int tabIndex,wishListCount=0,activeListCount=0;

	static SharedPreferences pref;
	static String userID;
	
	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feature1);
		setTitleFromActivityLabel(R.id.title_text);

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
		
		setWishListCount();
		setActiveListCount();

	}

	class OnIndicatorClickListener implements
			ViewPagerIndicator.OnClickListener {

		public void onCurrentClicked(View v) {
			Toast.makeText(Feature1Activity.this, "Hello", Toast.LENGTH_SHORT)
					.show();
		}

		public void onNextClicked(View v) {
			mViewPager.setCurrentItem(Math.min(mPagerAdapter.getCount() - 1,
					mIndicator.getCurrentPosition() + 1));
		}

		public void onPreviousClicked(View v) {
			mViewPager.setCurrentItem(Math.max(0,
					mIndicator.getCurrentPosition() - 1));
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
				heading = "Watching";
				break;
			case 1:
				heading = "Selling";
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

			View v = inflater.inflate(R.layout.content_view_myadvertiseme, container, false);
			View tv = v.findViewById(R.id.text);

			lnWtching = (LinearLayout) v.findViewById(R.id.lnWatching);
			lnSelling = (LinearLayout) v.findViewById(R.id.lnSelling);
					
			txtWishList=(TextView)v.findViewById(R.id.txtWishList);
			txtBuyerActiveList=(TextView)v.findViewById(R.id.txtBuyerActiveList);
			txtBuyerOfferList=(TextView)v.findViewById(R.id.txtBuyerOfferList);
			txtSellerActiveList=(TextView)v.findViewById(R.id.txtSellerActiveList);
			txtSellerOfferList=(TextView)v.findViewById(R.id.txtSellerOfferList);
			txtExpiredList=(TextView)v.findViewById(R.id.txtExpiredList);
			txtScheduledList=(TextView)v.findViewById(R.id.txtScheduledList);
			
			
			if (tabIndex == 0) {
				lnSelling.setVisibility(android.view.View.GONE);
				tabIndex++;
			} else {
				lnWtching.setVisibility(android.view.View.GONE);
			}
			
			setMyAdvertiseMeCount();
			
			return v;
		}
		
		static void setMyAdvertiseMeCount(){
			
			WebServiceCall webServiceCall = new WebServiceCall();				
									
			String []resultList = webServiceCall.getMyAdvertiseMeCount(userID);
			
			txtWishList.setText("Wish List ("+wishListCount+")");
			txtBuyerActiveList.setText("Active ("+activeListCount+")");
			
			txtBuyerOfferList.setText("Offeres ("+resultList[0]+")");
			txtSellerActiveList.setText("Active Listing ("+resultList[1]+")");
			txtSellerOfferList.setText("Pending best offeres("+resultList[2]+")");
			txtExpiredList.setText("Expired ("+resultList[3]+")");
			txtScheduledList.setText("Scheduled ("+resultList[4]+")");
			
									
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
	public void setWishListCount(){
		
		DatabaseHandler db=new DatabaseHandler(this);
		wishListCount=db.getWishListCount(userID);
	
	}
	public void setActiveListCount(){
		
		DatabaseHandler db=new DatabaseHandler(this);
		activeListCount=db.getActiveListCount(userID);
	
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
		startActivity(new Intent(getApplicationContext(), AboutActivity.class));
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

	public void onClickMoreOption(View v) {
		startActivity(new Intent(getApplicationContext(), AboutActivity.class));
	}
}