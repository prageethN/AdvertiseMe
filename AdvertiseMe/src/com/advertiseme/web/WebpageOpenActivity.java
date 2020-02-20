package com.advertiseme.web;

import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class WebpageOpenActivity extends StartPanelActivity {
	
	WebView webView;
	String webUrl=null;
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView (R.layout.activity_webpage_open);
	    setTitleFromActivityLabel (R.id.title_text);
	    setStartUp();
	}
	
	void setStartUp(){
		
		webUrl = getIntent().getExtras().getSerializable("WEB_URL")
		.toString();
		webView = (WebView) findViewById(R.id.webPage);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(webUrl);
	}
}

