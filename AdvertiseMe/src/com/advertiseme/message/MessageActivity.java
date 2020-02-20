package com.advertiseme.message;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.webservicecall.WebServiceCall;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageActivity extends StartPanelActivity {
	
	ImageView imgSenderProfilePic;
	TextView textSenderName,textSenderLocation,textMessageTopic,
	textMessageDate,txtMessageContent,txtMessageIndex;
	
	WebServiceCall webServiceCall;
	SharedPreferences pref;

	String messageID,userID,messageCount,messageIndex,messageType;

	private static final String PREF_PROFILE = "userProfile";
	private static final String PREF_USERID = "userID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView (R.layout.activity_message);
	    setTitleFromActivityLabel (R.id.title_text);
	    setupStart();
	}
	
	void setupStart(){
		
		webServiceCall = new WebServiceCall();

		pref = getSharedPreferences(PREF_PROFILE, MODE_PRIVATE);
		userID = pref.getString(PREF_USERID, null);
		
		try {
			messageID = getIntent().getExtras().getSerializable("MESSAGE_ID")
			.toString();
			messageCount = getIntent().getExtras().getSerializable("MESSAGE_COUNT")
			.toString();
			messageIndex = getIntent().getExtras().getSerializable("MESSAGE_INDEX")
			.toString();
			messageType = getIntent().getExtras().getSerializable("MESSAGE_TYPE")
			.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		imgSenderProfilePic=(ImageView)findViewById(R.id.list_image);
		
		textSenderName=(TextView)findViewById(R.id.textSenderName);
		textSenderLocation=(TextView)findViewById(R.id.textSenderLocation);
		textMessageTopic=(TextView)findViewById(R.id.textMessageTopic);
		textMessageDate=(TextView)findViewById(R.id.textMessageDate);
		txtMessageContent=(TextView)findViewById(R.id.txtMessageContent);
		txtMessageIndex=(TextView)findViewById(R.id.txtMessageIndex);
		
		setMessageDetail();
		
	}
	
	void setMessageDetail(){
		
		String []resultList = webServiceCall.getMessageDetail(messageID,messageType);
		
		textSenderName.setText(resultList[7]);
		textSenderLocation.setText(getLocationString(resultList[8],resultList[9],resultList[10]));
		textMessageTopic.setText(resultList[1]);
		textMessageDate.setText(resultList[3]);
		txtMessageContent.setText(resultList[2]);
		
		String imgUrl=resultList[11];
		Bitmap bimage=  getBitmapFromURL(imgUrl);
		imgSenderProfilePic.setImageBitmap(bimage);
		
		txtMessageIndex.setText(messageIndex+" of "+messageCount);
	}
	String getLocationString(String strCity,String strState,String strCountry ) {
		String defaultText = "";
		String locationString = "";
	
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
	public void onClickSenderProfile(View view){
		
	}
	public void onClickDown(View view){
		
	}
	public void onClickUp(View view){
		
	}
	public void onClickSave(View view){
		
	}
	public void onClickReply(View view){
		showDialog(0);
	}
	public void onClickNew(View view){

		startActivity(new Intent(getApplicationContext(),
				ComposeMessageActivity.class));
	}
	public void onClickDelete(View view){
		deleteMessage();
	}
protected Dialog onCreateDialog(int id) {
		
	switch (id) {
	case 0:
		String arrOptions[]={"Reply","Forward"};
		 return new AlertDialog.Builder(MessageActivity.this)
       .setTitle("Options")
       .setItems(arrOptions, new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {

               /* User clicked so do some stuff */                
               if(which==0){
               	startActivity(new Intent(getApplicationContext(),
           				ComposeMessageActivity.class));
                }if(which==1){
               	 startActivity(new Intent(getApplicationContext(),
               			ComposeMessageActivity.class));
                }
           }
       })
       .create();
		
	case 1:
		String arrOptions2[]={"Forward"};
		 return new AlertDialog.Builder(MessageActivity.this)
       .setTitle("Options")
       .setItems(arrOptions2, new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {

               /* User clicked so do some stuff */                
               if(which==0){
               	startActivity(new Intent(getApplicationContext(),
           				MessageActivity.class));
                }
           }
       })
       .create();
		
	}
		return null;
		 
	 }
void deleteMessage(){
	AlertDialog.Builder builder = new AlertDialog.Builder(
			MessageActivity.this);
	builder.setMessage("Are you sure you want to delete this messege ?")
			.setTitle("Delete Message")
			.setCancelable(false)
			.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int id) {
							MessageActivity.this.finish();
						}
					})
			.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int id) {
							dialog.cancel();
						}
					});
	AlertDialog alert = builder.create();
	alert.show();
}
}

