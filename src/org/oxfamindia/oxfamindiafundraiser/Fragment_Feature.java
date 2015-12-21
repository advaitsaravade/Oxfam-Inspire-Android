package org.oxfamindia.oxfamindiafundraiser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Fragment_Feature extends Fragment implements OnClickListener{
	
	private ImageView featured_hero;
	private TextView project_title_featured;
	private CardView featured_card;
	private CardView donate_but;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_featured_project, container, false);
		
		featured_hero = (ImageView) rootView.findViewById(R.id.featured_hero);
		featured_hero.setImageBitmap(blur(getActivity().getApplicationContext(), BitmapFactory.decodeResource(getResources(), R.drawable.draft_image)));
        project_title_featured = (TextView) rootView.findViewById(R.id.proj_title);
        project_title_featured.setText("Rural Classrooms");
        featured_card = (CardView) rootView.findViewById(R.id.card_view_feature);
        donate_but = (CardView) rootView.findViewById(R.id.donate_button);
        featured_card.setOnClickListener(this);
        donate_but.setOnClickListener(this);
        
		
        return rootView;
	}
	
	public static Bitmap blur(Context ctx, Bitmap image) {
		 final float BITMAP_SCALE = 0.4f;
		    final float BLUR_RADIUS = 20f;
	        int width = Math.round(image.getWidth() * BITMAP_SCALE);
	        int height = Math.round(image.getHeight() * BITMAP_SCALE);

	        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
	        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

	        RenderScript rs = RenderScript.create(ctx);
	        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
	        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
	        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
	        theIntrinsic.setRadius(BLUR_RADIUS);
	        theIntrinsic.setInput(tmpIn);
	        theIntrinsic.forEach(tmpOut);
	        tmpOut.copyTo(outputBitmap);

	        return outputBitmap;
	    }

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.card_view_feature)
		{
			Intent intent = new Intent();
	         String packageName = "org.oxfamindia.oxfamindiafundraiser";
	         String className = "org.oxfamindia.oxfamindiafundraiser.IndividualProjectsActivity";
	         intent.setClassName(packageName, className);
	         intent.putExtra("url", v.getId());
	         intent.putExtra("origin_activity", "Feature"); // Change this if this method is being used dynamically with other activities
	         v.getContext().startActivity(intent);
		}
		else if(id == R.id.donate_button)
		{
			Intent intent = new Intent();
	        String packageName = "org.oxfamindia.oxfamindiafundraiser";
	        String className = "org.oxfamindia.oxfamindiafundraiser.DonatePortalActivity";
	        intent.setClassName(packageName, className);
	        startActivity(intent);
		}
	}
}
