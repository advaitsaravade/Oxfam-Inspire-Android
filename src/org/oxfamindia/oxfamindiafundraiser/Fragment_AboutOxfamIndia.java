package org.oxfamindia.oxfamindiafundraiser;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Fragment_AboutOxfamIndia extends Fragment {
	
	private WebView myWebView;
	private ActionBar actionbar;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_aboutus, container, false);
		actionbar = ((ActionBarActivity)getActivity()).getSupportActionBar();
		myWebView = (WebView) rootView.findViewById(R.id.webview);
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
            	actionbar.setTitle("About Us");
            }
        });
        myWebView.loadUrl("http://oxfamindia.org/who-we-are");
		
        return rootView;
	}
}