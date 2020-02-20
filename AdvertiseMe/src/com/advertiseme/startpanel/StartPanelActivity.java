/*
 * Copyright (C) 2011 Wglxy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.advertiseme.startpanel;

import com.advertiseme.nearby.MyMapLocationActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the base class for activities in the dashboard application.
 * It implements methods that are useful to all top level activities.
 * That includes: (1) stub methods for all the activity lifecycle methods;
 * (2) onClick methods for clicks on home, search, feature 1, feature 2, etc.
 * (3) a method for displaying a message to the screen via the Toast class.
 *
 */

public abstract class StartPanelActivity extends Activity 
{


@Override
protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_default);
}

@Override
protected void onDestroy ()
{
   super.onDestroy ();
}

@Override
protected void onPause ()
{
   super.onPause ();
}

@Override
protected void onRestart ()
{
   super.onRestart ();
}

@Override
protected void onResume ()
{
   super.onResume ();
  
}

@Override
protected void onStart ()
{
   super.onStart ();
}

@Override
protected void onStop ()
{
   super.onStop ();
}

/**
 */
// Click Methods

/**
 * Handle the click on the home button.
 * 
 * @param v View
 * @return void
 */

public void onClickHome (View v)
{
    goHome (this);
}

/**
 * Handle the click on the search button.
 * 
 * @param v View
 * @return void
 */

public void onClickSearch (View v)
{
    startActivity (new Intent(getApplicationContext(), Feature2Activity.class));
}

/**
 * Handle the click on the About button.
 * 
 * @param v View
 * @return void
 */

public void onClickAbout (View v)
{
	startActivity (new Intent(getApplicationContext(), AboutActivity.class));
}
public void onClickSlideMenu (View v)
{
	
    startActivity (new Intent(getApplicationContext(), SlideMenuActivity.class));
    overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right );
}

/**
 * Handle the click of a Feature button.
 * 
 * @param v View
 * @return void
 */

public void onClickFeature (View v)
{
    int id = v.getId ();
    int x=R.id.home_btn_feature1 ;
    try{
    switch (id) {
      case R.id.home_btn_feature1 :
           startActivity (new Intent(getApplicationContext(), Feature1Activity.class));
           break;
      case R.id.home_btn_feature2 :
           startActivity (new Intent(getApplicationContext(), Feature2Activity.class));
           break;
      case R.id.home_btn_feature3 :
           startActivity (new Intent(getApplicationContext(), Feature3Activity.class));
           break;
      case R.id.home_btn_feature4 :
          //startActivity (new Intent(getApplicationContext(), Feature4Activity.class));
    	  startActivity(new Intent(getApplicationContext(),
    			  MyMapLocationActivity.class));
    	  break;
      case R.id.home_btn_feature5 :
           startActivity (new Intent(getApplicationContext(), Feature5Activity.class));
           break;
      case R.id.home_btn_feature6 :
           startActivity (new Intent(getApplicationContext(), Feature6Activity.class));
           break;
      case R.id.home_btn_feature7 :
          startActivity (new Intent(getApplicationContext(), Feature7Activity.class));
          break;
      case R.id.home_btn_feature8 :
          startActivity (new Intent(getApplicationContext(), Feature8Activity.class));
          break;
      default: 
    	   break;
    }
    }catch(Exception ex){
    	toast("Connection to the server is lost, please check your network connectivity");
    }
}

/**
 */
// More Methods

/**
 * Go back to the home activity.
 * 
 * @param context Context
 * @return void
 */

public void goHome(Context context) 
{
    final Intent intent = new Intent(context, HomeActivity.class);
    intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
    context.startActivity (intent);
}

/**
 * Use the activity label to set the text in the activity's title text view.
 * The argument gives the name of the view.
 *
 * <p> This method is needed because we have a custom title bar rather than the default Android title bar.
 * See the theme definitons in styles.xml.
 * 
 * @param textViewId int
 * @return void
 */

public void setTitleFromActivityLabel (int textViewId)
{
    TextView tv = (TextView) findViewById (textViewId);
    if (tv != null) tv.setText (getTitle ());
} // end setTitleText

/**
 * Show a string on the screen via Toast.
 * 
 * @param msg String
 * @return void
 */

public void toast (String msg)
{
    Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_SHORT).show ();
} // end toast

/**
 * Send a message to the debug log and display it using Toast.
 */
public void trace (String msg) 
{
    Log.d("Demo", msg);
    toast (msg);
}

} // end class
