package com.advertiseme.advertisement;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.kxml2.wap.WbxmlSerializer;

import com.advertiseme.data.DatabaseHandler;
import com.advertiseme.deal.BuyerDealActivity;
import com.advertiseme.nearby.FixedMyLocationOverlay;
import com.advertiseme.resource.ResourceActivity;
import com.advertiseme.seller.SellerDetailActivity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.web.WebpageOpenActivity;
import com.advertiseme.webservicecall.WebServiceCall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableRow.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

public class AdvertisementActivity extends StartPanelActivity implements
		OnClickListener {

	TextView testbar1, txtAdvertTitle, txtAdvertDetail, txtListPriceValue,
			txtPriceValue, txtSaveValue, txtNote, txtReviewCount,
			txtSellerName, txtPositiveFeedback;
	LinearLayout lnResourcePreview, layoutProductDetail, layoutSellerDetail;

	RelativeLayout layoutCustomerRating;
	ImageView imgResourcePreview, imgStar,imgNextArrow;
	TableRow tblRowPrice, tblRowSave, tblRowListPrice;
	RatingBar ratingbar;
	Button btnBuyOnline;
	ImageButton btnAddWatchList;

	String userID,advertID, previewURL, buyOnlineUrl, advertFatures, advertDesc,
			advertDetail,buyerOfferFlg="0";
	WebServiceCall webServiceCall;
	DatabaseHandler db;

	private static final String PREF_ADVERT_PROFILE = "advertProfile";
	private static final String PREF_ADVERTID = "advertID";
	private static final String PREF_SELLERID = "sellerID";
	// private static final String PREF_USERID = "userID";
	private static final String PREF_SELLER_NAME = "sellerName";
	private static final String PREF_ADVERT_TITLE = "advertTitle";
	private static final String PREF_ADVERT_PRICE = "advertPrice";

	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";

	private static int ADVERT_ACTION_FLG = 0;

	SharedPreferences prefAdvert, prefUser;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advertisement);
		setTitleFromActivityLabel(R.id.title_text);

		setStartUp();

	}

	void setStartUp() {

		advertID = getIntent().getExtras().getSerializable("ADVERT_ID")
				.toString();
		/*
		 * previewURL = getIntent().getExtras().getSerializable("PREVIEW_URL")
		 * .toString();
		 */

		webServiceCall = new WebServiceCall();
		db = new DatabaseHandler(this);

		prefAdvert = getSharedPreferences(PREF_ADVERT_PROFILE, MODE_PRIVATE);
		//advertID = prefAdvert.getString(PREF_ADVERTID, null);
		
		prefUser = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		userID = prefUser.getString(PREF_USERID, null);

		lnResourcePreview = (LinearLayout) findViewById(R.id.lnResourcePreview);
		layoutCustomerRating = (RelativeLayout) findViewById(R.id.layoutCustomerRating);
		layoutCustomerRating.setOnClickListener(this);

		imgResourcePreview = (ImageView) findViewById(R.id.imgResourcePreview);
		imgNextArrow=(ImageView) findViewById(R.id.imgNextArrow);
		
		txtAdvertTitle = (TextView) findViewById(R.id.txtAdvertTitle);
		txtAdvertDetail = (TextView) findViewById(R.id.txtAdvertDetail);
		txtListPriceValue = (TextView) findViewById(R.id.txtListPriceValue);
		txtPriceValue = (TextView) findViewById(R.id.txtPriceValue);
		txtSaveValue = (TextView) findViewById(R.id.txtSaveValue);
		txtNote = (TextView) findViewById(R.id.txtNote);
		txtReviewCount = (TextView) findViewById(R.id.txtReviewCount);

		tblRowPrice = (TableRow) findViewById(R.id.tblRowPrice);
		tblRowSave = (TableRow) findViewById(R.id.tblRowSave);
		tblRowListPrice = (TableRow) findViewById(R.id.tblRowListPrice);

		ratingbar = (RatingBar) findViewById(R.id.ratingbar);

		txtSellerName = (TextView) findViewById(R.id.txtSellerName);
		txtPositiveFeedback = (TextView) findViewById(R.id.txtPositiveFeedback);

		imgStar = (ImageView) findViewById(R.id.imgStar);

		btnBuyOnline = (Button) findViewById(R.id.btnBuyOnline);
		btnAddWatchList = (ImageButton) findViewById(R.id.btnAddWatchList);

		setSuggestControlReference();

		setAdvertisementDetails(advertID);
		setAdvertRating(advertID);
		setAdvertAction();
		performAdvertWatch();

		getSuggestAdvertList();
		setSuggestAdvertList();
		setBuyerOfferControls();
		
		saveAdvertview(advertID,userID);
	}

	public void onClickPreview(View view) {

		Intent intent = new Intent(getApplicationContext(),
				ResourceActivity.class);
		intent.putExtra("ADVERT_ID", prefAdvert.getString(PREF_ADVERTID, null));

		startActivity(intent);

	}

	public void onClickDetail(View view) {

		Intent intent = new Intent(getApplicationContext(),
				AdvertDetailActivity.class);
		intent.putExtra("ADVERT_FEATURES", advertFatures);
		intent.putExtra("ADVERT_DESC", advertDesc);
		intent.putExtra("ADVERT_DETAIL", advertDetail);
		startActivity(intent);

	}

	public void onClickReview(View view) {

		startActivity(new Intent(getApplicationContext(),
				CutomerReviewActivity.class));

	}

	public void onClickSeller(View view) {
		startActivity(new Intent(getApplicationContext(),
				SellerDetailActivity.class));
	}

	public void onClick(View view) {
		// TODO Auto-generated method stub
		startActivity(new Intent(getApplicationContext(),
				CutomerReviewActivity.class));

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

	void setAdvertisementDetails(String advertID) {

		String[] arrAdvertDetail = webServiceCall
				.getAdvertisementDetail(advertID);
		String sellPrice = null;

		getSharedPreferences(PREF_ADVERT_PROFILE, MODE_PRIVATE).edit()
				.putString(PREF_ADVERTID, arrAdvertDetail[0])
				.putString(PREF_SELLERID, arrAdvertDetail[1])
				.putString(PREF_USERID, arrAdvertDetail[3])
				.putString(PREF_SELLER_NAME, arrAdvertDetail[18])
				.putString(PREF_ADVERT_TITLE, arrAdvertDetail[2])
				.putString(PREF_ADVERT_PRICE, arrAdvertDetail[14]).commit();
if(arrAdvertDetail!=null){
		txtAdvertTitle.setText(arrAdvertDetail[2]);
		txtAdvertDetail.setText(arrAdvertDetail[2]);
		txtListPriceValue.setText(arrAdvertDetail[13]);
		txtPriceValue.setText(arrAdvertDetail[14]);
		txtSaveValue.setText(arrAdvertDetail[15] + " (" + arrAdvertDetail[17]
				+ "% off)");
		txtNote.setText(arrAdvertDetail[8].replace("anyType{}", ""));

		buyOnlineUrl = arrAdvertDetail[12];
		advertFatures = arrAdvertDetail[4].replace("anyType{}", "");
		advertDesc = arrAdvertDetail[5].replace("anyType{}", "");
		advertDetail = arrAdvertDetail[6].replace("anyType{}", "");

		setOfferInfo(arrAdvertDetail[9]);
		setSellerDetail(arrAdvertDetail[1]);
		setBuyOnlineInfo(arrAdvertDetail[11]);

		previewURL = arrAdvertDetail[19];
		Bitmap bimage = getBitmapFromURL(previewURL);
		imgResourcePreview.setImageBitmap(bimage);
		
		buyerOfferFlg= arrAdvertDetail[20];
}
	}

	void setOfferInfo(String flgVisible) {
		if (!flgVisible.equalsIgnoreCase("1")) {
			tblRowListPrice.setVisibility(android.view.View.GONE);
			tblRowSave.setVisibility(android.view.View.GONE);
		}
	}

	void setBuyOnlineInfo(String flgOnline) {
		if (!flgOnline.equalsIgnoreCase("1")) {
			btnBuyOnline.setVisibility(android.view.View.GONE);
		}
	}

	void setAdvertRating(String advertID) {

		String[] arrAdvertRating = webServiceCall
				.getAdvertisementRating(advertID);

		if (arrAdvertRating.length > 0) {

			ratingbar.setRating(Float.parseFloat(arrAdvertRating[0]));
			txtReviewCount.setText("(" + arrAdvertRating[1] + ")");

			setReviewSummaryGraph(Integer.parseInt(arrAdvertRating[2]),
					Integer.parseInt(arrAdvertRating[3]),
					Integer.parseInt(arrAdvertRating[4]),
					Integer.parseInt(arrAdvertRating[5]),
					Integer.parseInt(arrAdvertRating[6]));

		}

	}

	void setReviewSummaryGraph(int vStarFive, int vStarFour, int vStarThree,
			int vStarTwo, int vStarOne) {

		TextView g_star_five, g_star_four, g_star_three, g_star_two, g_star_one, v_star_five, v_star_four, v_star_three, v_star_two, v_star_one;

		g_star_five = (TextView) findViewById(R.id.g_star_five);
		g_star_four = (TextView) findViewById(R.id.g_star_four);
		g_star_three = (TextView) findViewById(R.id.g_star_three);
		g_star_two = (TextView) findViewById(R.id.g_star_two);
		g_star_one = (TextView) findViewById(R.id.g_star_one);

		v_star_five = (TextView) findViewById(R.id.v_star_five);
		v_star_four = (TextView) findViewById(R.id.v_star_four);
		v_star_three = (TextView) findViewById(R.id.v_star_three);
		v_star_two = (TextView) findViewById(R.id.v_star_two);
		v_star_one = (TextView) findViewById(R.id.v_star_one);

		int vTotal = vStarFive + vStarFour + vStarThree + vStarThree + vStarTwo
				+ vStarOne;
		float lWeightFive, lWeightFour, lWeightThree, lWeightTwo, lWeightOne;

		lWeightFive = ((float) vStarFive) / vTotal;
		lWeightFour = ((float) vStarFour) / vTotal;
		lWeightThree = ((float) vStarThree) / vTotal;
		lWeightTwo = ((float) vStarTwo) / vTotal;
		lWeightOne = ((float) vStarOne) / vTotal;

		g_star_five.setLayoutParams(new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
				lWeightFive));
		g_star_four.setLayoutParams(new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
				lWeightFour));
		g_star_three.setLayoutParams(new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
				lWeightThree));
		g_star_two.setLayoutParams(new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
				lWeightTwo));
		g_star_one.setLayoutParams(new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
				lWeightOne));

		v_star_five.setText("(" + vStarFive + ")");
		v_star_four.setText("(" + vStarFour + ")");
		v_star_three.setText("(" + vStarThree + ")");
		v_star_two.setText("(" + vStarTwo + ")");
		v_star_one.setText("(" + vStarOne + ")");

	}

	void setSellerDetail(String sellerID) {

		String[] resultList = webServiceCall.getSellerDetail(sellerID);
		Double positiveFeedback = 0.0;
		if(resultList!=null){
			if (Double.parseDouble(resultList[2]) > 0) {
				positiveFeedback = (Double.parseDouble(resultList[3]) / Double
						.parseDouble(resultList[2])) * 100;
			}
			txtSellerName.setText(resultList[0] + " (" + resultList[2] + ")");
			txtPositiveFeedback.setText(positiveFeedback + "% positive feedback");
			setSellerStar(resultList[4]);
			
		}
		
	}

	void setSellerStar(String sumRating) {

		int sumRatingCount = Integer.parseInt(sumRating);

		if (0 < sumRatingCount && sumRatingCount < 49) {
			imgStar.setImageResource(R.drawable.icn_yellow_star);
		}
		if (50 < sumRatingCount && sumRatingCount < 99) {
			imgStar.setImageResource(R.drawable.icn_blue_star);
		}
		if (100 < sumRatingCount && sumRatingCount < 499) {
			imgStar.setImageResource(R.drawable.icn_skyblue_star);
		}
		if (500 < sumRatingCount && sumRatingCount < 999) {
			imgStar.setImageResource(R.drawable.icn_purple_star);
		}
		if (1000 < sumRatingCount && sumRatingCount < 4999) {
			imgStar.setImageResource(R.drawable.icn_red_star);
		}
		if (5000 < sumRatingCount && sumRatingCount < 9999) {
			imgStar.setImageResource(R.drawable.icn_green_star);
		}

	}

	public void onClickBuyOnline(View view) {

		Intent intent = new Intent(getApplicationContext(),
				WebpageOpenActivity.class);
		intent.putExtra("WEB_URL", buyOnlineUrl);

		startActivity(intent);
	}

	public void onClickWatchList(View view) {

		String userID = prefUser.getString(PREF_USERID, null);
		String advertID = prefAdvert.getString(PREF_ADVERTID, null);

		db.addWatchListItem(userID, advertID);
		setAdvertAction();
		toast("Successfully Added to watch list");

	}

	public void setAdvertAction() {

		if (isWatchListItem()) {
			btnAddWatchList.setVisibility(android.view.ViewGroup.GONE);
			ADVERT_ACTION_FLG = 0;
		} else {
			btnAddWatchList.setVisibility(android.view.ViewGroup.VISIBLE);
			ADVERT_ACTION_FLG = 1;
		}
	}

	Boolean isWatchListItem() {

		Boolean isFavSeller = false;
		String userID = prefUser.getString(PREF_USERID, null);
		String advertID = prefAdvert.getString(PREF_ADVERTID, null);

		ArrayList<String> arrList = new ArrayList<String>();

		ArrayList<ArrayList<String>> resultList;
		ArrayList<String> rowList;

		resultList = db.getWatchList(userID);

		if (resultList != null) {

			for (int i = 0; i < resultList.size(); i++) {

				rowList = resultList.get(i);

				if (rowList.get(2).equalsIgnoreCase(advertID)) {
					isFavSeller = true;
					break;
				}
				;

			}

		}
		return isFavSeller;

	}

	void performAdvertWatch() {

		String userID = prefUser.getString(PREF_USERID, null);
		String advertID = prefAdvert.getString(PREF_ADVERTID, null);

		db.addActiveListItem(userID, advertID);

	}

	public void onClickBuyerOffer(View view){
		
		String userID = prefUser.getString(PREF_USERID, null);
		String sellerID = prefAdvert.getString(PREF_SELLERID, null);
		
		if(buyerOfferFlg.equalsIgnoreCase("1")&& !userID.equalsIgnoreCase(sellerID)){
			
			Intent intent=new Intent(getApplicationContext(),
					BuyerDealActivity.class);
			intent.putExtra("USER_TYPE", "0");
			startActivity(intent);
		}
		
	}
	
	void setBuyerOfferControls(){
		
		if(buyerOfferFlg.equalsIgnoreCase("1")){
			imgNextArrow.setVisibility(android.view.View.VISIBLE);
		}
	}
	/* ----------------------------  suggestion list      ----------------------------- */

	LinearLayout layoutItem_1, layoutItem_2, layoutItem_3, layoutItem_4,
			layoutItem_5, layoutItem_6, layoutItem_7, layoutItem_8,
			layoutItem_9, layoutItem_10;
	ImageView imgPreview_1, imgPreview_2, imgPreview_3, imgPreview_4,
			imgPreview_5, imgPreview_6, imgPreview_7, imgPreview_8,
			imgPreview_9, imgPreview_10;
	TextView txtProductName_1, txtProductName_2, txtProductName_3,
			txtProductName_4, txtProductName_5, txtProductName_6,
			txtProductName_7, txtProductName_8, txtProductName_9,
			txtProductName_10;
	TextView txtProductPrice_1, txtProductPrice_2, txtProductPrice_3,
			txtProductPrice_4, txtProductPrice_5, txtProductPrice_6,
			txtProductPrice_7, txtProductPrice_8, txtProductPrice_9,
			txtProductPrice_10;

	ArrayList<ListItemDetails> resultsList;

	void setSuggestControlReference() {

		layoutItem_1=(LinearLayout)findViewById(R.id.layoutItem_1);
		layoutItem_2=(LinearLayout)findViewById(R.id.layoutItem_2);
		layoutItem_3=(LinearLayout)findViewById(R.id.layoutItem_3);
		layoutItem_4=(LinearLayout)findViewById(R.id.layoutItem_4);
		layoutItem_5=(LinearLayout)findViewById(R.id.layoutItem_5);
		layoutItem_6=(LinearLayout)findViewById(R.id.layoutItem_6);
		layoutItem_7=(LinearLayout)findViewById(R.id.layoutItem_7);
		layoutItem_8=(LinearLayout)findViewById(R.id.layoutItem_8);
		layoutItem_9=(LinearLayout)findViewById(R.id.layoutItem_9);
		layoutItem_10=(LinearLayout)findViewById(R.id.layoutItem_10);
		
		imgPreview_1 = (ImageView) findViewById(R.id.imgPreview_1);
		imgPreview_2 = (ImageView) findViewById(R.id.imgPreview_2);
		imgPreview_3 = (ImageView) findViewById(R.id.imgPreview_3);
		imgPreview_4 = (ImageView) findViewById(R.id.imgPreview_4);
		imgPreview_5 = (ImageView) findViewById(R.id.imgPreview_5_);
		imgPreview_6 = (ImageView) findViewById(R.id.imgPreview_6);
		imgPreview_7 = (ImageView) findViewById(R.id.imgPreview_7);
		imgPreview_8 = (ImageView) findViewById(R.id.imgPreview_8);
		imgPreview_9 = (ImageView) findViewById(R.id.imgPreview_9);
		imgPreview_10 = (ImageView) findViewById(R.id.imgPreview_10);

		txtProductName_1 = (TextView) findViewById(R.id.txtProductName_1);
		txtProductName_2 = (TextView) findViewById(R.id.txtProductName_2);
		txtProductName_3 = (TextView) findViewById(R.id.txtProductName_3);
		txtProductName_4 = (TextView) findViewById(R.id.txtProductName_4);
		txtProductName_5 = (TextView) findViewById(R.id.txtProductName_5);
		txtProductName_6 = (TextView) findViewById(R.id.txtProductName_6);
		txtProductName_7 = (TextView) findViewById(R.id.txtProductName_7);
		txtProductName_8 = (TextView) findViewById(R.id.txtProductName_8);
		txtProductName_9 = (TextView) findViewById(R.id.txtProductName_9);
		txtProductName_10 = (TextView) findViewById(R.id.txtProductName_10);

		txtProductPrice_1 = (TextView) findViewById(R.id.txtProductPrice_1);
		txtProductPrice_2 = (TextView) findViewById(R.id.txtProductPrice_2);
		txtProductPrice_3 = (TextView) findViewById(R.id.txtProductPrice_3);
		txtProductPrice_4 = (TextView) findViewById(R.id.txtProductPrice_4);
		txtProductPrice_5 = (TextView) findViewById(R.id.txtProductPrice_5);
		txtProductPrice_6 = (TextView) findViewById(R.id.txtProductPrice_6);
		txtProductPrice_7 = (TextView) findViewById(R.id.txtProductPrice_7);
		txtProductPrice_8 = (TextView) findViewById(R.id.txtProductPrice_8);
		txtProductPrice_9 = (TextView) findViewById(R.id.txtProductPrice_9);
		txtProductPrice_10 = (TextView) findViewById(R.id.txtProductPrice_10);
		

	}

	void getSuggestAdvertList() {

		resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList = new ArrayList<ArrayList>();
		ArrayList<String> rowList = null;

		WebServiceCall webServiceCall = new WebServiceCall();
		
		String qString=getQueryString();
		//resultList = webServiceCall.getAdvertSearchResult("");		
		resultList = webServiceCall.getRecommendationList(qString);
		
		if (resultList != null) {

			for (int i = 0; i < resultList.size(); i++) {

				rowList = resultList.get(i);

				ListItemDetails item_details = new ListItemDetails();
				item_details.setItemID(rowList.get(0));
				item_details.setTextHeader(rowList.get(1));
				item_details.setTextNormal(rowList.get(2));
				item_details.setTextFooter(rowList.get(3));
				item_details.setPreviewURL(rowList.get(6));
				resultsList.add(item_details);

			}

		}
	}
	String getQueryString(){
	
		WebServiceCall webServiceCall = new WebServiceCall();
		String qString="";
		String []resultList = webServiceCall.getItemRecommendationList(userID,advertID);
		if(resultList!=null){
			
			for(int i=0;i<resultList.length;i++){
				
				if(i>0){
					qString+=","+resultList[i];
					
				}else{
					qString+=resultList[i];
				}
			}
			
		}
		return qString;
}
	void setSuggestAdvertList() {

		LinearLayout[] layoutItem={ layoutItem_1, layoutItem_2, layoutItem_3, layoutItem_4,
		layoutItem_5, layoutItem_6, layoutItem_7, layoutItem_8,
		layoutItem_9, layoutItem_10};
		
		ImageView[] imageView = { imgPreview_1, imgPreview_2, imgPreview_3,
				imgPreview_4, imgPreview_5, imgPreview_6, imgPreview_7,
				imgPreview_8, imgPreview_9, imgPreview_10 };
		TextView[] txtProductName = { txtProductName_1, txtProductName_2,
				txtProductName_3, txtProductName_4, txtProductName_5,
				txtProductName_6, txtProductName_7, txtProductName_8,
				txtProductName_9, txtProductName_10 };
		TextView[] txtProductPrice = { txtProductPrice_1, txtProductPrice_2,
				txtProductPrice_3, txtProductPrice_4, txtProductPrice_5,
				txtProductPrice_6, txtProductPrice_7, txtProductPrice_8,
				txtProductPrice_9, txtProductPrice_10 };

		for (int count = 0; count < resultsList.size(); count++) {

			ListItemDetails obj_itemDetails = resultsList.get(count);

			layoutItem[count].setVisibility(android.view.View.VISIBLE);
			String previewURL = obj_itemDetails.getPreviewURL();
			Bitmap bimage = getBitmapFromURL(previewURL);
			imageView[count].setImageBitmap(bimage);

			txtProductName[count].setText(obj_itemDetails.getTextHeader());
			txtProductPrice[count].setText(obj_itemDetails.getTextNormal());

		}
	}
	
	public void onClickSuggestion(View view){
		
		LinearLayout[] layoutItem={ layoutItem_1, layoutItem_2, layoutItem_3, layoutItem_4,
				layoutItem_5, layoutItem_6, layoutItem_7, layoutItem_8,
				layoutItem_9, layoutItem_10};
		ListItemDetails obj_itemDetails=null;
		
		if(view==layoutItem[0]){
			obj_itemDetails = resultsList.get(0);
		}
		if(view==layoutItem[1]){
			obj_itemDetails = resultsList.get(1);
		}
		if(view==layoutItem[2]){
			obj_itemDetails = resultsList.get(2);
		}
		if(view==layoutItem[3]){
			obj_itemDetails = resultsList.get(3);
		}
		if(view==layoutItem[4]){
			obj_itemDetails = resultsList.get(4);
		}
		if(view==layoutItem[5]){
			obj_itemDetails = resultsList.get(5);
		}
		if(view==layoutItem[6]){
			obj_itemDetails = resultsList.get(6);
		}
		if(view==layoutItem[7]){
			obj_itemDetails = resultsList.get(7);
		}
		if(view==layoutItem[8]){
			obj_itemDetails = resultsList.get(8);
		}
		if(view==layoutItem[9]){
			obj_itemDetails = resultsList.get(9);
		}
		
		Intent intent= new Intent(AdvertisementActivity.this,
				AdvertisementActivity.class);
		intent.putExtra("ADVERT_ID", obj_itemDetails.getItemId());
		intent.putExtra("PREVIEW_URL", obj_itemDetails.getPreviewURL());
		
		startActivity(intent);
	}
	void saveAdvertview(String _advertID, String _userID){
		
		ArrayList paraList =new ArrayList<String>();
		
		paraList.add(_advertID);
		paraList.add(_userID);
		
		try {
			Boolean isSuccess=webServiceCall.saveAdvertView(paraList);
		} catch (Exception e) {
			toast("Error!");
		}
				
		
		
	}
	
}
