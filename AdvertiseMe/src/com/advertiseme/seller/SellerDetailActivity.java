package com.advertiseme.seller;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.advertiseme.advertisement.AdvertListActivity;
import com.advertiseme.data.DatabaseHandler;
import com.advertiseme.favoriteseller.FavoriteSellerBaseAdapter;
import com.advertiseme.startpanel.Feature5Activity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.user.UserProfileActivity;
import com.advertiseme.webservicecall.WebServiceCall;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TableRow.LayoutParams;

public class SellerDetailActivity extends StartPanelActivity {
	
	TextView txtSellerName,txtPositiveFeedback,txtMembershpYear,txtSaveSeller;
	ImageView imgStar,imgProfilePic;
	RelativeLayout lnCustomerProfile,lnReviewSeller;
	
	WebServiceCall webServiceCall;
	DatabaseHandler db;
	SharedPreferences pref ;
	
	private static final String PREF_ADVERT_PROFILE = "advertProfile";
	private static final String PREF_ADVERTID = "advertID";
	private static final String PREF_SELLERID = "sellerID";
	//private static final String PREF_USERID = "userID";
	
	SharedPreferences prefUser;	

	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	private static int SELLER_ACTION_FLG=1;
	
	String userID,advertID,sellerID;
	int flgUserRate=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView (R.layout.activity_seller_detail);
	    setTitleFromActivityLabel (R.id.title_text);
	    setStartUp();
	}
	
	void setStartUp(){
		webServiceCall=new WebServiceCall();
		db = new DatabaseHandler(this);
		
		pref = getSharedPreferences(PREF_ADVERT_PROFILE,MODE_PRIVATE);
		advertID = pref.getString(PREF_ADVERTID, null);
		sellerID = pref.getString(PREF_SELLERID, null);
		
		prefUser = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		userID = prefUser.getString(PREF_USERID, null);
		
		txtSellerName=(TextView)findViewById(R.id.txtSellerName);
		txtPositiveFeedback=(TextView)findViewById(R.id.txtPositiveFeedback);
		txtMembershpYear=(TextView)findViewById(R.id.txtMembershpYear);
		txtSaveSeller=(TextView)findViewById(R.id.txtSaveSeller);
		
		imgStar=(ImageView)findViewById(R.id.imgStar);
		imgProfilePic=(ImageView)findViewById(R.id.imgProfilePic);		
				
		lnReviewSeller=(RelativeLayout)findViewById(R.id.lnReviewSeller);
		
		setSellerDetail(sellerID);
		setSellerAction();
		hasUserRated();
		setUserRateControls();
	}
	void setSellerDetail(String sellerID) {

		String[] resultList = webServiceCall.getSellerDetail(sellerID);
		if(resultList!=null){
		Double positiveFeedback = 0.0;
		if (Double.parseDouble(resultList[2]) > 0) {
			positiveFeedback = (Double.parseDouble(resultList[3]) / Double
					.parseDouble(resultList[2])) * 100;
		}
		txtSellerName.setText(resultList[0]+" ("+resultList[2]+")");
		txtPositiveFeedback.setText(positiveFeedback + "% positive feedback");
		txtMembershpYear.setText("Member since "+resultList[5]);
		setSellerStar(resultList[4]);
		Bitmap bimage=  getBitmapFromURL(resultList[6]);
		imgProfilePic.setImageBitmap(bimage);
		
		setSellerRating(pref.getString(PREF_SELLERID, null));
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
		if (5000< sumRatingCount && sumRatingCount < 9999) {
			imgStar.setImageResource(R.drawable.icn_green_star);
		}

	}
	 public static Bitmap getBitmapFromURL(String src) {
	        try {
	            Log.e("src",src);
	            URL url = new URL(src);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setDoInput(true);
	            connection.connect();
	            InputStream input = connection.getInputStream();
	            Bitmap myBitmap = BitmapFactory.decodeStream(input);
	            Log.e("Bitmap","returned");
	            return myBitmap;
	        } catch (IOException e) {
	            e.printStackTrace();
	            Log.e("Exception",e.getMessage());
	            return null;
	        }
	    }
	 void setSellerRating(String advertID) {

			String[] arrAdvertRating = webServiceCall
					.getSellerReview(advertID);

			if (arrAdvertRating.length > 0) {

				setReviewSummaryGraph(
						Integer.parseInt(arrAdvertRating[0]),
						Integer.parseInt(arrAdvertRating[1]),
						Integer.parseInt(arrAdvertRating[2]));	

			}

		}
	 void setReviewSummaryGraph(int vStarFive, int vStarFour, int vStarThree
				) {

			TextView g_star_five, g_star_four, g_star_three, v_star_five, v_star_four, v_star_three, v_star_two, v_star_one;

			g_star_five = (TextView) findViewById(R.id.g_star_five);
			g_star_four = (TextView) findViewById(R.id.g_star_four);
			g_star_three = (TextView) findViewById(R.id.g_star_three);
			
			v_star_five = (TextView) findViewById(R.id.v_star_five);
			v_star_four = (TextView) findViewById(R.id.v_star_four);
			v_star_three = (TextView) findViewById(R.id.v_star_three);
		

			int vTotal = vStarFive + vStarFour + vStarThree ;
			float lWeightFive, lWeightFour, lWeightThree;

			lWeightFive = ((float) vStarFive) / vTotal;
			lWeightFour = ((float) vStarFour) / vTotal;
			lWeightThree = ((float) vStarThree) / vTotal;
			

			g_star_five.setLayoutParams(new TableRow.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
					lWeightFive));
			g_star_four.setLayoutParams(new TableRow.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
					lWeightFour));
			g_star_three.setLayoutParams(new TableRow.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
					lWeightThree));
			
			v_star_five.setText("(" + vStarFive + ")");
			v_star_four.setText("(" + vStarFour + ")");
			v_star_three.setText("(" + vStarThree + ")");
			

		}
	
	public void setSellerAction(){
		
		if(isFavoriteSeller()){
			txtSaveSeller.setText("Remove this seller");
			SELLER_ACTION_FLG=0;
		}else{
			txtSaveSeller.setText("Save this seller");
			SELLER_ACTION_FLG=1;
		}
	}
	public void onClickFeedback(View view){
		
		startActivity(new Intent(getApplicationContext(),
				SellerFeedbackActivity.class));
	}
	public void onClickOtherItems(View view){
		
		startActivity(new Intent(getApplicationContext(),
				AdvertListActivity.class));
	}
	
	public void onClickSellerProfile(View view){
		
		Intent intent=new Intent(getApplicationContext(),
				UserProfileActivity.class);
		intent.putExtra("USER_ID",pref.getString(PREF_USERID, null) );
		startActivity(intent);
	}
	public void onClickSaveSeller(View view){
		
		String userID=pref.getString(PREF_USERID, null);
		String sellerID=pref.getString(PREF_SELLERID, null);
		String sellerName=txtSellerName.getText().toString();
		
		switch(SELLER_ACTION_FLG){
		
		case 1:
			db.addFavoriteSeller(userID,sellerID);
			toast("Successfully added to favorite sellers");
			setSellerAction();break;
		case 0:
			removeFromList(userID,sellerID,sellerName);break;
			
		}
		
	}
	Boolean isFavoriteSeller(){

		Boolean isFavSeller=false;
		String userID=pref.getString(PREF_USERID, null);
		String sellerID=pref.getString(PREF_SELLERID, null);
		
		ArrayList<String> arrList=new ArrayList<String>();
		
		ArrayList<ArrayList<String>> resultList;
		ArrayList<String> rowList;
		
		resultList=db.getFavoriteSeller(userID);
		
		if(resultList!=null){
			
		for(int i=0;i<resultList.size();i++){
			
			rowList=resultList.get(i);
			
			if(rowList.get(2).equalsIgnoreCase(sellerID)){
				isFavSeller=true;
				break;
			};		
			
		}
		
		}
		return isFavSeller;
		
	}
	void removeFromList(final String _userID,final String _sellerID,String sellerName) {
		
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		
		
		alertDialog.setTitle("Romove from favorite sellers?");
		alertDialog.setMessage(sellerName);
		
		alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				db.deleteFavoriteSeller(_userID,_sellerID);
				setSellerAction();
				toast("Successfully Removed from favorite sellers");
			}
		});
		alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		alertDialog.show();
}
	
void setUserRateControls(){
		
		if(flgUserRate==1){
			lnReviewSeller.setVisibility(android.view.View.GONE);
		}
	}
void hasUserRated(){
	ArrayList<ArrayList> resultList=null;
	ArrayList<String> rowList=null;
			
	resultList=webServiceCall.getSellerFeedbackList(sellerID);
	
		if(resultList.size()>0){
			
			for(int i=0;i<resultList.size();i++){
				
					rowList=resultList.get(i);
					if(rowList.get(4).equalsIgnoreCase(userID)){
						flgUserRate=1;
						break;
					}
				
			}
			}
	
	}
	public void onClickReviewSeller(View v){
		
		showPopup(this,p);
	}
	/*  ---------------------------     Rating advertisement -------------------------------------------- */
	Point p;
	RatingBar rbRate;
	TextView txtRate;
	EditText txtTitle,txtComment;
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

		int[] location = new int[2];
		TextView lnRateItem  = (TextView) findViewById(R.id.txtMembershpYear);

		// Get the x, y location and store it in the location[] array
		// location[0] = x, location[1] = y.
		lnRateItem.getLocationOnScreen(location);

		// Initialize the Point with x, and y positions
		p = new Point();
		p.x = location[0];
		p.y = location[1];
	}
	
	private void showPopup(final Activity context,Point p) {
			
			int popupWidth = ViewGroup.LayoutParams.FILL_PARENT;
			int popupHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

			// Inflate the popup_layout.xml
			LinearLayout viewGroup = (LinearLayout) context
					.findViewById(R.id.popupReviewSeller);
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = layoutInflater.inflate(R.layout.popup_review_seller, viewGroup);

			// Creating the PopupWindow
			final PopupWindow popup = new PopupWindow(context);
			popup.setContentView(layout);
			popup.setWidth(popupWidth);
			popup.setHeight(popupHeight);
			popup.setFocusable(true);

			// Some offset to align the popup a bit to the right, and a bit down,
			// relative to button's position.
			int OFFSET_X = 30;
			int OFFSET_Y = 30;

			// Clear the default translucent background
			popup.setBackgroundDrawable(new BitmapDrawable());

			// Displaying the popup at the specified location, + offsets.
			popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y
					+ OFFSET_Y);

			rbRate=(RatingBar)layout.findViewById(R.id.rbRate);
			txtRate = (TextView) layout.findViewById(R.id.txtRate);			
			txtComment=(EditText)layout.findViewById(R.id.txtComment);
			
			rbRate.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				public void onRatingChanged(RatingBar ratingBar, float rating,
						boolean fromUser) {
			 switch ((int)rating) {
			case 1:
				txtRate.setText("My rating: negative");
				break;

			case 2:
				txtRate.setText("My rating: nuetral");
				break;
			case 3:
				txtRate.setText("My rating: postive");
				break;
			}
					
			 
					}
				});
			// Getting a reference to Close button, and close the popup when
			// clicked.
			Button close = (Button) layout.findViewById(R.id.btnClose);
			close.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					popup.dismiss();
				}
			});
			Button done = (Button) layout.findViewById(R.id.btnOk);
			done.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					saveSellerReview(rbRate.getRating(),txtComment.getText().toString());
					popup.dismiss();
				}
			});
		}
	void saveSellerReview( Float _reviewFlg, String _reviewComment){
		
		ArrayList paraList =new ArrayList<String>();
		
		paraList.add(sellerID);
		paraList.add(userID);
		paraList.add(_reviewFlg);
		paraList.add(_reviewComment);
		
		Boolean isSuccess=webServiceCall.saveSellerReview(paraList);
		
		if(isSuccess){
			toast("Review successfully saved");
			lnReviewSeller.setVisibility(android.view.View.GONE);
			
			
		}else{
			toast("Could not complete your request, please try again");
			return;
		}
		
	}
}

