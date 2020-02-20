package com.advertiseme.deal;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.advertiseme.advertisement.AdvertDetailActivity;
import com.advertiseme.advertisement.AdvertisementActivity;
import com.advertiseme.resource.ResourceActivity;
import com.advertiseme.seller.SellerDetailActivity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.webservicecall.WebServiceCall;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class DealDetailActivity extends StartPanelActivity implements OnClickListener {
	
	TextView testbar1, txtAdvertTitle, txtAdvertDetail, txtListPriceValue,
	txtPriceValue, txtSaveValue, txtAvailability, txtReviewCount,
	txtDealDescription;
	ImageView imgResourcePreview, imgStar;
	RatingBar ratingbar;
	
	String advertID,previewURL;
	WebServiceCall webServiceCall;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView (R.layout.activity_deal_detail);
	    setTitleFromActivityLabel (R.id.title_text);
	    
	    setStartUp();
	    
	}
	
	void setStartUp(){
		
		webServiceCall = new WebServiceCall();
		
		advertID = getIntent().getExtras().getSerializable("ADVERT_ID")
		.toString();
		
				
		txtAdvertTitle = (TextView) findViewById(R.id.txtAdvertTitle);
		txtAdvertDetail = (TextView) findViewById(R.id.txtAdvertDetail);
		txtListPriceValue = (TextView) findViewById(R.id.txtListPriceValue);
		txtPriceValue = (TextView) findViewById(R.id.txtPriceValue);
		txtSaveValue = (TextView) findViewById(R.id.txtSaveValue);
		txtAvailability = (TextView) findViewById(R.id.txtAvailability);
		txtReviewCount = (TextView) findViewById(R.id.txtReviewCount);
		txtDealDescription=(TextView) findViewById(R.id.txtDealDescription);
		
		ratingbar = (RatingBar) findViewById(R.id.ratingbar);
		
		imgResourcePreview = (ImageView) findViewById(R.id.imgResourcePreview);
		imgStar = (ImageView) findViewById(R.id.imgStar);
		
		setAdvertisementDetails(advertID);
		setAdvertRating(advertID);
				
	}
	
	void setAdvertisementDetails(String advertID) {

		String[] arrAdvertDetail = webServiceCall
				.getBestDealAdvertisementDetail(advertID);
				
		txtAdvertTitle.setText(arrAdvertDetail[2]);
		txtAdvertDetail.setText(arrAdvertDetail[2]);
		txtListPriceValue.setText(arrAdvertDetail[13]);
		txtPriceValue.setText(arrAdvertDetail[14]);
		txtSaveValue.setText(arrAdvertDetail[15] + " (" + arrAdvertDetail[17]+ "% off)");
		
		txtDealDescription.setText(arrAdvertDetail[21]);
		
		previewURL=arrAdvertDetail[19];
		Bitmap bimage = getBitmapFromURL(previewURL);
		imgResourcePreview.setImageBitmap(bimage);
		
		setDealAvailability(Integer.parseInt(arrAdvertDetail[20]));
		
	}
	
	void setDealAvailability(int flgAvailable){
		
		switch(flgAvailable){
		
		case 1:
		txtAvailability.setText("Available");break;
		case 0:
		txtAvailability.setText("Not available");break;
		}
	}
	void setAdvertRating(String advertID) {

		String[] arrAdvertRating = webServiceCall
				.getAdvertisementRating(advertID);

		if (arrAdvertRating.length > 0) {

			ratingbar.setRating(Float.parseFloat(arrAdvertRating[0]));
			txtReviewCount.setText("(" + arrAdvertRating[1] + ")");
		
		}

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
	public void onClickPreview(View view){
		
		startActivity(new Intent(getApplicationContext(),
				ResourceActivity.class));		
		
	}
	public void onClickDetail(View view){
		
		Intent intent=new Intent(getApplicationContext(),
				AdvertisementActivity.class);	
		intent.putExtra("ADVERT_ID",advertID);
		startActivity(intent);
	}
	
	public void onClick(View view) {
		// TODO Auto-generated method stub
	
		
	}
}

