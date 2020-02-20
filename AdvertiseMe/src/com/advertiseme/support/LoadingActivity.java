package com.advertiseme.support;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.advertiseme.myadvertiseme.ActiveListingActivity;
import com.advertiseme.startpanel.HomeActivity;
import com.advertiseme.startpanel.R;


public class LoadingActivity extends Activity {
   
    @Override
    protected void onCreate(Bundle icicle) {
        // Be sure to call the super class.
        super.onCreate(icicle);

        // Have the system blur any windows behind this one.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        
        // See assets/res/any/layout/translucent_background.xml for this
        // view layout definition, which is being set here as
        // the content of our screen.
        setContentView(R.layout.activity_loadingdialog);
       // setProgressBar();
        
        ComponentName activity=getCallingActivity();  
        if(activity.getClassName()=="LoadingActivity"){
        	
        	Toast.makeText(getApplicationContext(), "False", Toast.LENGTH_LONG);
        	
        };
       
    }
    @Override
	 public void onBackPressed() {
    	startActivity(new Intent(getApplicationContext(),
				HomeActivity.class));
	   
	 }
   
void setProgressBar(){
		
		new Thread() {

		@Override
		public void run() {

		try{

		sleep(3000);
		startActivity(new Intent(getApplicationContext(),
				HomeActivity.class));
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		} catch (Exception e) {

		Log.e("tag", e.getMessage());

		}

		// dismiss the progress dialog

	

		}

		}.start();

	}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	finish();
}

}

