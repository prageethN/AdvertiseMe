package com.advertiseme.advertisement;


import java.util.ArrayList;

import com.advertiseme.nearby.FixedMyLocationOverlay;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableRow.LayoutParams;

public class CutomerReviewActivity extends StartPanelActivity {
	
	ListView lvReview;
	TextView txtDescExpan,txtDescColps,txtReviewCount,txtProductName,txtSeller,txtPrice;
	View vwExpand,vwRatingExpand;
	RelativeLayout layoutCustomerRating;
	LinearLayout lnRateItem;
	ImageView imgStar;	
	RatingBar ratingbar;
	
	ArrayList<ListItemDetails> itemReviewList;
	int position=0,flgExpand=0;
	int flgRatingExpand=0,flgUserRate=0;
	
	private static final String PREF_ADVERT_PROFILE = "advertProfile";
	private static final String PREF_ADVERTID = "advertID";
	private static final String PREF_SELLER_NAME = "sellerName";
	private static final String PREF_ADVERT_TITLE = "advertTitle";
	private static final String PREF_ADVERT_PRICE = "advertPrice";
	
	SharedPreferences pref ;
	WebServiceCall webServiceCall;
	
	String adverID,userID;
	
	
	SharedPreferences prefUser;	

	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView (R.layout.activity_cutomer_review);
	    setTitleFromActivityLabel (R.id.title_text);
	    setStartUp();
	}
	
	void setStartUp(){
		
		webServiceCall=new WebServiceCall();
		
		txtReviewCount = (TextView) findViewById(R.id.txtReviewCount);
		ratingbar = (RatingBar) findViewById(R.id.ratingbar);
		imgStar = (ImageView) findViewById(R.id.imgStar);
		
		txtProductName=(TextView)findViewById(R.id.txtProductName);
		txtSeller=(TextView)findViewById(R.id.txtSeller);
		txtPrice=(TextView)findViewById(R.id.txtPrice);
		
		vwRatingExpand=findViewById(R.id.vwRatingExpand);
		layoutCustomerRating=(RelativeLayout)findViewById(R.id.layoutCustomerRating);
		
		lnRateItem=(LinearLayout)findViewById(R.id.lnRateItem);
		
	    pref = getSharedPreferences(PREF_ADVERT_PROFILE,MODE_PRIVATE);	
	    adverID=pref.getString(PREF_ADVERTID, null);
		
	    prefUser = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		userID = prefUser.getString(PREF_USERID, null);
	    
	    try {
	    	adverID = getIntent().getExtras().getSerializable("ADVERT_ID")
					.toString();

		} catch (Exception e) {
			// TODO: handle exception
		}
	    setAdvertisementDetail();
	    setAdvertRating(adverID);
	    
		itemReviewList= getReviewList();
		lvReview = (ListView) findViewById(R.id.lvReviewList);
		lvReview.setAdapter(new ReviewBaseAdapter(this,
				itemReviewList));
		lvReview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvReview.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;
				txtDescExpan=(TextView) v.findViewById(R.id.text2);
				txtDescColps=(TextView) v.findViewById(R.id.text4);
				vwExpand=v.findViewById(R.id.viewExpand);
				expandComment(obj_itemDetails.getImageNumber());
			}
		});
		setUserRateControls();
	}
	void expandComment(int curPosition){
		
		
		if(curPosition==position && flgExpand==0){
			
			position=curPosition;
			flgExpand=1;
			txtDescExpan.setVisibility(android.view.View.GONE);
			txtDescColps.setVisibility(android.view.View.VISIBLE);
			vwExpand.setBackgroundResource(R.drawable.icn_collapse);
			
		}else if(curPosition==position && flgExpand==1){
			
			position=curPosition;
			flgExpand=0;
			txtDescExpan.setVisibility(android.view.View.VISIBLE);
			txtDescColps.setVisibility(android.view.View.GONE);
			vwExpand.setBackgroundResource(R.drawable.icn_expand);
		}else if(flgExpand==0){
			flgExpand=1;
			position=curPosition;
			txtDescExpan.setVisibility(android.view.View.GONE);
			txtDescColps.setVisibility(android.view.View.VISIBLE);
			vwExpand.setBackgroundResource(R.drawable.icn_collapse);
		}else if(flgExpand==1){
			flgExpand=2;
			position=curPosition;
			txtDescExpan.setVisibility(android.view.View.VISIBLE);
			txtDescColps.setVisibility(android.view.View.GONE);
			vwExpand.setBackgroundResource(R.drawable.icn_expand);
		}
	}
	private ArrayList<ListItemDetails> getReviewList() {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();
		ArrayList<ArrayList> resultList=null;
		ArrayList<String> rowList=null;
		
				
		resultList=webServiceCall.getUserReviewList(adverID);
		
		if(resultList!=null){
			
		for(int i=0;i<resultList.size();i++){
			
				rowList=resultList.get(i);
				
				ListItemDetails item_details = new ListItemDetails();				
				item_details.setTextHeader(rowList.get(1));
				item_details.setTextNormal(rowList.get(2));
				item_details.setTextFooter("Published "+rowList.get(3)+" by "+rowList.get(4));
				item_details.setItemRating(Float.parseFloat(rowList.get(0)));								
				resultsList.add(item_details);
			
		}
		hasUserRated(resultList);
		}
		/*
		ListItemDetails item_details = new ListItemDetails();
		item_details.setTextHeader("Asesome phone, best one ever");
		item_details.setTextNormal(getResources().getString(R.string.about_intro));
		item_details.setTextFooter("Published Sep 17, 2012 by JackSparrow");
		item_details.setItemRating(4.5f);		
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Beter,faster");
		item_details.setTextNormal(getResources().getString(R.string.about_intro));
		item_details.setTextFooter("Published Sep 17, 2012 by JackSparrow");
		item_details.setItemRating(4.5f);		
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Android leading");
		item_details.setTextNormal(getResources().getString(R.string.about_intro));
		item_details.setTextFooter("Published Sep 17, 2012 by JackSparrow");
		item_details.setItemRating(4.5f);		
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Nice");
		item_details.setTextNormal(getResources().getString(R.string.about_intro));
		item_details.setTextFooter("Published Sep 17, 2012 by JackSparrow");
		item_details.setItemRating(4.5f);	
		resultsList.add(item_details);

*/
		return resultsList;
	}
	public void onClickExpand(View view){
		
		if(flgRatingExpand==0){
			flgRatingExpand=1;
			layoutCustomerRating.setVisibility(android.view.ViewGroup.GONE);
			vwRatingExpand.setBackgroundResource(R.drawable.icn_expand);
		}else if(flgRatingExpand==1){
			flgRatingExpand=0;
			vwRatingExpand.setBackgroundResource(R.drawable.icn_collapse);
			layoutCustomerRating.setVisibility(android.view.ViewGroup.VISIBLE);
		}
	}
void setAdvertisementDetail(){
		
		txtProductName.setText( pref.getString(PREF_ADVERT_TITLE, null));
		txtSeller.setText(pref.getString(PREF_SELLER_NAME, null));
		txtPrice.setText(pref.getString(PREF_ADVERT_PRICE, null));
		
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
	
	void setUserRateControls(){
		
		if(flgUserRate==1){
			lnRateItem.setVisibility(android.view.View.GONE);
		}
	}
	
	void hasUserRated(ArrayList<ArrayList> resultList){
		
		ArrayList<String> rowList=null;
		if(resultList.size()>0){
			
			for(int i=0;i<resultList.size();i++){
				
					rowList=resultList.get(i);
					if(rowList.get(5).equalsIgnoreCase(userID)){
						flgUserRate=1;
						break;
					}
				
			}
			}
	
	}
	public void onClickReview(View view){
		
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
		TextView lnRateItem  = (TextView) findViewById(R.id.txtSeller);

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
					.findViewById(R.id.popupRateItem);
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = layoutInflater.inflate(R.layout.popup_rate_product, viewGroup);

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
			txtTitle=(EditText)layout.findViewById(R.id.txtTitle);
			txtComment=(EditText)layout.findViewById(R.id.txtComment);
			
			rbRate.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				public void onRatingChanged(RatingBar ratingBar, float rating,
						boolean fromUser) {
			 
					txtRate.setText("My rating: "+String.valueOf((int)rating));
			 
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
					
					
					saveAdvertReview(txtComment.getText().toString(),rbRate.getRating(),txtTitle.getText().toString());
					popup.dismiss();
				}
			});
		}
	void saveAdvertReview( String _reviewContent, Float _rateValue,String _reviewHeading){
		
		ArrayList paraList =new ArrayList<String>();
		
		paraList.add(adverID);
		paraList.add(userID);
		paraList.add(_reviewContent);
		paraList.add(_rateValue);
		paraList.add(_reviewHeading);
		Boolean isSuccess=webServiceCall.saveAdvertReview(paraList);
		
		if(isSuccess){
			toast("Review successfully saved");
			lnRateItem.setVisibility(android.view.View.GONE);
			reloadAdvertReview();
			
		}else{
			toast("Could not complete your request, please try again");
			return;
		}
		
	}
	
	void reloadAdvertReview(){
		
		itemReviewList= getReviewList();
		lvReview = (ListView) findViewById(R.id.lvReviewList);
		lvReview.setAdapter(new ReviewBaseAdapter(this,
				itemReviewList));
		lvReview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvReview.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;
				txtDescExpan=(TextView) v.findViewById(R.id.text2);
				txtDescColps=(TextView) v.findViewById(R.id.text4);
				vwExpand=v.findViewById(R.id.viewExpand);
				expandComment(obj_itemDetails.getImageNumber());
			}
		});
	}
}

