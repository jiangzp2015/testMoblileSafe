package com.heima.mobilesafe.activity;

import com.heima.mobilesafe.R;
import com.heima.mobilesafe.util.ContasPre;
import com.heima.mobilesafe.util.SharePreUtil;
import com.heima.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity implements OnClickListener{
	private SettingItemView mSivAutoUpdate,mIntercept,mSivLocationShow,mSivLocationStyle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		initView();
		initData();
		initListener();
	}

	private void initView() {
		mSivAutoUpdate = (SettingItemView) findViewById(R.id.siv_setting_update);
//		mIntercept = (SettingItemView) findViewById(R.id.siv_setting_black_service);
//		mSivLocationShow = (SettingItemView) findViewById(R.id.siv_setting_location_show);
//		mSivLocationStyle = (SettingItemView) findViewById(R.id.siv_setting_location_style);
		
	}
	private void initData() {
		mSivAutoUpdate.setToggle(SharePreUtil.getBoolean(this, ContasPre.AUTO_UPDATE, true));
//		mIntercept.setToggle(SharePreUtil.getBoolean(this, ContasPre.AUTO_UPDATE, true));
	}
	private void initListener() {
		mSivAutoUpdate.setOnClickListener(this);
//		mIntercept.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
			if (mSivAutoUpdate.getIsToggle()) {
				mSivAutoUpdate.setToggle(false);
				SharePreUtil.putBoolean(SettingActivity.this, ContasPre.AUTO_UPDATE, false);
			}else {
				mSivAutoUpdate.setToggle(true);
				SharePreUtil.putBoolean(SettingActivity.this, ContasPre.AUTO_UPDATE, true);
			}

		}
	

}
