package com.advertiseme.support;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

public class NotificationHandler {

	Context conUI;

	public NotificationHandler() {

	}

	public NotificationHandler(Context _alertContext) {
		this.conUI = _alertContext;
	}

	public void Alertbox(String title, String mymessage) {

		new AlertDialog.Builder(conUI).setMessage(mymessage).setTitle(title)
				.setCancelable(true)
				.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						// if(whichButton == DialogInterface.BUTTON_NEUTRAL)
						// dialog.dismiss();

					}

				}).show();

	}

	public void LoadingBox() {
		ProgressDialog dialog = ProgressDialog.show(conUI, "", "Loading...",
				true);

		// make the progress bar cancel
		dialog.setCancelable(true);

	}

	public void showNoConnectionDialog() {
		final Context ctx = conUI;
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setCancelable(true);
		builder.setMessage("You cannot complete this task at this time. Please check your mobile network settings and try again.");
		builder.setTitle("Cannot connet to Internet");
		builder.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						ctx.startActivity(new Intent(
								Settings.ACTION_WIRELESS_SETTINGS));
					}
				});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			public void onCancel(DialogInterface dialog) {
				return;
			}
		});

		builder.show();
	}
	public void toast (String msg)
	{
	    Toast.makeText (conUI, msg, Toast.LENGTH_SHORT).show ();
	} // end toast
}
