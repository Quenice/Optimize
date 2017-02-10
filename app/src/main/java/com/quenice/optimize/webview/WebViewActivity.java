package com.quenice.optimize.webview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.quenice.optimize.Application;
import com.quenice.optimize.R;

/**
 * Created by qiubb on 2017/2/7.
 */

public class WebViewActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);

		WebView mWebView = (WebView)findViewById(R.id.webview);
		// 设置缓存
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
		mWebView.getSettings().setJavaScriptEnabled(true);
		// 设置定位
		mWebView.getSettings().setAppCacheEnabled(true);
		mWebView.getSettings().setDatabaseEnabled(true);
		mWebView.getSettings().setGeolocationEnabled(true);
		mWebView.getSettings().setGeolocationDatabasePath(Application.getInstance().getDir("database", Context.MODE_PRIVATE).getPath());
		mWebView.getSettings().setDomStorageEnabled(true);
		//支付宝
		mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
		mWebView.getSettings().setSupportMultipleWindows(true);
		mWebView.getSettings().setSavePassword(false);
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		mWebView.getSettings().setAllowFileAccess(true);
		mWebView.getSettings().setTextSize(WebSettings.TextSize.NORMAL);

//		mWebView.loadUrl("http://192.168.1.22:8020/html5_insu/upload_certificates.html");
		mWebView.loadUrl("http://123.57.0.44/lbt_xzm_wx/index.jsp");
	}
}
