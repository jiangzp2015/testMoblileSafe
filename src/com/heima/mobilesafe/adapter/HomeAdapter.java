package com.heima.mobilesafe.adapter;

import java.util.ArrayList;
import java.util.List;

import com.heima.mobilesafe.R;
import com.heima.mobilesafe.entity.HomeEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<HomeEntity> mEntities;
	public HomeAdapter(Context context,List<HomeEntity> homeEntities) {
		mContext=context;
		mInflater=LayoutInflater.from(mContext);
		mEntities=homeEntities;
		homeEntities=new ArrayList<HomeEntity>();
	}
	@Override
	public int getCount() {
		return mEntities.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			convertView =mInflater.inflate(R.layout.item_home, null);}
			TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			TextView tvDesc = (TextView) convertView.findViewById(R.id.tv_desc);
			ImageView ivIcon=(ImageView) convertView.findViewById(R.id.iv_icon);
		
			HomeEntity homeEntity = mEntities.get(position);
			
			tvTitle.setText(homeEntity.title);
			tvDesc.setText(homeEntity.desc);
			ivIcon.setImageResource(homeEntity.icon);
			
			return convertView;
	}

	@Override
	public HomeEntity getItem(int position) {
		return mEntities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


}
