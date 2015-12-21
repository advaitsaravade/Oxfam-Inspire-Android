package org.oxfamindia.oxfamindiafundraiser;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DonatePortalActivity extends ActionBarActivity {

	private WebView myWebView;
	private ActionBar actionbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donate_portal);
		actionbar = getSupportActionBar();
		myWebView = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true); 
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            	// TODO Auto-generated method stub
            	super.onPageStarted(view, url, favicon);
            	actionbar.setTitle("Loading");
            }
            @Override
            public void onPageFinished(WebView view, String url) {
            	// TODO Auto-generated method stub
            	super.onPageFinished(view, url);
            	actionbar.setTitle("Donate Portal");
            }
        });
        myWebView.loadUrl("http://www.oxfamindia.org");
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    // Check if the key event was the Back button and if there's history
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
	        myWebView.goBack();
	        return true;
	    }
	    // If it wasn't the Back key or there's no web page history, bubble up to the default
	    // system behavior (probably exit the activity)
	    return super.onKeyDown(keyCode, event);
	}
}
