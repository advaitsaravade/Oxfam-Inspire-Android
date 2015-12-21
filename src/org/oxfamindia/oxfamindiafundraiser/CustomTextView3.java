package org.oxfamindia.oxfamindiafundraiser;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView3 extends TextView {
	public CustomTextView3(Context context, AttributeSet attrs) {
        super(context, attrs);      
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "robotomedium.ttf"));
    }
}