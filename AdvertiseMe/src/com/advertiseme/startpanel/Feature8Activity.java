package com.advertiseme.startpanel;

import android.os.Bundle;

public class Feature8Activity extends StartPanelActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView (R.layout.activity_feature8);
	    setTitleFromActivityLabel (R.id.title_text);
	}
}

