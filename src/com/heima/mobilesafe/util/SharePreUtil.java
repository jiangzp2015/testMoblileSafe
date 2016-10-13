package com.heima.mobilesafe.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreUtil {
	
	private static SharedPreferences getPre(Context  context){
		SharedPreferences sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		return sharedPreferences;
	}
	public static void putBoolean(Context context,String key,boolean value) {
		getPre(context).edit().putBoolean(key, value).commit();
	}
	public static boolean getBoolean(Context context,String key,boolean defValue) {
		boolean result = getPre(context).getBoolean(key, defValue);
		return result;
	}
	public static boolean getBoolean(Context context,String key) {
		boolean result = getBoolean(context, key, false);
		return result;
	}
	
	//=============================================
	
	public static void putString(Context context,String key,String value) {
		getPre(context).edit().putString(key, value).commit();
	}
	public static String getString(Context context,String key,String defValue) {
		String result = getPre(context).getString(key, defValue);
		return result;
	}
	public static String getString(Context context,String key) {
		String result =getString(context, key, "");
		return result;
	}
	
	//===========================================

	public static void putInt(Context context,String key,int value) {
		getPre(context).edit().putInt(key, value).commit();
	}
	public static int getInt(Context context,String key,int defValue) {
		int result = getPre(context).getInt(key, defValue);
		return result;
	}
	public static int getInt(Context context,String key) {
		int result = getInt(context, key, Integer.parseInt(""));
		return result;
	}
}
