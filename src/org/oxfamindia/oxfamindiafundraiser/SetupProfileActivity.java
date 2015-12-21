package org.oxfamindia.oxfamindiafundraiser;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SetupProfileActivity extends ActionBarActivity {

	private ImageView profile_pic;
	private EditText fname;
	private EditText lname;
	private EditText email;
	private EditText address;
	private EditText city;
	private EditText state;
	private EditText country;
	private EditText pincode;
	private EditText mobileno;
	private EditText pannumb;
	private SQLiteDatabase db;
	private SharedPreferences appPrefs;
	
	private boolean profileset = false;
	private static int RESULT_LOAD_IMAGE = 1;
	private String picturePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_profile);
		profile_pic = (ImageView) findViewById(R.id.acc_photo);
		fname = (EditText) findViewById(R.id.f_name);
		lname = (EditText) findViewById(R.id.l_name);
		email = (EditText) findViewById(R.id.emailid);
		address = (EditText) findViewById(R.id.address);
		city = (EditText) findViewById(R.id.city);
		state = (EditText) findViewById(R.id.state);
		country = (EditText) findViewById(R.id.country);
		pincode = (EditText) findViewById(R.id.pincode);
		mobileno = (EditText) findViewById(R.id.mobilenumber);
		pannumb = (EditText) findViewById(R.id.pannumber);
		appPrefs = getSharedPreferences("org.oxfamindia.oxfamindiafundraiser", MODE_PRIVATE);
		
		Typeface type = Typeface.createFromAsset(getAssets(),"robotolight.ttf");
		
		fname.setTypeface(type);
		lname.setTypeface(type);
		email.setTypeface(type);
		address.setTypeface(type);
		city.setTypeface(type);
		state.setTypeface(type);
		country.setTypeface(type);
		pincode.setTypeface(type);
		mobileno.setTypeface(type);
		pannumb.setTypeface(type);
		
		boolean profilesetup = appPrefs.getBoolean("profilesetup", false);
		if(!profilesetup)
		{
			new AlertDialog.Builder(this)
		    .setMessage("Setting up a donor profile allows you to contribute to the causes you care" +
		    		" about without having to enter your details every time. You can fill in your details later" +
		    		" too.")
		    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue with delete
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_alert)
		     .show();
		}
		
		setupProfilePic();
	}

	private void setupProfilePic() {
		profile_pic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(
				Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						
			    startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			picturePath = cursor.getString(columnIndex); // We found love.. no wait.. we found the path!
			FileOutputStream out;
		    try {
		        out = openFileOutput("profile.png", Context.MODE_PRIVATE);
		        BitmapFactory.Options opt = new BitmapFactory.Options();
		        opt.inSampleSize=4; // reduced the image to 1/4 of the orignal size
		        Bitmap b = BitmapFactory.decodeFile(picturePath, opt);
		        getCircularBitmap(b).compress(Bitmap.CompressFormat.PNG, 90, out);
		        out.close();
		        profileset = true;
		    } catch (Exception e) {
		        e.printStackTrace();
		        profileset = false;
		    }
			
			cursor.close();
			previewProfilePic("profile.png");
		}
	}
	private void previewProfilePic(String name) {
		Bitmap b = null;
		try{
		    FileInputStream fis = openFileInput(name);
		    b = BitmapFactory.decodeStream(fis);
		    fis.close();
		    profile_pic.setImageBitmap(b);
		    }
		    catch(Exception e){
		    }
	}

	public static Bitmap getCircularBitmap(Bitmap bitmap) {
	    Bitmap output;

	    if (bitmap.getWidth() > bitmap.getHeight()) {
	        output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Config.ARGB_8888);
	    } else {
	        output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Config.ARGB_8888);
	    }

	    Canvas canvas = new Canvas(output);

	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

	    float r = 0;

	    if (bitmap.getWidth() > bitmap.getHeight()) {
	        r = bitmap.getHeight() / 2;
	    } else {
	        r = bitmap.getWidth() / 2;
	    }

	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawCircle(r, r, r, paint);
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);
	    return output;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setup_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_skip) {
			// Random comment.
			// This is a comment talking to you.
			// You could smile.
			// Always helps to smile.
			// That's more like it! (y)
			 Intent intent = new Intent();
	         String packageName = "org.oxfamindia.oxfamindiafundraiser";
	         String className = "org.oxfamindia.oxfamindiafundraiser.ProjectsActivity";
	         intent.setClassName(packageName, className);
	         startActivity(intent);
	         finish();
		}
		else if(id == R.id.action_next)
		{
			if(checkFormValidity())
			{
				storeDataPlz();
				Toast.makeText(getApplicationContext(), "Great! Your profile is complete", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
		        String packageName = "org.oxfamindia.oxfamindiafundraiser";
		        String className = "org.oxfamindia.oxfamindiafundraiser.ProjectsActivity";
		        intent.setClassName(packageName, className);
		        startActivity(intent);
		        finish();
			}
		}
		return true;
	}

	private void storeDataPlz() {
		db = openOrCreateDatabase("inspire_app.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		try
		{
		final String CREATE_TABLE_CITIES = "CREATE TABLE profile (id INTEGER PRIMARY KEY AUTOINCREMENT, isProfilePicSet INTEGER, fname TEXT, lname TEXT, email TEXT, address TEXT, city TEXT, state TEXT, country TEXT, pincode INTEGER, mobilenumber TEXT, pannumber TEXT);";
		db.execSQL(CREATE_TABLE_CITIES);
		}
		catch (SQLiteException e){ // Aare wah! The table already exists! Good.
		}
		ContentValues cv = new ContentValues();
		cv.put("isProfilePicSet", isTheProfilePicSet());
		cv.put("fname", fname.getText().toString());
		cv.put("lname", lname.getText().toString());
		cv.put("email", email.getText().toString());
		cv.put("address", address.getText().toString());
		cv.put("city", city.getText().toString());
		cv.put("state", state.getText().toString());
		cv.put("country", country.getText().toString());
		cv.put("pincode", pincode.getText().toString());
		cv.put("mobilenumber", mobileno.getText().toString());
		cv.put("pannumber", pannumb.getText().toString());
		db.insert("profile", null, cv);
		db.close();
		SharedPreferences.Editor sharedprefedit = appPrefs.edit();
 	    sharedprefedit.putBoolean("profilesetup", true).commit();
	}

	private int isTheProfilePicSet() {
		if(profileset)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

	private boolean checkFormValidity() {
		int l1 = fname.getText().toString().length();
		int l2 = lname.getText().toString().length();
		int l3 = email.getText().toString().length();
		int l4 = address.getText().toString().length();
		int l5 = city.getText().toString().length();
		int l6 = state.getText().toString().length();
		int l7 = country.getText().toString().length();
		int l8 = pincode.getText().toString().length();
		int l9 = mobileno.getText().toString().length();
		if(l1 != 0 && l2 != 0 && l3 != 0 && l4 != 0 && l5 != 0 && l6 != 0 && l7 != 0 && l8 != 0 && l9 != 0)
		{
			return true;
		}
		else
		{
		Toast.makeText(getApplicationContext(), "Oops! You forgot to fill in some fields", Toast.LENGTH_LONG).show();
		return false;
		}
	}
}
