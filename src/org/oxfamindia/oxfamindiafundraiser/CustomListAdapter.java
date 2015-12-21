package org.oxfamindia.oxfamindiafundraiser;

import java.io.FileInputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomListAdapter extends BaseAdapter{

	private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private SharedPreferences appPrefs;
     
    public CustomListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }
    
	@Override
	public int getCount() {
		return navDrawerItems.size()+1;
	}

	@Override
	public Object getItem(int position) {
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
          
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        ImageView hero_logo = (ImageView) convertView.findViewById(R.id.hero_logo);
          
        if(position == 0)
        {
        imgIcon.setImageDrawable(null);
        txtTitle.setText("");
        hero_logo.setVisibility(View.VISIBLE);
        appPrefs = context.getSharedPreferences("org.oxfamindia.oxfamindiafundraiser", context.MODE_PRIVATE);
        boolean profile_setup = appPrefs.getBoolean("profilesetup", false);
        if(profile_setup)
        {
        Bitmap b = null;
		try{
		    FileInputStream fis = context.openFileInput("profile.png");
		    b = BitmapFactory.decodeStream(fis);
		    fis.close();
		    hero_logo.setImageBitmap(b);
		    }
		    catch(Exception e){
		    }
        }
        }
        else
        {
        	imgIcon.setImageResource(navDrawerItems.get(position-1).getIcon());        
            txtTitle.setText(navDrawerItems.get(position-1).getTitle());
            hero_logo.setVisibility(View.GONE);
        }
         
        return convertView;
	}
}
