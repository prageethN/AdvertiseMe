package com.advertiseme.deal;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adveriseme.category.CategoryListActivity;
import com.advertiseme.advertisement.AdvertListActivity;
import com.advertiseme.advertisement.AdvertisementActivity;
import com.advertiseme.startpanel.Feature2Activity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

public class TodayDealActivity extends StartPanelActivity{
	
	ListView lvBestDealList;
	ArrayList<ListItemDetails> itemBestDealList;
	
	ImageView imgDailyDeal;
	TextView txtDailyDealName,txtListPrice,txtSellPrice;
	RelativeLayout lnTodayDeal;
	
	String advertID;
	WebServiceCall webServiceCall;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_today_deal);
	    setTitleFromActivityLabel (R.id.title_text);
	    setupStart();
	}
	
	void setupStart(){
		
		webServiceCall=new WebServiceCall();
		
		imgDailyDeal=(ImageView)findViewById(R.id.imgDailyDeal);
		txtDailyDealName=(TextView)findViewById(R.id.txtDailyDealName);
		txtListPrice=(TextView)findViewById(R.id.txtListPrice);
		txtSellPrice=(TextView)findViewById(R.id.txtSellPrice);
		
		lnTodayDeal=(RelativeLayout)findViewById(R.id.lnTodayDeal);
		
		setDailyDealDetail();
		
		itemBestDealList= getDealList();
		lvBestDealList = (ListView) findViewById(R.id.lvBestDealList);
		lvBestDealList.setAdapter(new BestDealBaseAdapter(this,
				itemBestDealList));
		lvBestDealList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvBestDealList.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;
				
				String advertID=obj_itemDetails.getItemId();
				Intent intent=new Intent(TodayDealActivity.this,
						DealDetailActivity.class);
				intent.putExtra("ADVERT_ID",advertID);
				startActivity(intent);
			}
		});
		
	}
	
	private void setDailyDealDetail(){
		
		String previewURL;
		String[] arrDailyDeal = webServiceCall.getDailyDealItem();
		
		if(arrDailyDeal!=null){
			
			advertID=arrDailyDeal[0];
			txtDailyDealName.setText(arrDailyDeal[1]);
			txtListPrice.setText(arrDailyDeal[2]);
			txtListPrice.setPaintFlags(txtListPrice.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
			txtSellPrice.setText(arrDailyDeal[3]);
			
			previewURL=arrDailyDeal[4];
			Bitmap bimage = getBitmapFromURL(previewURL);
			imgDailyDeal.setImageBitmap(bimage);
		}else{
			lnTodayDeal.setVisibility(android.view.View.INVISIBLE);
		}
		
	}
	private ArrayList<ListItemDetails> getDealList() {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList=null;
		ArrayList<String> rowList=null;
		
				
		resultList=webServiceCall.getBestDealList("");
		
		if(resultList.size()>0){
			
		for(int i=0;i<resultList.size();i++){
			
				rowList=resultList.get(i);
				
				ListItemDetails item_details = new ListItemDetails();
				item_details.setItemID(rowList.get(0));
				item_details.setTextHeader(rowList.get(1));
				item_details.setTextNormal(rowList.get(2));
				item_details.setPreviewURL(rowList.get(3));												
				resultsList.add(item_details);
				
		}
		
		}
		
		/*
		ListItemDetails item_details = new ListItemDetails();
		item_details.setTextHeader("Sony Bluetooth Smart watch MN2 for Android Scratch water proof Resistant -US");
		item_details.setTextNormal(getResources().getString(R.string.about_intro));
		item_details.setTextFooter("link");		
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("50% off deal for iPhone 5");
		item_details.setTextNormal(getResources().getString(R.string.about_intro));
		item_details.setTextFooter("link");		
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Toyota coparation (10)");
		item_details.setTextNormal(getResources().getString(R.string.about_intro));
		item_details.setTextFooter("link");	
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Geometry and text drawn with this style will be both filled,Geometry and text drawn with this style will be both filled");
		item_details.setTextNormal(getResources().getString(R.string.about_intro));
		item_details.setTextFooter("link");
		resultsList.add(item_details);*/

		return resultsList;
	}
	
	public void onClickTodayDeal(View view){
		
		Intent intent= new Intent(TodayDealActivity.this,
				DealDetailActivity.class);
		intent.putExtra("ADVERT_ID",advertID);
		startActivity(intent);
		
	}
	
	public static Bitmap getBitmapFromURL(String src) {
		try {
			Log.e("src", src);
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			Log.e("Bitmap", "returned");
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("Exception", e.getMessage());
			return null;
		}
	}
}
