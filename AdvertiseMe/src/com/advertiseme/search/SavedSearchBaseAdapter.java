package com.advertiseme.search;

import java.util.ArrayList;

import com.advertiseme.support.ListItemDetails;
import com.advertiseme.startpanel.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class SavedSearchBaseAdapter extends BaseAdapter {

	private static ArrayList<ListItemDetails> itemDetailsrrayList;
	
	
	private LayoutInflater l_Inflater;

	public SavedSearchBaseAdapter(Context context, ArrayList<ListItemDetails> results) {
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
			convertView = l_Inflater.inflate(R.layout.row_format_savedsearch, null);
			holder = new ViewHolder();
			holder.txt_Header = (TextView) convertView.findViewById(R.id.text1);
			holder.chkEnable = (CheckBox) convertView.findViewById(R.id.checkBox1);
			holder.imgNew = (ImageView) convertView.findViewById(R.id.imgNew);
					
			convertView.setTag(holder); 
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_Header.setText(itemDetailsrrayList.get(position)
				.getTextHeader());		
		holder.chkEnable.setChecked(itemDetailsrrayList.get(position).getConditionStatus());
		
		int imgOption=itemDetailsrrayList.get(position).getConditionOption();
		if(imgOption==0){
			holder.imgNew.setVisibility(android.view.View.INVISIBLE);
		}
		
		return convertView;
	}

	static class ViewHolder {
		TextView txt_Header;
		CheckBox chkEnable;
		ImageView imgNew;		
	}
}
