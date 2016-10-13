package com.heima.mobilesafe.view;

import com.heima.mobilesafe.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout{
	private LayoutInflater mInflater;
	private TextView mTitle;
	private ImageView mTvToggle;
	private boolean mIsToggle=true;
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mInflater=LayoutInflater.from(context);
		mInflater.inflate(R.layout.item_setting, this);
		
		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
		String text = attributes.getString(R.styleable.SettingItemView_sivText);
		int backBoo = attributes.getInt(R.styleable.SettingItemView_backBoo, 0);
		attributes.recycle();
		
		mTitle = (TextView) findViewById(R.id.tv_setting_title);
		mTvToggle = (ImageView) findViewById(R.id.iv_toggle);
		mTitle.setText(text);
		switch (backBoo) {
		case 0:
			setBackgroundResource(R.drawable.first_selector);
			break;
		case 1:
			setBackgroundResource(R.drawable.middle_selector);
			break;
		case 2:
			setBackgroundResource(R.drawable.last_selector);
			break;

		default:
			break;
		}
		
	}
	/**
	 * 设置当前开关状态,
	 * @param isToggle 判断状态，设置图片背景 
	 */
	public void setToggle(boolean isToggle) {
		mIsToggle=isToggle;
		if (isToggle) {
			mTvToggle.setImageResource(R.drawable.on);
		}else {
			mTvToggle.setImageResource(R.drawable.off);
		}
	}
	/**
	 * 用于返回当前开关的状态
	 * @return true on,false off
	 */
	public boolean getIsToggle() {
		return mIsToggle;
	}

}
