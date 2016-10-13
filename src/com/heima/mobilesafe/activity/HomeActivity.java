package com.heima.mobilesafe.activity;

import java.util.ArrayList;
import java.util.List;

import com.heima.mobilesafe.R;
import com.heima.mobilesafe.adapter.HomeAdapter;
import com.heima.mobilesafe.entity.HomeEntity;
import com.heima.mobilesafe.util.ContasPre;
import com.heima.mobilesafe.util.SharePreUtil;
import com.heima.mobilesafe.util.ToastUtil;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * HomeActivity界面
 * @author Jzp
 *
 */
public class HomeActivity extends Activity implements OnClickListener, OnItemClickListener {
	private ImageView mIvLogo;
	private ImageView mIvSetting;
	private List<HomeEntity> mHomeEntities;
	private String[] mTitle={"手机防盗","骚扰拦截","软件管家","进程管理",
							"流量统计","手机杀毒","缓存清理","常用工具"};
	private String[] mDesc={"远程定位手机", "全面拦截骚扰","管理您的软件", "管理运行进程",
			"流量一目了然", "病毒无处藏身", "系统快如火箭", "工具大全" };
	private int[] mImages={R.drawable.sjfd,R.drawable.srlj,R.drawable.rjgj,R.drawable.jcgl,
							R.drawable.lltj,R.drawable.sjsd,R.drawable.hcql,R.drawable.cygj};
	private GridView mGvHome;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
		initData();
		initListener();
	}
	
	private void initListener() {
		mIvSetting.setOnClickListener(this);
		mGvHome.setOnItemClickListener(this);
	}

	private void initData() {
		showAnimation();
		/**
		 * 将数据与实体绑定，并且放到list中
		 */
			mHomeEntities=new ArrayList<HomeEntity>();
			for (int i = 0; i < mDesc.length; i++) {
			HomeEntity homeEntity=new HomeEntity();
			homeEntity.title=mTitle[i];
			homeEntity.desc=mDesc[i];
			homeEntity.icon=mImages[i];
			mHomeEntities.add(homeEntity);
			
		}
		//设置Adapter
		initAdapter();
	}

	private void initAdapter() {
		HomeAdapter mAdapter=new HomeAdapter(this, mHomeEntities); 
		mGvHome.setAdapter(mAdapter);
	}

	private void initView() {
		mIvLogo = (ImageView) findViewById(R.id.iv_logo);
		mIvSetting = (ImageView) findViewById(R.id.iv_setting);
		mGvHome = (GridView) findViewById(R.id.gv_home);
	}
	/**
	 * 展示Logo界面
	 */
	private void showAnimation() {
		ObjectAnimator animator =ObjectAnimator.ofFloat(mIvLogo, "rotationY", 0f,180f,270f,360f);
		animator.setDuration(2000);
		animator.setRepeatCount(ObjectAnimator.INFINITE);
		animator.setRepeatMode(ObjectAnimator.REVERSE);
		animator.start();
	}
	
	@Override
	public void onClick(View v) {
//		ToastUtil.showToast(HomeActivity.this, "Touch Setting");
		startActivity(new Intent(HomeActivity.this,SettingActivity.class));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			showPwdDialog();
			break;

		default:
			break;
		}
	}
	/**
	 * 设置密码弹框
	 */
	private void showPwdDialog() {
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);
		View dialogView = View.inflate(HomeActivity.this, R.layout.view_dialog_pwd, null);
		dialog.setView(dialogView);
		
		final TextView tvPwd = (TextView) dialogView.findViewById(R.id.et_pwd);
		final TextView tvPwdConfirm = (TextView) dialogView.findViewById(R.id.et_pwd_confirm);
		//确定按钮的事件
		dialogView.findViewById(R.id.btn_confirm).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String pwd = tvPwd.getText().toString().trim();
				String pwdConfirm = tvPwdConfirm.getText().toString().trim();
				if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdConfirm)) {
					ToastUtil.showToast(HomeActivity.this, "您输入的密码不能为空");
					return;
				}
				if (!pwd.equals(pwdConfirm)) {
					ToastUtil.showToast(HomeActivity.this, "您输入的密码两次不一致");
					return;
				}
				SharePreUtil.putString(HomeActivity.this, ContasPre.SJWS_PWD, pwdConfirm);
				dialog.dismiss();
				//TODO  enter 手机防盗导航界面
				ToastUtil.showToast(getApplicationContext(), "设置成功");
			}
		});
		//取消按钮的事件
		dialogView.findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				ToastUtil.showToast(HomeActivity.this, "取消成功");
			}
		});
		dialog.show();
		
	}
}
