package com.advertiseme.resource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.apache.http.util.ByteArrayBuffer;

import com.advertiseme.startpanel.R;
import com.advertiseme.support.ListItemDetails;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ResourceBaseAdapter extends BaseAdapter {

	private static ArrayList<ListItemDetails> itemDetailsrrayList;

	private LayoutInflater l_Inflater;

	public ResourceBaseAdapter(Context context, ArrayList<ListItemDetails> results) {
		itemDetailsrrayList = results;
		l_Inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return itemDetailsrrayList.size();
	}

	public Object getItem(int position) {
		return itemDetailsrrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = l_Inflater.inflate(R.layout.row_format_resource, null);
			holder = new ViewHolder();
			holder.txt_Header = (TextView) convertView.findViewById(R.id.text1);
			holder.txt_Normal = (TextView) convertView.findViewById(R.id.text2);
			holder.itemImageItem=(ImageView)convertView.findViewById(R.id.imgPreview);

			convertView.setTag(holder); 
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txt_Header.setText(itemDetailsrrayList.get(position)
				.getTextHeader());
		holder.txt_Normal.setText(getResourceType(Integer.parseInt(itemDetailsrrayList.get(position)
				.getTextNormal())));
		
		String imgUrl=itemDetailsrrayList.get(position)
		.getTextFooter();
		Bitmap bimage=  getBitmapFromURL(imgUrl);
		holder.itemImageItem.setImageBitmap(bimage);
		
		return convertView;
	}

	static class ViewHolder {
		TextView txt_Header;
		TextView txt_Normal;		
		ImageView itemImageItem;
		
	}
	
	 public static Bitmap getBitmapFromURL(String src) {
	        try {
	            Log.e("src",src);
	            URL url = new URL(src);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setDoInput(true);
	            connection.connect();
	            InputStream input = connection.getInputStream();
	            Bitmap myBitmap = BitmapFactory.decodeStream(input);
	            Log.e("Bitmap","returned");
	            return myBitmap;
	        } catch (IOException e) {
	            e.printStackTrace();
	            Log.e("Exception",e.getMessage());
	            return null;
	        }
	    }
	 
	 public String getResourceType(int typeId){
		 
		 String rType=null;
		 switch(typeId){
		 
		 case 1:
			 rType="Video";break;
		 case 2:
			 rType="Image";break;
		 case 3:
			 rType="PDF";break;
		 case 4:
			 rType="MS-Word";break;
		 case 5:
			 rType="MS-Powerpoint";break;
		 case 6:
			 rType="MS-Excel";break;
		 case 7:
			 rType="Audio";break;
		 default: 
			 rType="Other";
		 }
		 
		 return rType;
	 }

}
