package com.advertiseme.advertisement;

import java.util.ArrayList;

import com.advertiseme.support.ListItemDetails;
import com.advertiseme.startpanel.R;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class ReviewBaseAdapter extends BaseAdapter {

	private static ArrayList<ListItemDetails> itemDetailsrrayList;

	private LayoutInflater l_Inflater;
	ViewHolder holder;
	int flgExpand=0,currentPosition=0;
	public ReviewBaseAdapter(Context context, ArrayList<ListItemDetails> results) {
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
		
		if (convertView == null) {
			convertView = l_Inflater.inflate(R.layout.row_format_review, null);
			holder = new ViewHolder();
			holder.txt_Header = (TextView) convertView.findViewById(R.id.text1);
			holder.txt_Normal1 = (TextView) convertView.findViewById(R.id.text2);
			holder.txt_Normal2 = (TextView) convertView.findViewById(R.id.text4);
			holder.txt_Footer = (TextView) convertView.findViewById(R.id.text3);	
			holder.rvRating=(RatingBar)convertView.findViewById(R.id.ratingbar);
			holder.vwExpand=convertView.findViewById(R.id.viewExpand);
			convertView.setTag(holder); 
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txt_Header.setText(itemDetailsrrayList.get(position)
				.getTextHeader());
		holder.txt_Normal1.setText(itemDetailsrrayList.get(position)
				.getTextNormal());
		holder.txt_Normal2.setText(itemDetailsrrayList.get(position)
				.getTextNormal());	
		holder.txt_Footer.setText(itemDetailsrrayList.get(position)
				.getTextFooter());		
		holder.rvRating.setRating(itemDetailsrrayList.get(position)
				.getItemRating());
				
		itemDetailsrrayList.get(position).setImageNumber(position);
				
		return convertView;
	}
	
	static class ViewHolder {
		TextView txt_Header;
		TextView txt_Normal1;
		TextView txt_Normal2;
		TextView txt_Footer;
		RatingBar rvRating;	
		View vwExpand;
	}

}
