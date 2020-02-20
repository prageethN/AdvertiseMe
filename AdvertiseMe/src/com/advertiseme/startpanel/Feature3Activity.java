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

import com.advertiseme.message.MessageInboxActivity;
import com.advertiseme.message.MessageSavedMessageActivity;
import com.advertiseme.message.MessageSentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * This is the activity for feature 3 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */

public class Feature3Activity extends StartPanelActivity 
{

/**
 * onCreate
 *
 * Called when the activity is first created. 
 * This is where you should do all of your normal static set up: create views, bind data to lists, etc. 
 * This method also provides you with a Bundle containing the activity's previously frozen state, if there was one.
 * 
 * Always followed by onStart().
 *
 * @param savedInstanceState Bundle
 */

@Override
protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_feature3);
    setTitleFromActivityLabel (R.id.title_text);
}
public void onClickInbox (View v)
{
	startActivity (new Intent(getApplicationContext(), MessageInboxActivity.class));
}
public void onClickSent (View v)
{
	startActivity (new Intent(getApplicationContext(), MessageSentActivity.class));
}
public void onClickSaved (View v)
{
	startActivity (new Intent(getApplicationContext(), MessageSavedMessageActivity.class));
}
} // end class
