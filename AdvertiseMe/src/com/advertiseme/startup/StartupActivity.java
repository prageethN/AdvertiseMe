package com.advertiseme.startup;

import com.advertiseme.startpanel.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartupActivity extends Activity{
	boolean isRunning = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView (R.layout.activity_startup);
	    
	}
	


	@Override
	public void onStart() {
		super.onStart();
		setupStart();
	}
	void setupStart(){
		
		Thread background = new Thread(new Runnable() {
			
			public void run() {
				try {
					int currentcount = 0;
					int maxCount = 5;

					while (currentcount < maxCount && isRunning) {
						try {
							Thread.sleep(300);
							currentcount = currentcount + 1;
						} catch (InterruptedException e) {
							return;
						} catch (Exception e) {
							return;
						}
					}

					startActivity(new Intent(StartupActivity.this,
							SignInActivity.class));

				} catch (Throwable t) {
				}
			}
		});
		isRunning = true;
		// start the background thread
		background.start();
		isRunning = true;
	}
	public void run() {
		// TODO Auto-generated method stub
		int currentcount = 0;
		int maxCount = 5;

		while (currentcount < maxCount && isRunning) {
			try {
				Thread.sleep(300);
				currentcount = currentcount + 1;
			} catch (InterruptedException e) {
				return;
			} catch (Exception e) {
				return;
			}

		}
		startActivity(new Intent(getApplicationContext(), SignInActivity.class));
	}

	@Override
	public void onStop() {
		super.onStop();
		isRunning = false;

	}

	@Override
	public void onPause() {
		super.onPause();
		isRunning = false;
		finish();

	}
}
