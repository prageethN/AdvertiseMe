package com.advertiseme.startup;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import com.advertiseme.startpanel.HomeActivity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.R.color;
import com.advertiseme.support.NotificationHandler;
import com.advertiseme.webservicecall.WebServiceCall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SignInActivity extends Activity implements OnClickListener {

	private EditText txtUserName, txtPassword;
	private Button btnSignIn;
	private LinearLayout linkToRegister;
	private String userName="", passWord="", serviceCallReponce="";
	private NotificationHandler notifyer;
	private WebServiceCall webServiceCall;

	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	private static final String PREF_USERNAME= "userName";
	public static final String PREFS_FULLNAME = "fullName";
	private static final String PREF_LOCATION = "userLocation";
	private static final String PREF_STATUS_MSG= "statusMessage";
	private static final String PREF_PROFILE_IMG="userImage";
	
	private static final String PATH = "/sdcard/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
		setContentView(R.layout.activity_signin);
		setupStart();
	}

	void setupStart() {
		txtUserName = (EditText) findViewById(R.id.txtUserName);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		linkToRegister = (LinearLayout) findViewById(R.id.linkToRegister);
		linkToRegister.setOnClickListener(this);
		btnSignIn = (Button) findViewById(R.id.btnSignIn);
		btnSignIn.setOnClickListener(this);

		notifyer = new NotificationHandler(this);
		webServiceCall = new WebServiceCall();
	}

	public void onClick(View view) {

		if (view == btnSignIn) {
			
			authenticateLogin()	;				

		}
		if (view == linkToRegister) {

			startActivity(new Intent(SignInActivity.this, SignUpActivity.class));

		}

	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		 
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();		
	}

	 void authenticateLogin() {

		
		userName = txtUserName.getText().toString();
		passWord = txtPassword.getText().toString();

		if (isUserInputsValid()) {

			serviceCallReponce = webServiceCall.authenticateLogin(userName,
					passWord);
			
		if (serviceCallReponce.equalsIgnoreCase("100")) {
		
			notifyer.Alertbox("Error",
					"The username or password you entered is incorrect");
			return;
		} else if (serviceCallReponce.equalsIgnoreCase("200")) {
			notifyer.toast(
					"Server is not responding, please try again");
			return;
		}
		else if (serviceCallReponce.equalsIgnoreCase("300")) {
			notifyer.toast(
					"Could not establish the connection to server, please check your internet connectivity");
			return;
		}else{
			initiateUserLogin(serviceCallReponce);
		}
		} else{
			notifyer.Alertbox("Error", "Please enter both username and password"
			);
			return;
		}
	
	}

	boolean isUserInputsValid() {

		if (userName.trim().equalsIgnoreCase("")
				|| passWord.trim().equalsIgnoreCase("")) {
			
			return false;
		}

		return true;
	}
	void initiateUserLogin(String userID){
		
		String defaultText="";
		String userFullName,userLocation,userStatusMessage,userProfileImageURL,userName;
		String [] arrUserDetail=webServiceCall.getUserProfileDetail(userID);
		if(!(arrUserDetail.length==0)){
		userFullName= arrUserDetail[1];
		userStatusMessage=arrUserDetail[2];
		userProfileImageURL=getProfileImage(userID,arrUserDetail[18]);
		userName=arrUserDetail[19];
		
		userLocation="";
		if(!arrUserDetail[5].equalsIgnoreCase(defaultText)){
			userLocation=userLocation+arrUserDetail[5]+",";
		}
		if(!arrUserDetail[7].equalsIgnoreCase(defaultText)){
			userLocation=userLocation+arrUserDetail[7]+",";
		}
		if(!arrUserDetail[6].equalsIgnoreCase(defaultText)){
			userLocation=userLocation+arrUserDetail[6];
		}
		
		 getSharedPreferences(PREF_PROFILE,MODE_PRIVATE) .edit()
		 .putString(PREFS_FULLNAME, userFullName)
		 .putString(PREF_USERID, serviceCallReponce)
		 .putString(PREF_USERNAME, userName)
		 .putString(PREF_LOCATION, userLocation)
		 .putString(PREF_STATUS_MSG, userStatusMessage)
		 .putString(PREF_PROFILE_IMG, userProfileImageURL).commit();
		  
		  txtUserName.setText("");
		  txtPassword.setText("");
		  Intent i = new Intent(getApplicationContext(),
					LoadingDialogActivity.class); 
		  startActivity(i);
		 
		}else{
			notifyer.toast(
			"Server is not responding, please try again");
			return;
		}
	}
	public String  getProfileImage(String userID,String resourceURL){
		String profileImage="";
		if(DownloadFromUServer(PATH+userID+".jpg",resourceURL)){
			profileImage=PATH+userID+".jpg";
		}else{
			profileImage="default";
		}
		
		return profileImage;
	}
	public Boolean DownloadFromUServer(String fileName, String resourceURL) {
        try {
                URL url = new URL(resourceURL); //you can write here any link
                File file = new File(fileName);

                long startTime = System.currentTimeMillis();
                System.out.print("Starting download......from " + url);
                URLConnection ucon = url.openConnection();
                InputStream is = ucon.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                /*
                 * Read bytes to the Buffer until there is nothing more to read(-1).
                 */
                ByteArrayBuffer baf = new ByteArrayBuffer(50);
                int current = 0;
                while ((current = bis.read()) != -1) {
                        baf.append((byte) current);
                }

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(baf.toByteArray());
                fos.close();
                System.out.print("Download Completed in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
                
        } catch (IOException e) {
        	 System.out.print("Error: " + e);
        	 return false;
        }
        return true;
}
}
