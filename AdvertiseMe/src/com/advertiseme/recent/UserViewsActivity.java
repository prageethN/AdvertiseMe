package com.advertiseme.recent;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.advertiseme.advertisement.AdvertBaseAdapter;
import com.advertiseme.advertisement.AdvertisementActivity;
import com.advertiseme.message.MessageActivity;
import com.advertiseme.message.MessageInboxActivity;
import com.advertiseme.startpanel.AboutActivity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;

public class UserViewsActivity extends StartPanelActivity implements
		OnClickListener {

	ListView lvViews;
	ArrayList<ListItemDetails> itemViewsList;

	Button btnDepartment;
	TextView txtHeading;

	WebServiceCall webServiceCall;
	SharedPreferences pref;

	String userID;

	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_views_list);
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

		txtHeading = (TextView) findViewById(R.id.txtSearchHeading);
		txtHeading.setText("Recently viewed items (0) ");
		itemViewsList = getAdvertList(userID, 1);

		lvViews = (ListView) findViewById(R.id.lvViewsList);
		lvViews.setAdapter(new UserViewsBaseAdapter(this, itemViewsList));
		lvViews.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvViews.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;

				Intent intent = new Intent(UserViewsActivity.this,
						AdvertisementActivity.class);
				intent.putExtra("ADVERT_ID", obj_itemDetails.getItemId());

				startActivity(intent);
			}
		});

		

	}

	private ArrayList<ListItemDetails> getAdvertList(String qString, int typeFlg) {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;

		WebServiceCall webServiceCall = new WebServiceCall();

		resultList = webServiceCall.getUserViewsList(userID);
		

		if (resultList != null) {
			txtHeading.setText("Recently viewed items " + " (" + resultList.size()
					+ ") ");
			if (resultList.size() > 0) {

				for (int i = 0; i < resultList.size(); i++) {

					rowList = resultList.get(i);

					ListItemDetails item_details = new ListItemDetails();
					item_details.setItemID(rowList.get(0));
					item_details.setTextHeader(rowList.get(1));
					item_details.setTextNormal(rowList.get(2));
					item_details.setTextFooter(rowList.get(3));
					item_details.setPreviewURL(rowList.get(4));
					resultsList.add(item_details);

				}
			}
		}
		return resultsList;
	}

	private String[] getDepartmenttList() {

		String arrDepartmentList[] = new String[5];
		arrDepartmentList[0] = "Computers and tablets (10)";
		arrDepartmentList[1] = "Cell phones and accessories (21)";
		arrDepartmentList[2] = "Cameras and accessories (102)";
		arrDepartmentList[3] = "Computers and tablets (1)";
		arrDepartmentList[4] = "Electronics (4)";

		return arrDepartmentList;
	}

	protected Dialog onCreateDialog(int id) {

		return new AlertDialog.Builder(UserViewsActivity.this)
				.setTitle("Choose Department")
				.setItems(getDepartmenttList(),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								/* User clicked so do some stuff */
								String[] items = getDepartmenttList();
								new AlertDialog.Builder(UserViewsActivity.this)
										.setMessage(
												"You selected: " + items[which])
										.show();
							}
						}).create();

	}

	public void onClick(View view) {

		showDialog(0);

	}

	public void onClickMoreOption(View v) {
		startActivity(new Intent(getApplicationContext(), AboutActivity.class));
	}
}
