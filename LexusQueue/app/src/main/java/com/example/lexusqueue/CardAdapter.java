package com.example.lexusqueue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CardAdapter extends BaseAdapter {
	private ArrayList<String> data;
	private LayoutInflater inflater;

	public CardAdapter(ArrayList<String> data, Context context){
		this.data = data;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int i) {
		return data.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (vi == null)
			vi = inflater.inflate(R.layout.card_button, null);

		TextView text = (TextView) vi.findViewById(R.id.cardText);
		text.setText(data.get(position));

		vi.setTag(position);
		return vi;
	}
}
