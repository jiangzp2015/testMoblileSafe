package com.heima.mobilesafe.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
/**
 * 对连续多次弹Toast的优化
 * @author Jzp
 *
 */
public class ToastUtil {
	private static Toast mToast;
	private static Handler mHandler=new Handler();
	private static Runnable runnable=new Runnable() {
		@Override
		public void run() {
			mToast.cancel();
		}
	};
	public static void showToast(Context context,String msg) {
		mHandler.removeCallbacks(runnable);
		if (mToast!=null) {
			mToast.setText(msg);
		}else {
			mToast=Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		}
		mHandler.postDelayed(runnable, 5000);
		mToast.show();
	}
	public static void showToast(Context context,int strId) {
		showToast(context, context.getString(strId));
	}
}
