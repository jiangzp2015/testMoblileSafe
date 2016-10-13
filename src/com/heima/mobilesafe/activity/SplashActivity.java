package com.heima.mobilesafe.activity;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import com.heima.mobilesafe.R;
import com.heima.mobilesafe.util.ContasPre;
import com.heima.mobilesafe.util.PackageUtils;
import com.heima.mobilesafe.util.SharePreUtil;
import com.heima.mobilesafe.util.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 *  * 闪屏页面
 * 
 * - 展示logo, 公司品牌 - 检查版本更新 - 项目初始化 - 校验合法性(检查是否有网络, 检查是否登录) 开发流程:
 *  1. 布局文件 
 *  2.获取版本名,显示给TextView 
 *  3. 访问服务器,获取json数据 
 *  4. 解析json, 判断是否有更新 
 *  5. 有更新,弹窗提示 
 *  6.无更新,跳主页面 
 *  7. 网络异常等情况,也跳主页面
 *  8. 闪屏页显示2秒逻辑
 *  9. 打包2.0版本 
 *  10. 使用xutils下载apk 11.
 * 	11. 闪屏页渐变动画 
 * @author Jzp
 *
 */
public class SplashActivity extends Activity {

	protected static final String TAG = "SplashActivity";
	private TextView mVersion;
	private RelativeLayout mLayout;
	private String mServiceDes;
	private String mServiceUrl;
	private int mServiceVersionCode;
	private File mApkPath;
	private boolean isCancelInstall;
	private Handler mHandler=new Handler();
	String error="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		initView();
		showAnimation();
		initData();
		
	}
	/**
	 * 初始化数据
	 */
	private void initData() {
		initVersionName();
		checkUpdate();
		
	}
	/**
	 * 初始化版本信息
	 */
	private void initVersionName() {
		String versionName = PackageUtils.getVersionName(this);
		mVersion.setText(versionName);
	}
	/**
	 * 检查更新
	 */
	private void checkUpdate() {
		boolean isAutoUpdate = SharePreUtil.getBoolean(SplashActivity.this, ContasPre.AUTO_UPDATE, true);
		if (!isAutoUpdate) {
			load2Main();
			return;
		}
		String url="http://10.0.2.2:8080/update32.json";
		HttpUtils http=new HttpUtils(5000);
		http.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				ToastUtil.showToast(SplashActivity.this, "请求失败：超时");
				load2Main();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {

				//json解析
				String result=arg0.result;
				try {
					JSONObject jsonObject=new JSONObject(result);
					mServiceVersionCode = jsonObject.getInt("versionCode");
					mServiceDes = jsonObject.getString("des");
					mServiceUrl = jsonObject.getString("url");
					
					int locationVersionCode = PackageUtils.getVersionCode(SplashActivity.this);
					if (mServiceVersionCode>locationVersionCode) {
						mHandler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								showUpdateDialog();
							}
						}, 3000);
						
					}else {
						error="100";
						ToastUtil.showToast(SplashActivity.this, "dddd");
						load2Main();
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
					error="102";
					load2Main();
				}
			}
		});
		
	}
	/**
	 * 更新弹框
	 */
	protected void showUpdateDialog() {
		AlertDialog.Builder builder=new AlertDialog.Builder(SplashActivity.this);
		builder.setTitle("更新提示");
		builder.setMessage(mServiceDes);
		builder.setPositiveButton("立刻升级",new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				performDownloadApk();
			}
		});
		builder.setNegativeButton("稍后再说", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				load2Main();
			}
		});
		
	builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				load2Main();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		
	}
	/**
	 * 下載APK
	 */
	protected void performDownloadApk() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_UNMOUNTED)) {
			ToastUtil.showToast(SplashActivity.this, "SD卡未安裝");
			return;
		}
		mApkPath = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".apk");
		//載入進度條
		final ProgressDialog dialog=new ProgressDialog(SplashActivity.this);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.show();
		//利用Xutils下載
		HttpUtils httpUtils=new HttpUtils(3000);
		httpUtils.download(mServiceUrl, mApkPath.getAbsolutePath(), new RequestCallBack<File>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				dialog.dismiss();
				load2Main();
			}

			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				
				dialog.dismiss();
				enterInstall();
				
			}
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				super.onLoading(total, current, isUploading);
				dialog.setMax((int) total);
				dialog.setProgress((int) current);
			}
		});
	}
	/**
	 * 安裝界面
	 */
/*  <intent-filter>
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <data android:scheme="content" />
    <data android:scheme="file" />
    <data android:mimeType="application/vnd.android.package-archive" />
	</intent-filter>*/
	protected void enterInstall() {
		Intent intent=new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(mApkPath), 
				"application/vnd.android.package-archive");
		startActivity(intent);
	}
	/**
	 * 初始化View
	 */
	private void initView() {
		mLayout = (RelativeLayout) findViewById(R.id.rl_splash);
		mVersion = (TextView) findViewById(R.id.tv_version);
		
	}
	/**
	 * Show动画
	 */
	private void showAnimation() {
		ObjectAnimator alphaAnimator=ObjectAnimator.ofFloat(mLayout, "alpha", 0f,1f);
		ObjectAnimator scaleXAnimator=ObjectAnimator.ofFloat(mLayout, "scaleX", 0f,1f);
		ObjectAnimator scaleYAnimator=ObjectAnimator.ofFloat(mLayout, "scaleY", 0f,1f);
		AnimatorSet set=new AnimatorSet();
		set.playTogether(alphaAnimator,scaleXAnimator,scaleYAnimator);
		set.setDuration(2000);
		set.start();
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		if (isCancelInstall) {
			ToastUtil.showToast(SplashActivity.this, "取消安裝");
			load2Main();
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		isCancelInstall=true;
	}
	/**
	 * 進入HomeActivity
	 */
	private void load2Main() {
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if (!TextUtils.isEmpty(error)) {
					ToastUtil.showToast(SplashActivity.this, error);
				}
				startActivity(new Intent(SplashActivity.this,HomeActivity.class));
				finish();
			}
		}, 1500);
	}
}
