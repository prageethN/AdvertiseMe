package com.advertiseme.recomendation;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.advertiseme.advertisement.AdvertListActivity;
import com.advertiseme.advertisement.AdvertisementActivity;
import com.advertiseme.search.SavedSearchActivity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

public class RecommendationActivity extends StartPanelActivity{

	ListView lvRecommendation;
	ArrayList<ListItemDetails> itemRecommendation;
	TextView txtOptions;
	String rowText;
	int rowPosition;
	
	WebServiceCall webServiceCall;
	SharedPreferences pref;
	
	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_recommendation);
	    setTitleFromActivityLabel (R.id.title_text);
	    setupStart();
	}
	
	void setupStart(){
		
		webServiceCall =new WebServiceCall();
		pref = getSharedPreferences(PREF_PROFILE,MODE_PRIVATE);
		
		itemRecommendation= getActiveList();
		if(itemRecommendation!=null){
		lvRecommendation = (ListView) findViewById(R.id.lvActiveList);
		lvRecommendation.setAdapter(new RecomendationBaseAdapter(this,
				itemRecommendation));
		lvRecommendation.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {				
				Object o = lvRecommendation.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;
				String adverID=obj_itemDetails.getItemId();
				Intent intent=new Intent(RecommendationActivity.this,
						AdvertisementActivity.class);
				intent.putExtra("ADVERT_ID", adverID);
				startActivity(intent);
			}
		});
		lvRecommendation.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {				
				onLongListItemClick(view, position, id);
				return false;
			}
		});
		}
	}
	protected void onLongListItemClick(View view, int position, long id) {
		Object o = lvRecommendation.getItemAtPosition(position);
		ListItemDetails obj_itemDetails = (ListItemDetails) o;
		rowText = obj_itemDetails.getTextHeader();
		rowPosition=position;
		
	}
	private ArrayList<ListItemDetails> getActiveList() {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList=null;
		ArrayList<String> rowList=null;
		
		String qString=getQueryString();
				
		//resultList=webServiceCall.getUserRecommendationList(pref.getString(PREF_USERID, null));
		resultList=webServiceCall.getUserRecommendationList(qString);
		
		if(resultList!=null){
			
		for(int i=0;i<resultList.size();i++){
			
				rowList=resultList.get(i);
				
				ListItemDetails item_details = new ListItemDetails();
				item_details.setItemID(rowList.get(0));
				item_details.setTextHeader(rowList.get(1));
				item_details.setTextNormal(rowList.get(2));
				item_details.setTextFooter(rowList.get(3));	
				item_details.setPreviewURL(rowList.get(4));
				resultsList.add(item_details);
				
		}
		
		}
		
		
		return resultsList;
	}
	String getQueryString(){
		
		WebServiceCall webServiceCall = new WebServiceCall();
		String qString="";
		String []resultList = webServiceCall.getUserRecommendationList(pref.getString(PREF_USERID, null),"0000");
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
private String[] getDepartmenttList(){
		
		String arrDepartmentList[]=new String[5];
		arrDepartmentList[0]="Computers and tablets (10)";
		arrDepartmentList[1]="Cell phones and accessories (21)";
		arrDepartmentList[2]="Cameras and accessories (102)";
		arrDepartmentList[3]="Computers and tablets (1)";
		arrDepartmentList[4]="Electronics (4)";
		
		return arrDepartmentList;
	}
	protected Dialog onCreateDialog(int id) {
		
		switch(id){
		case 0:
			String arrOptions[]={"I Own It","Not Interested"};
			 return new AlertDialog.Builder(RecommendationActivity.this)
	        .setTitle("Options")
	        .setItems(arrOptions, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {

	                /* User clicked so do some stuff */                
	                if(which==0){
	                	 removeFromList(rowText,rowPosition);
	                 }if(which==1){
	                	 removeFromList(rowText,rowPosition);
	                 }
	            }
	        })
	        .create();
		case 1:
			
			 return new AlertDialog.Builder(RecommendationActivity.this)
	        .setTitle("Options")
	        .setItems(getDepartmenttList(), new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {

	                /* User clicked so do some stuff */                
	                if(which==0){
	                	 removeFromList(rowText,rowPosition);
	                 }if(which==1){
	                	 removeFromList(rowText,rowPosition);
	                 }
	            }
	        })
	        .create();	 
		}
		
		return null;
		 
	 }
void removeFromList(String listItemText, int position) {
		
		final int positionIndex = position;
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		
		
		alertDialog.setTitle("Romove from active list?");
		alertDialog.setMessage(listItemText);
		
		alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				itemRecommendation.remove(positionIndex);
				lvRecommendation.setAdapter(new RecomendationBaseAdapter(
						RecommendationActivity.this, itemRecommendation));
				Toast.makeText(RecommendationActivity.this, "Your fix is noted", Toast.LENGTH_LONG).show();
				
			}
		});

		alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		alertDialog.show();

	}
public void onClickMoreOption (View v)
{
	showDialog(0);
	
}
public void onClickDepartment (View v)
{
	showDialog(1);
	
}
}
