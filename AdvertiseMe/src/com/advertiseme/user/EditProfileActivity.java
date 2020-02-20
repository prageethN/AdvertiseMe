package com.advertiseme.user;

import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.NotificationHandler;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class EditProfileActivity extends StartPanelActivity {

	EditText txtEditField, txtRepeatPassword, txtCurrentPassword;
	TextView txtHeader;
	Spinner spCountry, spLanguage;
	String textValue, textHeader;
	String properties = "NO";
	String putExtra;
	NotificationHandler alertBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editprofile);
		setTitleFromActivityLabel(R.id.title_text);
		setupStart();

	}

	void setupStart() {
		
		alertBox = new NotificationHandler(this);
		textValue = getIntent().getExtras().getSerializable("EDIT_TEXT")
				.toString();
		textHeader = getIntent().getExtras().getSerializable("HEADER_TEXT")
				.toString();
		properties = getIntent().getExtras().getSerializable("PROPERTY")
				.toString();

		txtHeader = (TextView) findViewById(R.id.txtHeader);
		txtEditField = (EditText) findViewById(R.id.txtEditItem);
		txtRepeatPassword = (EditText) findViewById(R.id.txtRepeatPassword);
		txtCurrentPassword = (EditText) findViewById(R.id.txtCurrentPassword);
		spCountry = (Spinner) findViewById(R.id.spCountryEdit);
		spLanguage = (Spinner) findViewById(R.id.spLanguageEdit);
		txtHeader.setText("Enter your " + textHeader);
		txtEditField.setText(textValue);

		if (properties.equalsIgnoreCase("YES")) {
			txtEditField.setText("");
			txtEditField.setHint("New password");
			txtEditField.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PASSWORD);
			txtRepeatPassword.setVisibility(android.view.View.VISIBLE);
			txtCurrentPassword.setVisibility(android.view.View.VISIBLE);
		}
		if (properties.equalsIgnoreCase("CNT")) {
			txtEditField.setVisibility(android.view.View.GONE);
			spCountry.setVisibility(android.view.View.VISIBLE);
			
			ArrayAdapter myAdap = (ArrayAdapter) spCountry.getAdapter(); //cast to an ArrayAdapter
			int spinnerPosition = myAdap.getPosition(textValue);			
			spCountry.setSelection(spinnerPosition);//set the default according to value
		}
		if (properties.equalsIgnoreCase("LNG")) {
			txtEditField.setVisibility(android.view.View.GONE);
			spLanguage.setVisibility(android.view.View.VISIBLE);			

			ArrayAdapter myAdap = (ArrayAdapter) spLanguage.getAdapter(); //cast to an ArrayAdapter
			int spinnerPosition = myAdap.getPosition(textValue);			
			spLanguage.setSelection(spinnerPosition);//set the default according to value

		}
		if (properties.equalsIgnoreCase("P")) {
			txtEditField.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
		}
		if (properties.equalsIgnoreCase("E")) {
			txtEditField.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		}
		if (properties.equalsIgnoreCase("A")) {
			txtEditField.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
		}
		if (properties.equalsIgnoreCase("MP")) {
			txtEditField.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_CLASS_PHONE);
		}
		if (properties.equalsIgnoreCase("URL")) {
			txtEditField.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_URI);
		}
		if(textValue.equalsIgnoreCase("Not Specified")){
			txtEditField.setText("");
		}
	}

	public void onClickSave(View view) {
		
		String putExtra = txtEditField.getText().toString();
		
		if (properties.equalsIgnoreCase("YES")) {

			String currentPassword = txtCurrentPassword.getText().toString();
			String passWord = txtEditField.getText().toString();
			String repeatPassword = txtRepeatPassword.getText().toString();

			if (!currentPassword.equalsIgnoreCase(textValue)) {
				alertBox.Alertbox("Invalid input",
						"Please enter your current password correctly");
			} else if (!passWord.equalsIgnoreCase(repeatPassword)) {
				alertBox.Alertbox("Invalid input",
						"Password and repeat password should be matched");
			} else {
				sendResponse();
			}
		} else if (properties.equalsIgnoreCase("P") && putExtra=="") {
			alertBox.Alertbox("Invalid input",
			"Please enter your name");
			
		} else {
			
			sendResponse();
		}

	}

	void sendResponse() {
		
		putExtra = txtEditField.getText().toString();
		
		Intent resultIntent = new Intent();

		if (properties.equalsIgnoreCase("CNT")) {
			putExtra = spCountry.getSelectedItem().toString();
		}
		if (properties.equalsIgnoreCase("LNG")) {
			putExtra = spLanguage.getSelectedItem().toString();

		}
		if(putExtra.trim().equalsIgnoreCase("")){
			putExtra="Not Specified";
		}
		resultIntent.putExtra("RESULT_TEXT", putExtra);
		setResult(Activity.RESULT_OK, resultIntent);
		finish();
	}

	public void onClickCancel(View view) {
		finish();
	}
}
