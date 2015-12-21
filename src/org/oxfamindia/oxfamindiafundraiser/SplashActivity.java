package org.oxfamindia.oxfamindiafundraiser;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SplashActivity extends Activity implements AnimationListener, OnClickListener {

	private RelativeLayout layout1;
	private TextView oxfam_txt;
	private TextView subtxt;
	private ImageView oxfam_logo;
	private TextView intro1;
	private TextView intro2;
	private Animation animFadein;
	private Animation animFadeOut2;
	private Animation animFadein3;
	private Animation button_animFadein3;
	private Animation button_animFadein3_xtra;
	private Button nxtbut;
	private Button nxtbut2;
	private SharedPreferences appPrefs;
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
		    // Call some material design APIs here
			Window window = this.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(this.getResources().getColor(R.color.blacktrans));
		} else {
		    // Implement this feature without material design
		}
		setContentView(R.layout.activity_splash);
		layout1 = (RelativeLayout) findViewById(R.id.splash_layout);
		oxfam_txt = (TextView) findViewById(R.id.oxfam_txt);
		subtxt = (TextView) findViewById(R.id.subtxt);
		oxfam_logo = (ImageView) findViewById(R.id.logo_icon);
		intro1 = (TextView) findViewById(R.id.intro1);
		intro2 = (TextView) findViewById(R.id.intro2);
		nxtbut = (Button) findViewById(R.id.next_button);
		nxtbut2 = (Button) findViewById(R.id.next_button2);
		nxtbut.setOnClickListener(this);
		nxtbut.setClickable(false);
		nxtbut2.setOnClickListener(this);
		nxtbut2.setClickable(false);
		
		appPrefs = getSharedPreferences("org.oxfamindia.oxfamindiafundraiser", MODE_PRIVATE);
		
		Typeface type = Typeface.createFromAsset(getAssets(),"oxfamheadline.ttf");
		Typeface type2 = Typeface.createFromAsset(getAssets(),"robotolight.ttf");
		
		oxfam_txt.setTypeface(type);
		subtxt.setTypeface(type);
		intro1.setTypeface(type);
		intro2.setTypeface(type);
		nxtbut.setTypeface(type);
		nxtbut2.setTypeface(type);
		
		animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.spash_anim);
		animFadeOut2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
		animFadein3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);	
		button_animFadein3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_fadein);
		button_animFadein3_xtra = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_fadein);
		animFadein.setAnimationListener(this);
		button_animFadein3.setAnimationListener(this);
		button_animFadein3_xtra.setAnimationListener(this);
		oxfam_logo.setAnimation(animFadein);
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if(animation.equals(animFadein))
		{
		 new CountDownTimer(1500, 1000) {

		     public void onTick(long millisUntilFinished) {
		     }

		     public void onFinish() {
		 		 boolean fuser = appPrefs.getBoolean("fuser", true);
		 		 if(!fuser)
		 		 {
		    	 Intent intent = new Intent();
		         String packageName = "org.oxfamindia.oxfamindiafundraiser";
		         String className = "org.oxfamindia.oxfamindiafundraiser.ProjectsActivity";
		         intent.setClassName(packageName, className);
		         startActivity(intent);
		         finish();
		 		 }
		 		 else
		 		 {
		    	 oxfam_logo.startAnimation(animFadeOut2);
			   	 oxfam_txt.startAnimation(animFadeOut2);
			   	 subtxt.startAnimation(animFadeOut2);
			   	 TransitionDrawable transition = (TransitionDrawable) layout1.getBackground();
			   	 transition.startTransition(1000);
			   	 intro1.startAnimation(animFadein3);
			   	 nxtbut.startAnimation(button_animFadein3);
			   	 nxtbut.setClickable(true);
		 		 }
		     }
		  }.start();
		}
		else if(animation.equals(button_animFadein3_xtra))
		{
			nxtbut2.setClickable(true);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.next_button)
		{
			intro1.startAnimation(animFadeOut2);
			nxtbut.startAnimation(animFadeOut2);
			intro2.startAnimation(animFadein3);
			nxtbut2.startAnimation(button_animFadein3_xtra);
		}
		else if(id == R.id.next_button2)
		{
			 SharedPreferences.Editor sharedprefedit = appPrefs.edit();
	  	     sharedprefedit.putBoolean("fuser", false).commit();
			 Intent intent = new Intent();
	         String packageName = "org.oxfamindia.oxfamindiafundraiser";
	         String className = "org.oxfamindia.oxfamindiafundraiser.SetupProfileActivity";
	         intent.setClassName(packageName, className);
	         startActivity(intent);
	         finish();
		}
			
	}
}
