package com.heima.mobilesafe.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarqueeTextView extends TextView {
	//在布局里面使用这个控件的时候会调用  
	public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	//在布局里面使用这个控件的时候会调用
	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setEllipsize(TruncateAt.MARQUEE);
		setMarqueeRepeatLimit(-1);
		setFocusableInTouchMode(true);
		setFocusable(true);
		setSingleLine();
		
	}
	////在代码里面创建这个控件的时候才会使用。
	public MarqueeTextView(Context context) {
		super(context);
	}
	//View的欺骗
	@Override
	public boolean isFocused() {
		return true;
	}
	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		super.onFocusChanged(true, direction, previouslyFocusedRect);
	}
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(true);
	}
	

}
