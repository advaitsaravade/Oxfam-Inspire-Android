package org.oxfamindia.oxfamindiafundraiser;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView2 extends TextView {
	public CustomTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);      
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "oxfamheadline.ttf"));
    }
}