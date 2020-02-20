package com.advertiseme.resource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.util.ByteArrayBuffer;

import com.advertiseme.startpanel.AboutActivity;
import com.advertiseme.startpanel.Feature2Activity;
import com.advertiseme.startpanel.HomeActivity;
import com.advertiseme.startpanel.R;
import com.advertiseme.startpanel.StartPanelActivity;
import com.advertiseme.support.ListItemDetails;
import com.advertiseme.webservicecall.WebServiceCall;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ResourceActivity extends Activity{
	
	HorizontalListView lvResource;
	ArrayList<ListItemDetails> itemResourceList;
	String adverID;
	
	private static final String PATH = "/sdcard/temp/";
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_resource);
		setTitleFromActivityLabel (R.id.title_text);
	    setupStart();
	    
	}
	
	void setupStart(){
		
		adverID = getIntent().getExtras().getSerializable("ADVERT_ID")
		.toString();
		
		itemResourceList= getDealList();
		lvResource = (HorizontalListView) findViewById(R.id.listview);
		lvResource.setAdapter(new ResourceBaseAdapter(this,
				itemResourceList));
		lvResource.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lvResource.getItemAtPosition(position);
				ListItemDetails obj_itemDetails = (ListItemDetails) o;
				openResourceFile(Integer.parseInt(obj_itemDetails.getTextNormal()), obj_itemDetails.getTextExtra());
			}
		});
		
	}
	private ArrayList<ListItemDetails> getDealList() {

		ArrayList<ListItemDetails> resultsList = new ArrayList<ListItemDetails>();

		
		ArrayList<ArrayList> resultList=null;
		ArrayList<String> rowList=null;
		
		WebServiceCall webServiceCall=new WebServiceCall();
		
		resultList=webServiceCall.getResourceList(adverID);
		
		if(resultList!=null){
			
		for(int i=0;i<resultList.size();i++){
			
				rowList=resultList.get(i);
				
				ListItemDetails item_details = new ListItemDetails();
				
				item_details.setTextHeader(rowList.get(0));
				item_details.setTextNormal(rowList.get(1).replace("000", ""));								
				item_details.setTextExtra(rowList.get(3));
				item_details.setTextFooter(rowList.get(2));
				resultsList.add(item_details);
			
		}
		}
		/*
		ListItemDetails item_details = new ListItemDetails();
		item_details.setTextHeader("Sony Bluetooth Smart watch MN2");
		item_details.setTextNormal("1");			
		item_details.setTextExtra("http://192.168.69.1/MTutorService/App_Resources/AppleiPhone4S.flv");
		item_details.setTextFooter("http://192.168.69.1/MTutorService/App_Resources/Thumbnail/AppleiPhone4S.flv_t_.jpg");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("iPhone 4S 16GB white smartphone");
		item_details.setTextNormal("2");		
		item_details.setTextExtra("http://192.168.69.1/MTutorService/App_Resources/AppleiPhone4S.jpg"); 
		item_details.setTextFooter("http://192.168.69.1/MTutorService/App_Resources/Thumbnail/AppleiPhone4S.jpg_t_.jpg");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Sony Xperia S - Dual core smartphone");
		item_details.setTextNormal("3");		
		item_details.setTextExtra("http://192.168.69.1/MTutorService/App_Resources/AppleiPhone4S.pdf");
		item_details.setTextFooter("http://192.168.69.1/MTutorService/App_Resources/Thumbnail/AppleIphone4S.pdf_t_.jpg");
		resultsList.add(item_details);

		item_details = new ListItemDetails();
		item_details.setTextHeader("Sony Xperia arc - Android smart phone");
		item_details.setTextNormal("4");		
		item_details.setTextExtra("http://192.168.69.1/MTutorService/App_Resources/sample.doc");
		item_details.setTextFooter("http://192.168.69.1/MTutorService/App_Resources/Thumbnail/default_word.jpg");
		resultsList.add(item_details);
		
		item_details = new ListItemDetails();
		item_details.setTextHeader("Sony Xperia arc - Android smart phone");
		item_details.setTextNormal("5");		
		item_details.setTextExtra("http://192.168.69.1/MTutorService/App_Resources/sample.ppt");
		item_details.setTextFooter("http://192.168.69.1/MTutorService/App_Resources/Thumbnail/default_powerpoint.jpg");
		resultsList.add(item_details);
		
		item_details = new ListItemDetails();
		item_details.setTextHeader("Sony Xperia arc - Android smart phone");
		item_details.setTextNormal("6");		
		item_details.setTextExtra("http://192.168.69.1/MTutorService/App_Resources/sample.xls");
		item_details.setTextFooter("http://192.168.69.1/MTutorService/App_Resources/Thumbnail/default_excel.jpg");
		resultsList.add(item_details);*/

		return resultsList;
	}
	void openResourceFile(int rType,String rUrl){
		String rName;
		switch(rType){
		
		case 1:
			openVideo(rUrl);break;
		case 2:
			rName=getResourceName(rUrl);
			DownloadFromUrl(PATH+rName,rUrl);
			openImage(rName);break;
		case 3:
			rName=getResourceName(rUrl);
			DownloadFromUrl(PATH+rName,rUrl);
			openPDF(rName);break;
		case 4:
			rName=getResourceName(rUrl);
			DownloadFromUrl(PATH+rName,rUrl);
			openDoc(rName);	break;
		case 5:
			rName=getResourceName(rUrl);
			DownloadFromUrl(PATH+rName,rUrl);
			openPTT(rName);break;
		case 6:
			rName=getResourceName(rUrl);
			DownloadFromUrl(PATH+rName,rUrl);
			openExcel(rName);break;
		case 7:
			openAudio(rUrl);
		}
		
		
	}
	 void openVideo(String rUrl){
	    	
	    	Uri uri = Uri.parse(rUrl);
	        Intent intent = new Intent(Intent.ACTION_VIEW); 
	        intent.setDataAndType(uri, "video/mp4");
	        startActivity(intent);
	        
	    }
	    void openAudio(String rUrl){
	    	
	    	Uri uri = Uri.parse(rUrl);
	        Intent intent = new Intent(Intent.ACTION_VIEW); 
	        intent.setDataAndType(uri, "audio/mp3");
	        startActivity(intent);
	        
	    }
	    void openExcel(String rName){
	    	
	    	Intent intent = new Intent();
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	intent.setAction(Intent.ACTION_VIEW);
	    	String type = "application/vnd.ms-excel";
	    	File file = new File(PATH+rName);
	    	intent.setDataAndType(Uri.fromFile(file), type);
	    	startActivity(intent);
	    }
	    void openPTT(String rName){
	    	Intent intent = new Intent();
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	intent.setAction(Intent.ACTION_VIEW);
	    	String type = "application/vnd.ms-powerpoint";
	    	File file = new File(PATH+rName);
	    	intent.setDataAndType(Uri.fromFile(file), type);
	    	startActivity(intent);
	    	
	    }
	    void openDoc(String rName){
	    	Intent intent = new Intent();
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	intent.setAction(Intent.ACTION_VIEW);
	    	String type = "application/msword";
	    	File file = new File(PATH+rName);
	    	intent.setDataAndType(Uri.fromFile(file), type);
	    	startActivity(intent);
	    }
	    void openImage(String rName){
	    	
	    	Intent intent = new Intent();  
	        intent.setAction(android.content.Intent.ACTION_VIEW);
	        File file1 = new File(PATH+rName);
	        intent.setDataAndType(Uri.fromFile(file1), "image/jpg");
	        startActivity(intent);

	    }
	    void openPDF(String rName){
	    	 File file = new File(PATH+rName);

	         if (file.exists()) {
	             Uri path = Uri.fromFile(file);
	             Intent intent = new Intent(Intent.ACTION_VIEW);
	             intent.setDataAndType(path, "application/pdf");
	             intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

	             try {
	                 startActivity(intent);
	             } 
	             catch (ActivityNotFoundException e) {
	                 Toast.makeText(getApplicationContext(), 
	                     "No Application Available to View PDF", 
	                     Toast.LENGTH_SHORT).show();
	             }
	         }
	    	
	    }
		 public Boolean DownloadFromUrl(String fileName, String resourceURL) {
		        try {
		                URL url = new URL(resourceURL); //you can write here any link
		                File file = new File(fileName);

		                long startTime = System.currentTimeMillis();
		                System.out.print("Starting download......from " + url);
		                URLConnection ucon = url.openConnection();
		                InputStream is = ucon.getInputStream();
		                BufferedInputStream bis = new BufferedInputStream(is);
		                /*
		                 * Read bytes to the Buffer until there is nothing more to read(-1).
		                 */
		                ByteArrayBuffer baf = new ByteArrayBuffer(50);
		                int current = 0;
		                while ((current = bis.read()) != -1) {
		                        baf.append((byte) current);
		                }

		                FileOutputStream fos = new FileOutputStream(file);
		                fos.write(baf.toByteArray());
		                fos.close();
		                System.out.print("Download Completed in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
		                
		        } catch (IOException e) {
		        	 System.out.print("Error: " + e);
		        	 return false;
		        }
		        return true;
		}
		 String getResourceName(String rUrl){
			 
			 String rName=null;
			 String arrSplit[]=rUrl.split("/");
			 rName=arrSplit[arrSplit.length-1];
			 
			 return rName;
		 }
		 public void setTitleFromActivityLabel (int textViewId)
			{
			    TextView tv = (TextView) findViewById (textViewId);
			    if (tv != null) tv.setText (getTitle ());
			}
		 public void onClickHome (View v)
			{
			    goHome (this);
			}
			public void goHome(Context context) 
			{
			    final Intent intent = new Intent(context, HomeActivity.class);
			    intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    context.startActivity (intent);
			}
			public void onClickAbout (View v)
			{
				startActivity (new Intent(getApplicationContext(), AboutActivity.class));
			}
			public void onClickSearch (View v)
			{
			    startActivity (new Intent(getApplicationContext(), Feature2Activity.class));
			}
}
