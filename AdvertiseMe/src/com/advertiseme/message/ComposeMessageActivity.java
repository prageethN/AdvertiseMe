package com.advertiseme.message;

import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ComposeMessageActivity extends StartPanelActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView (R.layout.activity_new_message);
	    setTitleFromActivityLabel (R.id.title_text);
	}
	
	
	public void onClickAdd(View view){
		
		startActivity(new Intent(ComposeMessageActivity.this,
				AddReceiptantActivity.class));
	}
public void onClickSend(View view){
	startActivity(new Intent(ComposeMessageActivity.this,
			AddReceiptantActivity.class));
	
	}
	
}

