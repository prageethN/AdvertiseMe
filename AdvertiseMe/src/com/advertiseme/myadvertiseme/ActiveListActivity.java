package com.advertiseme.myadvertiseme;

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
import com.advertiseme.data.DatabaseHandler;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

public class ActiveListActivity extends StartPanelActivity{

	ListView lvActiveList;
	ArrayList<ListItemDetails> itemActiveList;
	TextView txtOptions,txtActiveListCount;
	String rowText,advertID;
	int rowPosition;
	
	WebServiceCall webServiceCall;
	DatabaseHandler db;
	SharedPreferences pref;

	String userID;

	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_activelist);
	    setTitleFromActivityLabel (R.id.title_text);
	    setupStart();
	}
	
	void setupStart(){
		
		db = new DatabaseHandler(this);

		pref = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);
		
		txtActiveListCount=(TextView)findViewById(R.id.txtActiveListCount);
		txtActiveListCount.setText("You have 0 items in the active list");
		
		itemActiveList= getActiveList();
		lvActiveList = (ListView) findViewById(R.id.lvActiveList);
		lvActiveList.setAdapter(new ActiveListBaseAdapter(this,
				itemActiveList));
		lvActiveList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {				
				Object o = lvActiveList.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;
				Intent intent=new Intent(ActiveListActivity.this,
						AdvertisementActivity.class);
				intent.putExtra("ADVERT_ID", obj_itemDetails.getItemId());
				startActivity(intent);
				
			}
		});
		lvActiveList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {				
				onLongListItemClick(view, position, id);
				return false;
			}
		});
	}
	protected void onLongListItemClick(View view, int position, long id) {
		Object o = lvActiveList.getItemAtPosition(position);
		ListItemDetails obj_itemDetails = (ListItemDetails) o;
		rowText = obj_itemDetails.getTextHeader();
		rowPosition=position;
		showDialog(0);
	}
	private ArrayList<ListItemDetails> getActiveList() {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList=null;
		ArrayList<String> rowList=null;
		
		webServiceCall=new WebServiceCall();
		
		resultList=webServiceCall.getAdvertListByUserPreference(getActiveListItems());
				
				
		if(resultList.size()>0){
			
		for(int i=0;i<resultList.size();i++){
			
				rowList=resultList.get(i);
				
				ListItemDetails item_details = new ListItemDetails();
				item_details.setItemID(rowList.get(0));
				item_details.setTextHeader(rowList.get(1));
				item_details.setTextNormal(rowList.get(2));
				item_details.setTextFooter(rowList.get(3));
				item_details.setItemRating(Float.parseFloat(rowList.get(4)));
				item_details.setTextExtra("("+rowList.get(5)+")");
				item_details.setPreviewURL(rowList.get(6));
				item_details.setConditionStatus(getAdvertisementStatus(rowList.get(7)));
				resultsList.add(item_details);
			
		}
		txtActiveListCount.setText("You have "+resultList.size()+" items in the active list");
		}
		
		return resultsList;
	}
	
	Boolean getAdvertisementStatus(String activeFlg){
		
		Boolean advertStatus=false;
		
		switch (Integer.parseInt(activeFlg)) {
		case 1:
			advertStatus=true;
			break;

		case 0:
			advertStatus=false;
			break;
		}
		return advertStatus;
	}

	String getActiveListItems(){
		String qString="";
		
		ArrayList<String> arrList=new ArrayList<String>();
		
		ArrayList<ArrayList<String>> resultList;
		ArrayList<String> rowList;
		
		resultList=db.getActiveList(userID);
		
		if(resultList!=null){
			
		for(int i=0;i<resultList.size();i++){
			
			rowList=resultList.get(i);
			
			if(i==0){
				qString=qString+rowList.get(2);
								
			}else{
				qString=qString+","+rowList.get(2);
			}
		}
		
		}
		return qString;
		
	}
protected Dialog onCreateDialog(int id) {
		
		String arrOptions[]={"Product Details","Delete"};
		 return new AlertDialog.Builder(ActiveListActivity.this)
        .setTitle("Options")
        .setItems(arrOptions, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                /* User clicked so do some stuff */                
                if(which==0){
                	startActivity(new Intent(ActiveListActivity.this,
    						AdvertisementActivity.class));
                 }if(which==1){
                	 removeFromList(rowText,rowPosition,advertID);
                 }
            }
        })
        .create();
		 
	 }
void removeFromList(String listItemText, int position,final String advertID) {
		
		final int positionIndex = position;
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		
		
		alertDialog.setTitle("Romove from active list?");
		alertDialog.setMessage(listItemText);
		
		alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				db.deleteFromActiveList(userID, advertID);
				itemActiveList.remove(positionIndex);
				lvActiveList.setAdapter(new WishListBaseAdapter(
						ActiveListActivity.this, itemActiveList));
				Toast.makeText(ActiveListActivity.this, "Successfully Removed from the list", Toast.LENGTH_LONG).show();
				
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
}
