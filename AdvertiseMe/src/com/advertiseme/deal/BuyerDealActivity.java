package com.advertiseme.deal;


import java.util.ArrayList;

import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.webservicecall.WebServiceCall;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class BuyerDealActivity extends StartPanelActivity {
	
	int detailStatus=0,featureStatus=0,descStatus=0;
	View viewFeature,viewDesc;
	TextView txtProductName,txtSeller,txtPrice,
	txtMyOffer,txtClientOffer,txtOfferPerson;
	
	int submitFlg=0;
	
	EditText txtMyNewOffer,txtBuyerTerms,txtSellerTerms;
	Button btnSubmit;
	
	CheckBox chkAccept;
		
	String advertName,sellerName,itemPrice,advertID,userID,userType,offerStatus="0",offerID;
	
	private static final String PREF_ADVERT_PROFILE = "advertProfile";
	private static final String PREF_ADVERTID = "advertID";
	private static final String PREF_SELLER_NAME = "sellerName";
	private static final String PREF_ADVERT_TITLE = "advertTitle";
	private static final String PREF_ADVERT_PRICE = "advertPrice";
	
	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	WebServiceCall webServiceCall;
	SharedPreferences prefUser;		
	SharedPreferences prefAdvert ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView (R.layout.activity_buyer_deal);
	    setTitleFromActivityLabel (R.id.title_text);
	    setStartUp();
	}
	
	void setStartUp(){
		
		webServiceCall = new WebServiceCall();

		prefUser = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		userID = prefUser.getString(PREF_USERID, null);
		
		prefAdvert = getSharedPreferences(PREF_ADVERT_PROFILE,MODE_PRIVATE);	
		
		advertID=prefAdvert.getString(PREF_ADVERTID, null);
		advertName=prefAdvert.getString(PREF_ADVERT_TITLE, null);
		sellerName=prefAdvert.getString(PREF_SELLER_NAME, null);
		itemPrice=prefAdvert.getString(PREF_ADVERT_PRICE, null);
		
		userType=getIntent().getExtras().getSerializable("USER_TYPE")
		.toString();
		
		if(getIntent().getExtras().getSerializable("ADVERT_NAME")!=null){
			
			offerID=getIntent().getExtras().getSerializable("OFFER_ID")
			.toString();
			advertID=getIntent().getExtras().getSerializable("ADVERT_ID")
			.toString();
			advertName=getIntent().getExtras().getSerializable("ADVERT_NAME")
			.toString();
			sellerName=getIntent().getExtras().getSerializable("SELLER_NAME")
			.toString();
			itemPrice=getIntent().getExtras().getSerializable("ITEM_PRICE")
			.toString();
		}
		
		viewFeature=findViewById(R.id.viewFeature);
		viewDesc=findViewById(R.id.viewDesc);
				
		txtBuyerTerms=(EditText)findViewById(R.id.txtBuyerTerms);
		txtSellerTerms=(EditText)findViewById(R.id.txtSellerTerms);
		txtMyNewOffer=(EditText)findViewById(R.id.txtMyNewOffer);
		
		txtProductName=(TextView)findViewById(R.id.txtProductName);
		txtSeller=(TextView)findViewById(R.id.txtSeller);
		txtPrice=(TextView)findViewById(R.id.txtPrice);
		
		txtMyOffer=(TextView)findViewById(R.id.txtMyOffer);
		txtClientOffer=(TextView)findViewById(R.id.txtClientOffer);
		txtOfferPerson=(TextView)findViewById(R.id.txtOfferPerson);
		
		chkAccept=(CheckBox)findViewById(R.id.chkAccept);
	   
		setBuyerOffferDetail();
		setOfferControls();
	}
	
	void setBuyerOffferDetail(){
		
		String[] resultList = null;
		if(userType.equalsIgnoreCase("0")){
			resultList = webServiceCall.getBuyerOfferByBuyerList(userID,advertID);
			
			if(resultList!=null){
				
				txtMyOffer.setText(resultList[3].replace("anyType{}", ""));
				txtClientOffer.setText(resultList[4].replace("anyType{}", ""));
				txtOfferPerson.setText(getOfferPerson());			
				
				txtBuyerTerms.setText(resultList[6].replace("anyType{}", ""));
				txtSellerTerms.setText(resultList[7].replace("anyType{}", ""));
				
				offerStatus=resultList[5];
				advertID=resultList[2];
				
				if(!txtMyOffer.getText().toString().equalsIgnoreCase("")){
					submitFlg=1;
				}
			}
			
		}else{
			resultList = webServiceCall.getBuyerOfferBySellerList(offerID);
			if(resultList!=null){
				
				txtMyOffer.setText(resultList[4].replace("anyType{}", ""));
				txtClientOffer.setText(resultList[3].replace("anyType{}", ""));
				txtOfferPerson.setText(getOfferPerson());			
				
				txtBuyerTerms.setText(resultList[6].replace("anyType{}", ""));
				txtSellerTerms.setText(resultList[7].replace("anyType{}", ""));
				
				offerStatus=resultList[5];
				advertID=resultList[2];
				offerID=resultList[0];
				
				if(!txtClientOffer.getText().toString().equalsIgnoreCase("")){
					submitFlg=1;
				}
			}
			
		}
		
		txtProductName.setText(advertName);
		txtSeller.setText(sellerName);
		txtPrice.setText(itemPrice);
		
	
	}
	
	String getOfferPerson(){
		
		String userTypeString="";
		if(userType.equalsIgnoreCase("0")){
			userTypeString="Seller Offer";
		}else{

			userTypeString="Buyer Offer";
			
		}
		return userTypeString;
	}
	void setOfferControls(){
		
		if(offerStatus.equalsIgnoreCase("2")){
			chkAccept.setEnabled(false);
		}
		if(userType.equalsIgnoreCase("0")){
			chkAccept.setVisibility(android.view.View.GONE);
			txtSellerTerms.setEnabled(false);
		}else{
			txtBuyerTerms.setEnabled(false);
		}
	}
	public void onClickBuyerTerms(View view){
		if(featureStatus==0){
			featureStatus=1;
			viewFeature.setBackgroundResource(R.drawable.icn_collapse);
			txtBuyerTerms.setSingleLine(false);
		}else{
			featureStatus=0;
			viewFeature.setBackgroundResource(R.drawable.icn_expand);
			txtBuyerTerms.setMaxLines(3);
		}
		
	}
	public void onClickSellerTerms(View view){
		if(descStatus==0){
			descStatus=1;
			viewDesc.setBackgroundResource(R.drawable.icn_collapse);
			txtSellerTerms.setSingleLine(false);
		}else{
			descStatus=0;
			viewDesc.setBackgroundResource(R.drawable.icn_expand);
			txtSellerTerms.setMaxLines(3);
		}
		
	}
	public void onClickSubmit(View view){
		
		if(submitFlg==0){
			saveBuyerOffer( );
		}else{
			if(userType.equalsIgnoreCase("0")){
				updateBuyerOffer();
			}else{
				updateBuyerOfferBySeller();
			}
			
		}
	}
void saveBuyerOffer( ){
		
	
		ArrayList paraList =new ArrayList<String>();
		
		paraList.add(userID);
		paraList.add(advertID);
		paraList.add(txtMyNewOffer.getText().toString());
		paraList.add("");
		paraList.add(txtBuyerTerms.getText().toString());
		paraList.add(txtSellerTerms.getText().toString());
		
		
		Boolean isSuccess=webServiceCall.saveBuyerOffer(paraList);
		
		if(isSuccess){
			submitFlg=1;
			txtMyOffer.setText(txtMyNewOffer.getText().toString());
			toast("successfully submitted");		
			
			
		}else{
			toast("Could not complete your request, please try again");
			return;
		}
		
	}
	void updateBuyerOffer( ){
	
	
	ArrayList paraList =new ArrayList<String>();
	
	paraList.add(userID);
	paraList.add(advertID);
	paraList.add(txtMyNewOffer.getText().toString());
	paraList.add(txtClientOffer.getText().toString());
	paraList.add(txtBuyerTerms.getText().toString());
	paraList.add(txtSellerTerms.getText().toString());
	
	
	Boolean isSuccess=webServiceCall.updateBuyerOffer(paraList);
	
	if(isSuccess){
		submitFlg=1;
		txtMyOffer.setText(txtMyNewOffer.getText().toString());
		toast("successfully submitted");		
		
		
	}else{
		toast("Could not complete your request, please try again");
		return;
	}
	}
	void updateBuyerOfferBySeller( ){
		
		
		ArrayList paraList =new ArrayList<String>();
			
		paraList.add(offerID);
		paraList.add(txtClientOffer.getText().toString());
		paraList.add(txtMyNewOffer.getText().toString());
		paraList.add(txtBuyerTerms.getText().toString());
		paraList.add(txtSellerTerms.getText().toString());
		paraList.add(getOfferStatus());
		
		
		Boolean isSuccess=webServiceCall.updateBuyerOfferBySeller(paraList);
		
		if(isSuccess){
			submitFlg=1;
			txtMyOffer.setText(txtMyNewOffer.getText().toString());
			toast("successfully submitted");		
			
			
		}else{
			toast("Could not complete your request, please try again");
			return;
		}
	
}
	int getOfferStatus(){
		int offerStatus=1;
		if(chkAccept.isChecked()){
			offerStatus=2;
		}
		return offerStatus;
	}
}

