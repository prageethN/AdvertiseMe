package com.advertiseme.myadvertiseme;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.advertiseme.support.ListItemDetails;
import com.advertiseme.startpanel.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class OfferBaseAdapter extends BaseAdapter {

	private static ArrayList<ListItemDetails> itemDetailsrrayList;

	private LayoutInflater l_Inflater;
	
	private int arrGreenRGB[]={34,139,34};
	private int arrRedRGB[]={255,0,0};
	private int arrOrangeRGB[]={255,165,0};
	
	public OfferBaseAdapter(Context context, ArrayList<ListItemDetails> results) {
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
			convertView = l_Inflater.inflate(R.layout.row_format_offer, null);
			holder = new ViewHolder();
			holder.txt_Header = (TextView) convertView.findViewById(R.id.text1);
			holder.txt_Normal = (TextView) convertView.findViewById(R.id.text2);
			holder.txt_Footer = (TextView) convertView.findViewById(R.id.text3);
			holder.txt_Status=(TextView) convertView.findViewById(R.id.text4);
			holder.txt_MyOffer=(TextView) convertView.findViewById(R.id.text5);
			holder.txt_SellerOffer=(TextView) convertView.findViewById(R.id.text6);
			holder.itemImageItem=(ImageView)convertView.findViewById(R.id.list_image);
			
			convertView.setTag(holder); 
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txt_Header.setText(itemDetailsrrayList.get(position)
				.getTextHeader());
		holder.txt_Normal.setText(itemDetailsrrayList.get(position)
				.getTextNormal());
		holder.txt_Footer.setText(itemDetailsrrayList.get(position)
				.getTextFooter());
		holder.txt_MyOffer.setText("My Offer: "+itemDetailsrrayList.get(position)
				.getTextExtra());
		
		int conditionFlg=itemDetailsrrayList.get(position).getConditionOption();
		switch(conditionFlg){
		
		case 0:
			holder.txt_Status.setText("Pending");
			holder.txt_Status.setTextColor(Color.rgb(arrRedRGB[0], arrRedRGB[1], arrRedRGB[2]));
			holder.txt_SellerOffer.setText("");
			break;
		case 1:
			holder.txt_Status.setText("Negotiate");
			holder.txt_Status.setTextColor(Color.rgb(arrOrangeRGB[0], arrOrangeRGB[1], arrOrangeRGB[2]));
			holder.txt_SellerOffer.setText("Seller Offer: "+itemDetailsrrayList.get(position)
					.getTextAdditional());
			break;
		case 2:
			holder.txt_Status.setText("Agreed");	
			holder.txt_Status.setTextColor(Color.rgb(arrGreenRGB[0], arrGreenRGB[1], arrGreenRGB[2]));
			holder.txt_SellerOffer.setText("Seller Offer: "+itemDetailsrrayList.get(position)
					.getTextAdditional());
		}
		
		String imgUrl=itemDetailsrrayList.get(position)
		.getPreviewURL();
		Bitmap bimage=  getBitmapFromURL(imgUrl);
		holder.itemImageItem.setImageBitmap(bimage);
		
		return convertView;
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
	static class ViewHolder {
		TextView txt_Header;
		TextView txt_Normal;
		TextView txt_Footer;
		TextView txt_Status;
		
		TextView txt_MyOffer;
		TextView txt_SellerOffer;
		
		ImageView itemImageItem;
		ImageView itemImageRating;
	}
}
