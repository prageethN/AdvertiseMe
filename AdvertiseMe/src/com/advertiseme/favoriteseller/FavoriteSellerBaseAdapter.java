package com.advertiseme.favoriteseller;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FavoriteSellerBaseAdapter extends BaseAdapter {
	
	private static ArrayList<ListItemDetails> itemDetailsrrayList;

	private Integer[] imgid = { R.drawable.icn_yellow_star
			 };

	private LayoutInflater l_Inflater;

	public FavoriteSellerBaseAdapter(Context context,
			ArrayList<ListItemDetails> results) {
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
			convertView = l_Inflater.inflate(R.layout.row_format_favseller, null);
			holder = new ViewHolder();
			holder.txt_Header = (TextView) convertView.findViewById(R.id.text1);
			holder.txt_Normal = (TextView) convertView.findViewById(R.id.text2);
			holder.itemImageRating = (ImageView) convertView
					.findViewById(R.id.imgRating);
			holder.itemImageSeller= (ImageView) convertView.findViewById(R.id.list_image);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txt_Header.setText(itemDetailsrrayList.get(position)
				.getTextHeader());
		holder.txt_Normal.setText(itemDetailsrrayList.get(position)
				.getTextNormal());		
		String imgUrl=itemDetailsrrayList.get(position).getPreviewURL();
		Bitmap bimage=  getBitmapFromURL(imgUrl);		
		holder.itemImageSeller.setImageBitmap(bimage);
		
		setSellerStar(	holder.itemImageRating,itemDetailsrrayList.get(position)
				.getTextExtra());
	
		return convertView;
	}

	static class ViewHolder {
		TextView txt_Header;
		TextView txt_Normal;
		ImageView itemImageSeller;
		ImageView itemImageRating;
	}
	void setSellerStar(ImageView imgStar,String sumRating) {

		int sumRatingCount = Integer.parseInt(sumRating);
		
		if (0 < sumRatingCount && sumRatingCount < 49) {
			imgStar.setImageResource(R.drawable.icn_yellow_star);
		}
		if (50 < sumRatingCount && sumRatingCount < 99) {
			imgStar.setImageResource(R.drawable.icn_blue_star);
		}
		if (100 < sumRatingCount && sumRatingCount < 499) {
			imgStar.setImageResource(R.drawable.icn_skyblue_star);
		}
		if (500 < sumRatingCount && sumRatingCount < 999) {
			imgStar.setImageResource(R.drawable.icn_purple_star);
		}
		if (1000 < sumRatingCount && sumRatingCount < 4999) {
			imgStar.setImageResource(R.drawable.icn_red_star);
		}
		if (5000< sumRatingCount && sumRatingCount < 9999) {
			imgStar.setImageResource(R.drawable.icn_green_star);
		}

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
}
