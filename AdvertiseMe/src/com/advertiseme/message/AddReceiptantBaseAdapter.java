package com.advertiseme.message;

import java.util.ArrayList;

import com.advertiseme.support.ListItemDetails;
import com.advertiseme.startpanel.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class AddReceiptantBaseAdapter extends BaseAdapter {
	
	private static ArrayList<ListItemDetails> itemDetailsrrayList;


	private LayoutInflater l_Inflater;

	public AddReceiptantBaseAdapter(Context context,
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
			convertView = l_Inflater.inflate(R.layout.row_format_add_people, null);
			holder = new ViewHolder();
			holder.txt_Header = (TextView) convertView.findViewById(R.id.text1);
			holder.txt_Normal = (TextView) convertView.findViewById(R.id.text2);
			holder.chkSelect=(CheckBox) convertView.findViewById(R.id.checkBox1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txt_Header.setText(itemDetailsrrayList.get(position)
				.getTextHeader());
		holder.txt_Normal.setText(itemDetailsrrayList.get(position)
				.getTextNormal());
				
		return convertView;
	}

	static class ViewHolder {
		TextView txt_Header;
		TextView txt_Normal;
		CheckBox chkSelect;
		ImageView itemImageSeller;
		ImageView itemImageRating;
	}
}
