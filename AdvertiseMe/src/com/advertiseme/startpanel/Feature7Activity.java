package com.advertiseme.startpanel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import com.advertiseme.myadvertiseme.ExpiredListActivity;
import com.advertiseme.recent.UserActivityActivity;
import com.advertiseme.recent.UserViewsActivity;
import com.advertiseme.search.SearchPeopleActivity;
import com.advertiseme.user.ContactRequestActivity;
import com.advertiseme.user.EditProfileActivity;
import com.advertiseme.user.MyContactActivity;
import com.advertiseme.webservicecall.WebServiceCall;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class Feature7Activity extends StartPanelActivity implements
		OnClickListener, OnLongClickListener {
	Button button;
	EditText text, txtStatusMessage;
	ImageView profilePicture;
	LinearLayout lnFullName, lnEmail, lnChangePic, lnAddress, lnCity,
			lnCountry, lnState, lnLanguage, lnMobilePhone, lnHomePhone,
			lnOfficePhone, lnHomePage, lnFacebook, lnGooglePlus, lnTwitter,
			lnSkype;
	TextView txtFullName, txtEmail, txtPassword, txtAddress, txtCity,
			txtCountry, txtState, txtLanguage, txtMobilePhone, txtHomePhone,
			txtOfficePhone, txtHomePage, txtFacebook, txtGooglePlus,
			txtTwitter, txtSkype, txtNameHeading, txtLocation, txtLogoutUser,
			txtUserName,txtActivityCount,txtViewCount,txtContactCount,txtContactRequestCount;
	static final int EDIT_FIELD_REQUEST_PASSWORD = 100,
			EDIT_FIELD_REQUEST_NAME = 0, EDIT_FIELD_REQUEST_EMAIL = 1,
			EDIT_FIELD_REQUEST_CITY = 2, EDIT_FIELD_REQUEST_COUNTRY = 3,
			EDIT_FIELD_REQUEST_STATE = 4, EDIT_FIELD_REQUEST_LANGUAGE = 5,
			EDIT_FIELD_REQUEST_MPHONE = 6, EDIT_FIELD_REQUEST_HPHONE = 7,
			EDIT_FIELD_REQUEST_OPHONE = 8, EDIT_FIELD_REQUEST_LINK = 9,
			EDIT_FIELD_REQUEST_FACEBOOK = 10, EDIT_FIELD_REQUEST_GOOGLE = 11,
			EDIT_FIELD_REQUEST_TWITTER = 12, EDIT_FIELD_REQUEST_SKYPE = 13,
			EDIT_FIELD_REQUEST_ADDRESS = 14, EDIT_FIELD_REQUEST_STATUS = 15;

	private static final String TEMP_PHOTO_FILE = "img_user_temp.jpg";
	private static final int REQ_CODE_PICK_IMAGE = 200;
	Boolean isImagePick = false;

	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	public static final String PREFS_FULLNAME = "fullName";
	private static final String PREF_USERNAME = "userName";
	private static final String PREF_LOCATION = "userLocation";
	private static final String PREF_STATUS_MSG = "statusMessage";
	private static final String PREF_PROFILE_IMG = "userImage";

	WebServiceCall webServiceCall;
	SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feature7);
		setTitleFromActivityLabel(R.id.title_text);
		setupStart();
	}

	void setupStart() {

		webServiceCall = new WebServiceCall();

		TabHost tabHost = (TabHost) findViewById(R.id.tbHost);
		tabHost.setup();

		View tabView = createTabView(this, "Account");
		TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab 1").setIndicator(
				tabView);
		spec1.setContent(R.id.lnAccount);
		tabHost.addTab(spec1);

		tabView = createTabView(this, "Profile");
		TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab 2").setIndicator(
				tabView);
		spec2.setContent(R.id.lnProfile);
		tabHost.addTab(spec2);

		tabHost.setCurrentTab(1);

		profilePicture = (ImageView) findViewById(R.id.imgProfileImage);
		profilePicture.setOnLongClickListener(this);

		txtNameHeading = (TextView) findViewById(R.id.txtNameHeading);
		txtLocation = (TextView) findViewById(R.id.txtLocation);

		txtStatusMessage = (EditText) findViewById(R.id.text1);
		txtStatusMessage.setOnClickListener(this);
		txtLogoutUser = (TextView) findViewById(R.id.text2);
		txtLogoutUser.setOnClickListener(this);

		lnFullName = (LinearLayout) findViewById(R.id.lnFullName);
		lnFullName.setOnLongClickListener(this);
		lnEmail = (LinearLayout) findViewById(R.id.lnEmail);
		lnEmail.setOnLongClickListener(this);
		lnChangePic = (LinearLayout) findViewById(R.id.lnChangePic);
		lnChangePic.setOnLongClickListener(this);

		lnAddress = (LinearLayout) findViewById(R.id.lnAddress);
		lnAddress.setOnLongClickListener(this);
		lnCity = (LinearLayout) findViewById(R.id.lnCity);
		lnCity.setOnLongClickListener(this);
		lnCountry = (LinearLayout) findViewById(R.id.lnCountry);
		lnCountry.setOnLongClickListener(this);
		lnState = (LinearLayout) findViewById(R.id.lnState);
		lnState.setOnLongClickListener(this);
		lnLanguage = (LinearLayout) findViewById(R.id.lnLanguage);
		lnLanguage.setOnLongClickListener(this);

		lnMobilePhone = (LinearLayout) findViewById(R.id.lnMobilePhone);
		lnMobilePhone.setOnLongClickListener(this);
		lnHomePhone = (LinearLayout) findViewById(R.id.lnHomePhone);
		lnHomePhone.setOnLongClickListener(this);
		lnOfficePhone = (LinearLayout) findViewById(R.id.lnOfficePhone);
		lnOfficePhone.setOnLongClickListener(this);
		lnHomePage = (LinearLayout) findViewById(R.id.lnHomePage);
		lnHomePage.setOnLongClickListener(this);

		lnFacebook = (LinearLayout) findViewById(R.id.lnFacebook);
		lnFacebook.setOnLongClickListener(this);
		lnGooglePlus = (LinearLayout) findViewById(R.id.lnGooglePlus);
		lnGooglePlus.setOnLongClickListener(this);
		lnTwitter = (LinearLayout) findViewById(R.id.lnTwitter);
		lnTwitter.setOnLongClickListener(this);
		lnSkype = (LinearLayout) findViewById(R.id.lnSkype);
		lnSkype.setOnLongClickListener(this);

		txtPassword = (TextView) findViewById(R.id.txtPassword);
		txtFullName = (TextView) findViewById(R.id.txtFullName);
		txtEmail = (TextView) findViewById(R.id.txtEmail);

		txtAddress = (TextView) findViewById(R.id.txtAddress);
		txtCity = (TextView) findViewById(R.id.txtCity);
		txtCountry = (TextView) findViewById(R.id.txtCountry);
		txtState = (TextView) findViewById(R.id.txtState);
		txtLanguage = (TextView) findViewById(R.id.txtLanguage);
		txtHomePage = (TextView) findViewById(R.id.txtHomePage);

		txtMobilePhone = (TextView) findViewById(R.id.txtMobilePhone);
		txtMobilePhone.setOnLongClickListener(this);
		txtHomePhone = (TextView) findViewById(R.id.txtHomePhone);
		txtHomePhone.setOnLongClickListener(this);
		txtOfficePhone = (TextView) findViewById(R.id.txtOfficePhone);
		txtOfficePhone.setOnLongClickListener(this);
		txtHomePage = (TextView) findViewById(R.id.txtHomePage);
		lnHomePage.setOnLongClickListener(this);

		txtFacebook = (TextView) findViewById(R.id.txtFacebook);
		txtGooglePlus = (TextView) findViewById(R.id.txtGooglePlus);
		txtTwitter = (TextView) findViewById(R.id.txtTwitter);
		txtSkype = (TextView) findViewById(R.id.txtSkype);

		txtUserName = (TextView) findViewById(R.id.txtUserName);

		txtActivityCount=(TextView)findViewById(R.id.txtActivityCount);
		txtViewCount=(TextView)findViewById(R.id.txtViewCount);
		txtContactCount=(TextView)findViewById(R.id.txtContactCount);
		txtContactRequestCount=(TextView)findViewById(R.id.txtContactRequestCount);
		
		
		pref = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);

		txtFullName.setText(pref.getString(PREFS_FULLNAME, null));
		txtNameHeading.setText(pref.getString(PREFS_FULLNAME, null));
		txtLocation.setText(pref.getString(PREF_LOCATION, null));
		txtStatusMessage.setText(pref.getString(PREF_STATUS_MSG, null));
		txtLogoutUser.setText(pref.getString(PREF_USERNAME, null));
		txtUserName.setText(pref.getString(PREF_USERNAME, null));
		setProfileImage(pref.getString(PREF_PROFILE_IMG, null));

		setUserProfileDetail();
		getUserAcountCount();
	}

	private void setUserProfileDetail() {

		String[] resultList = webServiceCall.getUserProfileDetail(pref
				.getString(PREF_USERID, null));

		txtEmail.setText(resultList[3].replace("anyType{}", "Not specified"));
		txtAddress.setText(resultList[4].replace("anyType{}", "Not specified"));
		txtCity.setText(resultList[5].replace("anyType{}", "Not specified"));
		txtCountry.setText(resultList[6].replace("anyType{}", "Not specified"));
		txtState.setText(resultList[7].replace("anyType{}", "Not specified"));
		txtLanguage
				.setText(resultList[8].replace("anyType{}", "Not specified"));
		txtMobilePhone.setText(resultList[9].replace("anyType{}",
				"Not specified"));
		txtHomePhone.setText(resultList[10].replace("anyType{}",
				"Not specified"));
		txtOfficePhone.setText(resultList[11].replace("anyType{}",
				"Not specified"));
		txtHomePage.setText(resultList[12]
				.replace("anyType{}", "Not specified"));
		txtFacebook.setText(resultList[13]
				.replace("anyType{}", "Not specified"));
		txtGooglePlus.setText(resultList[14].replace("anyType{}",
				"Not specified"));
		txtTwitter
				.setText(resultList[15].replace("anyType{}", "Not specified"));
		txtSkype.setText(resultList[16].replace("anyType{}", "Not specified"));
		txtPassword.setText(resultList[20]
				.replace("anyType{}", "Not specified"));

	}
	
	private void getUserAcountCount() {
		
		String[] resultList = webServiceCall.getUserAcountCount(pref
				.getString(PREF_USERID, null));
		if(resultList!=null){
		txtActivityCount.setText(resultList[0]);
		txtViewCount.setText(resultList[1]);
		txtContactCount.setText(resultList[3]);
		txtContactRequestCount.setText(resultList[2]);
		}
	}
	private static View createTabView(Context context, String tabText) {
		View view = LayoutInflater.from(context).inflate(R.layout.custom_tab,
				null, false);
		TextView tv = (TextView) view.findViewById(R.id.tabTitleText);
		tv.setText(tabText);
		return view;
	}

	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view == txtStatusMessage) {
			startEditActivity(txtStatusMessage.getText().toString(),
					"status message", "", EDIT_FIELD_REQUEST_STATUS);
		}
		if (view == txtLogoutUser) {
			userLogout();
		}
	}

	public boolean onLongClick(View view) {

		if (view == profilePicture) {
			pickImageFromGallery();
		}

		if (view == lnFullName) {

			startEditActivity(txtFullName.getText().toString(), "name", "P",
					EDIT_FIELD_REQUEST_NAME);
		}
		if (view == lnEmail) {
			startEditActivity(txtEmail.getText().toString(), "email", "E",
					EDIT_FIELD_REQUEST_EMAIL);
		}
		if (view == lnChangePic) {
			pickImageFromGallery();
		}
		if (view == lnAddress) {

			startEditActivity(txtAddress.getText().toString(), "address", "A",
					EDIT_FIELD_REQUEST_ADDRESS);
		}
		if (view == lnCity) {

			startEditActivity(txtCity.getText().toString(), "city", "",
					EDIT_FIELD_REQUEST_CITY);
		}
		if (view == lnCountry) {
			startEditActivity(txtCountry.getText().toString(), "country",
					"CNT", EDIT_FIELD_REQUEST_COUNTRY);
		}
		if (view == lnState) {

			startEditActivity(txtState.getText().toString(), "state", "",
					EDIT_FIELD_REQUEST_STATE);
		}
		if (view == lnLanguage) {
			startEditActivity(txtLanguage.getText().toString(), "language",
					"LNG", EDIT_FIELD_REQUEST_LANGUAGE);
		}
		if (view == lnMobilePhone) {

			startEditActivity(txtMobilePhone.getText().toString(),
					"mobile phone", "MP", EDIT_FIELD_REQUEST_MPHONE);
		}
		if (view == lnHomePhone) {
			startEditActivity(txtHomePhone.getText().toString(), "home phone",
					"MP", EDIT_FIELD_REQUEST_HPHONE);
		}
		if (view == lnOfficePhone) {
			startEditActivity(txtOfficePhone.getText().toString(),
					"office phone", "MP", EDIT_FIELD_REQUEST_OPHONE);
		}
		if (view == lnHomePage) {

			startEditActivity(txtHomePage.getText().toString(), "home page",
					"URL", EDIT_FIELD_REQUEST_LINK);
		}
		if (view == lnFacebook) {
			startEditActivity(txtFacebook.getText().toString(),
					"facebook account profile", "URL",
					EDIT_FIELD_REQUEST_FACEBOOK);
		}
		if (view == lnGooglePlus) {

			startEditActivity(txtGooglePlus.getText().toString(),
					"google+ account profile", "URL", EDIT_FIELD_REQUEST_GOOGLE);
		}
		if (view == lnTwitter) {
			startEditActivity(txtTwitter.getText().toString(),
					"twitter account profile", "URL",
					EDIT_FIELD_REQUEST_TWITTER);
		}
		if (view == lnSkype) {
			startEditActivity(txtSkype.getText().toString(), "skype name", "",
					EDIT_FIELD_REQUEST_SKYPE);
		}
		return false;
	}

	void startEditActivity(String editText, String headerText, String property,
			int REQUEST_CODE) {
		Intent intent = new Intent();
		intent.setClass(Feature7Activity.this, EditProfileActivity.class);
		intent.putExtra("EDIT_TEXT", editText);
		intent.putExtra("HEADER_TEXT", headerText);
		intent.putExtra("PROPERTY", property);
		startActivityForResult(intent, REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {

		case (EDIT_FIELD_REQUEST_STATUS): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				if (newText.equalsIgnoreCase("Not specified")) {
					newText = "Enter a message here to share with all of your contacts";
				}
				txtStatusMessage.setText(newText);
			}
			break;
		}
		case (EDIT_FIELD_REQUEST_PASSWORD): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				txtPassword.setText(newText);
			}
			break;
		}
		case (EDIT_FIELD_REQUEST_NAME): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				txtFullName.setText(newText);
				txtNameHeading.setText(newText);
			}
			break;
		}
		case (EDIT_FIELD_REQUEST_EMAIL): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				txtEmail.setText(newText);
			}
			break;
		}
		case (EDIT_FIELD_REQUEST_ADDRESS): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				txtAddress.setText(newText);
			}
			break;
		}
		case (EDIT_FIELD_REQUEST_CITY): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				txtCity.setText(newText);
				txtLocation.setText(getLocationString());
			}
			break;
		}

		case (EDIT_FIELD_REQUEST_COUNTRY): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				txtCountry.setText(newText);
				txtLocation.setText(getLocationString());
			}
			break;
		}

		case (EDIT_FIELD_REQUEST_STATE): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				txtState.setText(newText);
				txtLocation.setText(getLocationString());
			}
			break;
		}

		case (EDIT_FIELD_REQUEST_LANGUAGE): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				txtLanguage.setText(newText);
			}
			break;
		}

		case (EDIT_FIELD_REQUEST_MPHONE): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				txtMobilePhone.setText(newText);
			}
			break;
		}

		case (EDIT_FIELD_REQUEST_HPHONE): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				txtHomePhone.setText(newText);
			}
			break;
		}

		case (EDIT_FIELD_REQUEST_OPHONE): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				txtOfficePhone.setText(newText);
			}
			break;
		}

		case (EDIT_FIELD_REQUEST_LINK): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				txtHomePage.setText(newText);
			}
			break;
		}

		case (EDIT_FIELD_REQUEST_FACEBOOK): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				txtFacebook.setText(newText);
			}
			break;
		}
		case (EDIT_FIELD_REQUEST_GOOGLE): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				txtGooglePlus.setText(newText);
			}
			break;
		}
		case (EDIT_FIELD_REQUEST_TWITTER): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				txtTwitter.setText(newText);
			}
			break;
		}
		case (EDIT_FIELD_REQUEST_SKYPE): {
			if (resultCode == Activity.RESULT_OK) {
				String newText = data.getStringExtra("RESULT_TEXT");
				txtSkype.setText(newText);
			}
			break;
		}
		case (REQ_CODE_PICK_IMAGE): {
			if (resultCode == RESULT_OK) {
				if (data != null) {

					File tempFile = getTempFile();
					String filePath = Environment.getExternalStorageDirectory()
							+ "/temporary_holder.jpg";
					System.out.println("path " + filePath);

					Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
					profilePicture.setImageBitmap(selectedImage);
					profilePicture.setAdjustViewBounds(true);
					profilePicture.setMinimumHeight(177);
					profilePicture.setMinimumWidth(177);
					profilePicture.setMaxHeight(177);
					profilePicture.setMaxWidth(177);
					isImagePick = true;
					// uploadProfilePic(selectedImage);
				}
			}
		}
		}
	}
	public void onClickActivity(View view){
		
		Intent intent=new Intent(getApplicationContext(),UserActivityActivity.class);
		intent.putExtra("USER_ID", pref.getString(PREF_USERID,null));
		startActivity(intent);
	}
	public void onClickViews(View view){
		
		Intent intent=new Intent(getApplicationContext(),UserViewsActivity.class);
		intent.putExtra("USER_ID", pref.getString(PREF_USERID,null));
		startActivity(intent);
	}
	public void onClickSearchUser(View view) {
		startActivity(new Intent(getApplicationContext(),
				SearchPeopleActivity.class));
	}

	public void onClickMyContact(View view) {
		startActivity(new Intent(getApplicationContext(),
				MyContactActivity.class));
	}

	public void onClickContactRequest(View view) {
		startActivity(new Intent(getApplicationContext(),
				ContactRequestActivity.class));
	}

	public void onClickChangePassword(View view) {
		Intent intent = new Intent();
		intent.setClass(Feature7Activity.this, EditProfileActivity.class);
		intent.putExtra("EDIT_TEXT", txtPassword.getText().toString());
		intent.putExtra("HEADER_TEXT", "password");
		intent.putExtra("PROPERTY", "YES");
		startActivityForResult(intent, EDIT_FIELD_REQUEST_PASSWORD);
	}

	void pickImageFromGallery() {

		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		photoPickerIntent.setType("image/*");
		photoPickerIntent.putExtra("crop", "true");
		photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTemperoryUri());
		photoPickerIntent.putExtra("outputFormat",
				Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(photoPickerIntent, REQ_CODE_PICK_IMAGE);
	}

	private Uri getTemperoryUri() {
		return Uri.fromFile(getTempFile());
	}

	private File getTempFile() {
		if (isSDCARDMounted()) {

			File f = new File(Environment.getExternalStorageDirectory(),
					TEMP_PHOTO_FILE);
			try {
				f.createNewFile();
			} catch (IOException e) {

			}
			return f;
		} else {
			return null;
		}
	}

	private boolean isSDCARDMounted() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED))
			return true;
		return false;
	}

	String getLocationString() {
		String defaultText = "Not specified";
		String locationString = "";
		String strCity = txtCity.getText().toString(), strState = txtState
				.getText().toString(), strCountry = txtCountry.getText()
				.toString();
		if (!strCity.equalsIgnoreCase(defaultText)) {
			locationString = locationString + strCity + ",";
		}
		if (!strState.equalsIgnoreCase(defaultText)) {
			locationString = locationString + strState + ",";
		}
		if (!strCountry.equalsIgnoreCase(defaultText)) {
			locationString = locationString + strCountry;
		}

		return locationString;
	}

	void userLogout() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				Feature7Activity.this);
		builder.setMessage(
				"Logout user " + txtLogoutUser.getText().toString() + " ?")
				.setTitle("Logout AdvertiseMe")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Feature7Activity.this.finish();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	void setProfileImage(String profileJpgPath) {
		try {
			if (profileJpgPath != "default") {
				BitmapFactory.Options options = new BitmapFactory.Options();
				// options.inSampleSize = 2;
				Bitmap bm = BitmapFactory.decodeFile(profileJpgPath, options);
				profilePicture.setImageBitmap(bm);

				profilePicture.setAdjustViewBounds(true);
				profilePicture.setMinimumHeight(200);
				profilePicture.setMinimumWidth(200);
				profilePicture.setMaxHeight(200);
				profilePicture.setMaxWidth(200);
			} else {
				profilePicture
						.setBackgroundResource(R.drawable.img_default_user);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
