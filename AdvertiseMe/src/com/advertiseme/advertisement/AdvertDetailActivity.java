package com.advertiseme.advertisement;


import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.webservicecall.WebServiceCall;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class AdvertDetailActivity extends StartPanelActivity {
	
	int detailStatus=0,featureStatus=0,descStatus=0;
	View viewFeature,viewDesc,viewDetail;
	TextView txtFeature,txtDesc,txtDetail,txtProductName,txtSeller,txtPrice,txtReviewCount;
	
		
	String advertName,sellerName,itemPrice,itemFeatures,itemDesc,itemDetail;
	
	private static final String PREF_ADVERT_PROFILE = "advertProfile";
	private static final String PREF_SELLER_NAME = "sellerName";
	private static final String PREF_ADVERT_TITLE = "advertTitle";
	private static final String PREF_ADVERT_PRICE = "advertPrice";
		
	SharedPreferences pref ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView (R.layout.activity_advert_detail);
	    setTitleFromActivityLabel (R.id.title_text);
	    setStartUp();
	}
	
	void setStartUp(){
		
		viewFeature=findViewById(R.id.viewFeature);
		viewDesc=findViewById(R.id.viewDesc);
		viewDetail=findViewById(R.id.viewDetail);
		
		txtFeature=(TextView)findViewById(R.id.txtFeature);
		txtDesc=(TextView)findViewById(R.id.txtDesc);
		txtDetail=(TextView)findViewById(R.id.txtDetail);
		
		txtProductName=(TextView)findViewById(R.id.txtProductName);
		txtSeller=(TextView)findViewById(R.id.txtSeller);
		txtPrice=(TextView)findViewById(R.id.txtPrice);
		
		pref = getSharedPreferences(PREF_ADVERT_PROFILE,MODE_PRIVATE);	
		
	    itemFeatures=getIntent().getExtras().getSerializable("ADVERT_FEATURES")
		.toString();
	    itemDesc=getIntent().getExtras().getSerializable("ADVERT_DESC")
		.toString();
	    itemDetail=getIntent().getExtras().getSerializable("ADVERT_DETAIL")
		.toString();
	    
	    setAdvertisementDetail();
	 
	}
	
	void setAdvertisementDetail(){
		
		txtProductName.setText( pref.getString(PREF_ADVERT_TITLE, null));
		txtSeller.setText(pref.getString(PREF_SELLER_NAME, null));
		txtPrice.setText(pref.getString(PREF_ADVERT_PRICE, null));
		txtFeature.setText(itemFeatures);
		txtDesc.setText(itemDesc);
		txtDetail.setText(itemDetail);
	}
	public void onClickFeatureExpand(View view){
		if(featureStatus==0){
			featureStatus=1;
			viewFeature.setBackgroundResource(R.drawable.icn_collapse);
			txtFeature.setSingleLine(false);
		}else{
			featureStatus=0;
			viewFeature.setBackgroundResource(R.drawable.icn_expand);
			txtFeature.setMaxLines(3);
		}
		
	}
	public void onClickDescExpand(View view){
		if(descStatus==0){
			descStatus=1;
			viewDesc.setBackgroundResource(R.drawable.icn_collapse);
			txtDesc.setSingleLine(false);
		}else{
			descStatus=0;
			viewDesc.setBackgroundResource(R.drawable.icn_expand);
			txtDesc.setMaxLines(3);
		}
		
	}
	public void onClickDetailExpand(View view){
		if(detailStatus==0){
			detailStatus=1;
			viewDetail.setBackgroundResource(R.drawable.icn_collapse);
			txtDetail.setSingleLine(false);
		}else{
			detailStatus=0;
			viewDetail.setBackgroundResource(R.drawable.icn_expand);
			txtDetail.setMaxLines(3);
		}
		
	}
	
}

