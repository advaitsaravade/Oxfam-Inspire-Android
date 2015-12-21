package org.oxfamindia.oxfamindiafundraiser;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class IndividualProjectsActivity extends ActionBarActivity implements OnClickListener {

	private ImageView hero_image;
	private TextView proj_title;
	private TextView proj_categ;
	private CardView donate_but;
	private TextView proj_brief;
	private TextView proj_funfact_info;
	private String bundle_url;
	private boolean donatedAlreadyBro = false;
	private String titlebar_title;
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Check if we're running on Android 5.0 or higher
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
		    // Call some material design APIs here
			Window window = this.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(this.getResources().getColor(R.color.blacktrans));
		} else {
		    // Implement this feature without material design
		}
		setContentView(R.layout.activity_individual_projects);
		Bundle extras = getIntent().getExtras(); 
		if (extras != null) {
		    bundle_url = extras.getString("url");
		    donatedAlreadyBro = extras.getBoolean("donateAlreadyBro");
		    titlebar_title = extras.getString("origin_activity");
		}
		if(donatedAlreadyBro)
		{
			postDonate();
		}
		ActionBar actionbar = getSupportActionBar();
		actionbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.black_trans));
		actionbar.setTitle(titlebar_title);
		hero_image = (ImageView) findViewById(R.id.hero_image);
		proj_title = (TextView) findViewById(R.id.proj_title);
		proj_categ = (TextView) findViewById(R.id.proj_categ);
		donate_but = (CardView) findViewById(R.id.donate_button);
		proj_brief = (TextView) findViewById(R.id.proj_brief);
		proj_funfact_info = (TextView) findViewById(R.id.proj_funfact_info);
		proj_title.setText("Rural Classrooms");
		proj_categ.setText("EDUCATION");
		proj_brief.setText("Billions upon billions, hearts of the stars science! Orion's sword. Circumnavigated, as a patch of light corpus callosum, rich in heavy atoms hydrogen atoms cosmic ocean extraordinary claims require extraordinary evidence venture. Laws of physics cosmic fugue intelligent beings? Ship of the imagination, citizens of distant epochs Sea of Tranquility? Prime number!");
		proj_funfact_info.setText("Dream of the mind's eye, brain is the seed of intelligence tendrils of gossamer clouds Apollonius of Perga science, billions upon billions! Are creatures of the cosmos corpus callosum.");
		donate_but.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.share_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.share) {
			doShare();
		}
		return super.onOptionsItemSelected(item);
	}
	private void doShare() {
		Intent shareint = new Intent(Intent.ACTION_SEND);
		shareint.setType("text/plain");
		shareint.putExtra(Intent.EXTRA_TEXT, "Share this cause. Here is the link - [URL]");
		startActivity(shareint);
	}
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.donate_button)
		{
			Intent intent = new Intent();
	        String packageName = "org.oxfamindia.oxfamindiafundraiser";
	        String className = "org.oxfamindia.oxfamindiafundraiser.DonatePortalActivity";
	        intent.setClassName(packageName, className);
	        startActivity(intent);
		}
	}
	private void postDonate()
	{
		Intent intent = new Intent();
        String packageName = "org.oxfamindia.oxfamindiafundraiser";
        String className = "org.oxfamindia.oxfamindiafundraiser.PostDonateActivity";
        intent.setClassName(packageName, className);
        startActivity(intent);
	}
}
