package org.oxfamindia.oxfamindiafundraiser;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ProjectsActivity extends ActionBarActivity {

	private String[] nav_titles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
	private ActionBar actionbar;
	private RelativeLayout mDrawerHolderLayout;
	private int stuff_position = 0;
    
	private String[] navMenuTitles;
    private TypedArray navMenuIcons;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_projects);
	    actionbar = getSupportActionBar();
		      
		nav_titles = getResources().getStringArray(R.array.nav_drawer_items);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.listview);
		mDrawerHolderLayout = (RelativeLayout) findViewById(R.id.left_drawer);
		
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		 
        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
		ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();
		 
        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));
        
		final CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), navDrawerItems);
		// Set the adapter for the list view
		mDrawerList.setAdapter(adapter);
		        
		        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, 
		        		R.string.drawer_closed){
		        	/** Called when a drawer has settled in a completely closed state. */
		            public void onDrawerClosed(View view) {
		                super.onDrawerClosed(view);
		                actionbar.setTitle(nav_titles[stuff_position]);
		                FragmentManager fragmentManager = getSupportFragmentManager();
		                List<Fragment> fragments = fragmentManager.getFragments();
		                for(Fragment fragment : fragments){
		                    if(fragment != null && fragment.isVisible())
		                    {
		                        if(fragment.getTag().equals("Profile"))
		                        {
		                        	adapter.notifyDataSetChanged();
		                        }
		                    }
		                }
		            }

		            /** Called when a drawer has settled in a completely open state. */
		            public void onDrawerOpened(View drawerView) {
		                super.onDrawerOpened(drawerView);
		                actionbar.setTitle("Menu");
		            }
		        };
		 mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				setupTheWindow(position);
			}
		});
		        
		        

		        // Set the drawer toggle as the DrawerListener
		        mDrawerLayout.setDrawerListener(mDrawerToggle);

		        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		        getSupportActionBar().setHomeButtonEnabled(true);
		        
		        if (savedInstanceState == null) {
		            // on first time display view for first nav item
		            setupTheWindow(1);
		        }
	}
	protected void setupTheWindow(int position) {
		stuff_position = position;
		Fragment fragment = null;
		switch(position)
		{
		case 0:
			fragment = new EditProfileFragment();
			break;
		case 1:
			fragment = new Fragment_Feature();
			break;
		case 2:
			fragment = new Fragment_Projects();
			break;
		case 3:
			fragment = new Fragment_Impact();
			break;
		case 4:
			fragment = new Fragment_Blog();
			break;
		case 5:
			fragment = new Fragment_AboutOxfamIndia();
			break;
		case 6:
			fragment = new Fragment_ContactUs();
			break;
		case 7:
			fragment = new Fragment_AboutApp();
			break;
		default:
			break;
		}
		if(fragment != null)
		{
			FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment, nav_titles[position]).commit(); // Adding a tag here by nav_titles[pos]
 
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(stuff_position, true);
            mDrawerList.setSelection(stuff_position);
            mDrawerLayout.closeDrawer(mDrawerHolderLayout);
            actionbar.setTitle(nav_titles[position]);
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Hello.",  Toast.LENGTH_LONG).show();
		}
	}
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.share_menu, menu);
		return true;
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        else if(item.getItemId() == R.id.share)
        {
        	doShare();
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
	private void doShare() {
		Intent shareint = new Intent(Intent.ACTION_SEND);
		shareint.setType("text/plain");
		shareint.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_app));
		startActivity(shareint);
	}
}
