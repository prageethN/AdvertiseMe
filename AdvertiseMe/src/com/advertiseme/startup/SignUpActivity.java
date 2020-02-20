package com.advertiseme.startup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.advertiseme.startpanel.Feature7Activity;
import com.advertiseme.startpanel.HomeActivity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.R.color;
import com.advertiseme.support.Base64;
import com.advertiseme.support.NotificationHandler;
import com.advertiseme.webservicecall.WebServiceCall;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class SignUpActivity extends Activity implements OnClickListener{
	TextView linkLogin;
	EditText txtUserName,txtFirstName,txtLastName,txtEmail,txtAddress,txtCity,txtState,
	txtMobileNo,txtHomeNo,txtOfficeNo,txtHomeLink,txtFacebook,txtGooglePlus,
	txtTwitter,txtSkype,txtPssword,txtRePassword;
	Spinner spCountry,spLanguage;
	ImageView imgProfileImage;
	
	String userID,userName,firstName,lastName,fullName,userAddress,userCity,userState,userCountry,userLanguage,
	mobileNo,homeNo,officeNo,homeLink,faceBook,googlePlus,userTwitter,userSkype,
	userPassword;
	private static final String TEMP_PHOTO_FILE = "temporary_holder.jpg";
	private static final int REQ_CODE_PICK_IMAGE = 200;
    Boolean isImagePick=false;
    
	private NotificationHandler notifyer;
	private WebServiceCall webServiceCall;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView (R.layout.activity_signup);
	    setupStart();
	}
	
	void setupStart(){
		
		imgProfileImage=(ImageView)findViewById(R.id.imgProfileImage);
		
		txtUserName=(EditText)findViewById(R.id.txtUserName);
		txtFirstName=(EditText)findViewById(R.id.txtFirstName);
		txtLastName=(EditText)findViewById(R.id.txtLastName);
		txtEmail=(EditText)findViewById(R.id.txtEmail);
		txtAddress=(EditText)findViewById(R.id.txtAddress);
		txtCity=(EditText)findViewById(R.id.txtCity);
		txtState=(EditText)findViewById(R.id.txtState);
		txtMobileNo=(EditText)findViewById(R.id.txtMobileNo);
		txtHomeNo=(EditText)findViewById(R.id.txtHomeNo);
		txtOfficeNo=(EditText)findViewById(R.id.txtOfficeNo);
		txtHomeLink=(EditText)findViewById(R.id.txtHomeLink);
		txtFacebook=(EditText)findViewById(R.id.txtFacebook);
		txtGooglePlus=(EditText)findViewById(R.id.txtGooglePlus);
		txtTwitter=(EditText)findViewById(R.id.txtTwitter);
		txtSkype=(EditText)findViewById(R.id.txtSkype);
		txtPssword=(EditText)findViewById(R.id.txtPssword);
		txtRePassword=(EditText)findViewById(R.id.txtRePassword);
		
		spCountry=(Spinner)findViewById(R.id.spCountry);
		spLanguage=(Spinner)findViewById(R.id.spLanguage);
		
		linkLogin = (TextView) findViewById(R.id.link_to_login);
		linkLogin.setOnClickListener(this); 
		
		notifyer=new NotificationHandler(this);
		webServiceCall=new WebServiceCall();
		
	}
	public void onClick(View view) {
		if(view==linkLogin){
		overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
		finish();
		}
	}
	public void onPickImageClick(View view){
		
		pickImageFromGallery();
	}
	
	public void onClickSignUp(View view){
		 if(isUserInputValid()){
			registerNewUser();			
			 
		 };
		
	}
	
	Boolean isUserInputValid(){
		
		if(txtUserName.getText().toString().trim().equalsIgnoreCase("")){
			notifyer.Alertbox("Error", "Please enter the username you preffred");
			return false;
		}
		if(txtFirstName.getText().toString().trim().equalsIgnoreCase("") && txtLastName.getText().toString().trim().equalsIgnoreCase("") ){
			notifyer.Alertbox("Error", "Please either your fisrt name or last name");
			return false;
		}
		if(!txtEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
		)){
			notifyer.Alertbox("Error", "Please enter a valid email address");
			return false;
		}
		if(txtPssword.getText().toString().trim().equalsIgnoreCase("")){
			notifyer.Alertbox("Error", "Please enter your password");
			return false;
		}
		if(txtPssword.getText().toString().length()<6){
			notifyer.Alertbox("Error", "Please enter a password with minimum of six charaters");
			return false;
		}
		if(!(txtPssword.getText().toString().trim().equalsIgnoreCase((txtRePassword.getText().toString().trim())))){
			notifyer.Alertbox("Error", "Password and repeat password should be identical");
			return false;
		}
		
		if(isUsernameExist()){
			notifyer.Alertbox("Error", "Username you entered is already taken");
			return false;
		}
		
		return true;
	}
	
	boolean isUsernameExist(){
		
		Boolean isUserExists=webServiceCall.isUsernameExist(txtUserName.getText().toString());			
		return isUserExists;
	}
	
	void registerNewUser(){
			
		ArrayList paraList =new ArrayList<String>();
		
		paraList.add((txtFirstName.getText().toString().trim()+" "+ txtLastName.getText().toString().trim()).trim());
		paraList.add(txtEmail.getText().toString());
		paraList.add(txtAddress.getText().toString());
		paraList.add(txtCity.getText().toString());
		paraList.add(txtState.getText().toString());
		paraList.add(spCountry.getSelectedItem().toString());
		paraList.add(spLanguage.getSelectedItem().toString());
		paraList.add(txtMobileNo.getText().toString());
		paraList.add(txtHomeNo.getText().toString());
		paraList.add(txtOfficeNo.getText().toString());
		paraList.add(txtHomeLink.getText().toString());
		paraList.add(txtFacebook.getText().toString());
		paraList.add(txtGooglePlus.getText().toString());
		paraList.add(txtTwitter.getText().toString());
		paraList.add(txtSkype.getText().toString());
		paraList.add(txtUserName.getText().toString());
		paraList.add(txtPssword.getText().toString());
		paraList.add(getProfileImage());
		Boolean isSuccess=webServiceCall.registerNewUser(paraList);
		if(isSuccess){
			notifyer.toast("Successfully registered");
			finish();
			
		}else{
			notifyer.toast("Could not complete your request, please try again");
			return;
		}
	
				
	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	
}
void pickImageFromGallery() {

	Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
			android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	photoPickerIntent.setType("image/*");
	photoPickerIntent.putExtra("crop", "true");
	photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
	photoPickerIntent.putExtra("outputFormat",
			Bitmap.CompressFormat.JPEG.toString());
	startActivityForResult(photoPickerIntent, REQ_CODE_PICK_IMAGE);
}

private Uri getTempUri() {
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

@Override
protected void onActivityResult(int requestCode, int resultCode,
		Intent imageReturnedIntent) {
	super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

	switch (requestCode) {
	case REQ_CODE_PICK_IMAGE:
		if (resultCode == RESULT_OK) {
			if (imageReturnedIntent != null) {

				File tempFile = getTempFile();
				String filePath = Environment.getExternalStorageDirectory()
						+ "/temporary_holder.jpg";
				System.out.println("path " + filePath);

				Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
				imgProfileImage.setImageBitmap(selectedImage);
				imgProfileImage.setAdjustViewBounds(true);
				imgProfileImage.setMinimumHeight(177);
				imgProfileImage.setMinimumWidth(177);
				imgProfileImage.setMaxHeight(177);
				imgProfileImage.setMaxWidth(177);
				isImagePick=true;
				//uploadProfilePic(selectedImage);
			}
		}
	}
}
String getProfileImage( ){
if(isImagePick){
File tempFile = getTempFile();
String filePath = Environment.getExternalStorageDirectory()
		+ "/temporary_holder.jpg";
System.out.println("path " + filePath);

    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
	ByteArrayOutputStream bao = new ByteArrayOutputStream();
	selectedImage.compress(Bitmap.CompressFormat.JPEG, 90, bao);
	byte [] ba = bao.toByteArray();
	String ba1=Base64.encodeBytes(ba);
	return ba1;	
}else{
		return "";
	}
	
}
}
