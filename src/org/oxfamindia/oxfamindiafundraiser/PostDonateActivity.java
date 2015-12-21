package org.oxfamindia.oxfamindiafundraiser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class PostDonateActivity extends Activity implements OnClickListener{
	private CardView fb_share;
	private CardView twit_share;
	private CardView gplus_share;
	private CardView others_share;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_donate);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		fb_share = (CardView) findViewById(R.id.fb_share);
		twit_share = (CardView) findViewById(R.id.twit_share);
		gplus_share = (CardView) findViewById(R.id.gplus_share);
		others_share = (CardView) findViewById(R.id.others_share);
		fb_share.setOnClickListener(this);
		twit_share.setOnClickListener(this);
		gplus_share.setOnClickListener(this);
		others_share.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.fb_share)
		{
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/sharer/sharer.php?u=http%3A%2F%2Fwww.oxfamindia.org"));
			startActivity(browserIntent);
			finish();
		}
		else if(id == R.id.twit_share)
		{
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/tweet?text=http%3A%2F%2Fwww.oxfamindia.org"));
		    startActivity(browserIntent);
		    finish();
		}
		else if(id == R.id.gplus_share)
		{
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/share?url=http://www.oxfamindia.org"));
		    startActivity(browserIntent);
		    finish();
		}
		else if(id == R.id.others_share)
		{
			Intent shareint = new Intent(Intent.ACTION_SEND);
			shareint.setType("text/plain");
			shareint.putExtra(Intent.EXTRA_TEXT, "Share this cause. Here is the link - http://www.oxfamindia.org");
			startActivity(shareint);
			finish();
		}
	}
}
