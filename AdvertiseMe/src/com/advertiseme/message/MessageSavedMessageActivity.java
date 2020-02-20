package com.advertiseme.message;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.advertiseme.advertisement.AdvertBaseAdapter;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

public class MessageSavedMessageActivity extends StartPanelActivity implements
		OnClickListener {

	ListView lvMessage;
	ArrayList<ListItemDetails> itemMessage;
	Button btnNewMessage;

	TextView txtSavedMessageCount;
	
	WebServiceCall webServiceCall;
	SharedPreferences pref;

	String userID;

	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messagesaved);
		setTitleFromActivityLabel(R.id.title_text);
		setupStart();
	}

	void setupStart() {

		webServiceCall = new WebServiceCall();

		pref = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);
		
		try {
			userID = getIntent().getExtras().getSerializable("USER_ID")
			.toString();

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		txtSavedMessageCount=(TextView)findViewById(R.id.txtSavedMessageCount);
		
		itemMessage = getMessageList();
		lvMessage = (ListView) findViewById(R.id.lvMessageSaved);
		lvMessage.setAdapter(new MessageBaseAdapter(this, itemMessage));
		lvMessage.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvMessage.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;

				String mailID=obj_itemDetails.getItemId();
				
				Intent intent=new Intent(MessageSavedMessageActivity.this,
						MessageActivity.class);
				intent.putExtra("MESSAGE_ID", mailID);
				intent.putExtra("MESSAGE_COUNT",lvMessage.getCount() );
				intent.putExtra("MESSAGE_INDEX", position+1);
				intent.putExtra("MESSAGE_TYPE", 3); // Saved message
				startActivity(intent);
			}
		});
		

	}

	private ArrayList<ListItemDetails> getMessageList() {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;

		resultList = webServiceCall.getMessageSavedList(userID);

		if (resultList != null) {

			if (resultList.size() > 0) {

				for (int i = 0; i < resultList.size(); i++) {

					rowList = resultList.get(i);

					ListItemDetails item_details = new ListItemDetails();
					item_details.setItemID(rowList.get(0));
					item_details.setTextHeader(rowList.get(7));
					item_details.setTextNormal(rowList.get(1));
					item_details.setTextFooter(rowList.get(2));
					item_details.setTextExtra(rowList.get(3));
					item_details.setConditionStatus(false);
					item_details.setConditionOption(0);
					item_details.setPreviewURL(rowList.get(11));
					resultsList.add(item_details);

				}

			}
			txtSavedMessageCount.setText("Messages ("+resultsList.size() +")");
		}
		/*ListItemDetails item_details = new ListItemDetails();
		item_details.setTextHeader("Dinusha87");
		item_details.setTextNormal("Apple iMac you are talking");
		item_details.setTextFooter("Hi prageeth, I have the iMac you requested");
		item_details.setTextExtra("20,Nov 2012");
		item_details.setConditionStatus(true);
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("NipunaDev");
		item_details.setTextNormal("Can you provide the item?");
		item_details.setTextFooter("Hi prageeth, Prize category is ok , if you buy let me know");
		item_details.setTextExtra("20,Nov 2012");
		item_details.setConditionStatus(true);
		resultsList.add(item_details);*/

		return resultsList;
	}

	public void onClick(View view) {
		// TODO Auto-generated method stub
		
	}
	public void onClickNew(View view) {
		// TODO Auto-generated method stub
		startActivity(new Intent(getApplicationContext(),
				ComposeMessageActivity.class));
	}
}
