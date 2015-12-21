package org.oxfamindia.oxfamindiafundraiser;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.ContentValues;
import android.content.Context;
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
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditProfileFragment extends Fragment {

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
	private Button finish;
	private SQLiteDatabase db;
	private SharedPreferences appPrefs;
	private Context context;
	private boolean profileset = false;
	
	private static int RESULT_LOAD_IMAGE = 1;
	private String picturePath;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_edit_profile_fragment, container, false);
		
		context= rootView.getContext();

		profile_pic = (ImageView) rootView.findViewById(R.id.acc_photo);
		fname = (EditText) rootView.findViewById(R.id.f_name);
		lname = (EditText) rootView.findViewById(R.id.l_name);
		email = (EditText) rootView.findViewById(R.id.emailid);
		address = (EditText) rootView.findViewById(R.id.address);
		city = (EditText) rootView.findViewById(R.id.city);
		state = (EditText) rootView.findViewById(R.id.state);
		country = (EditText) rootView.findViewById(R.id.country);
		pincode = (EditText) rootView.findViewById(R.id.pincode);
		mobileno = (EditText) rootView.findViewById(R.id.mobilenumber);
		pannumb = (EditText) rootView.findViewById(R.id.pannumber);
		finish = (Button) rootView.findViewById(R.id.finishbutton);
		
		fillInTheDetails();
		
		finish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveStuffBro();
			}

			private void saveStuffBro() {
				if(checkFormValidity())
				{
					storeDataPlz();
					Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		appPrefs = context.getSharedPreferences("org.oxfamindia.oxfamindiafundraiser", context.MODE_PRIVATE);
		
		Typeface type = Typeface.createFromAsset(context.getAssets(),"oxfamheadline.ttf");
		Typeface type2 = Typeface.createFromAsset(context.getAssets(),"robotolight.ttf");
		
		fname.setTypeface(type2);
		lname.setTypeface(type2);
		email.setTypeface(type2);
		address.setTypeface(type2);
		city.setTypeface(type2);
		state.setTypeface(type2);
		country.setTypeface(type2);
		pincode.setTypeface(type2);
		mobileno.setTypeface(type2);
		pannumb.setTypeface(type2);
		
		boolean profilesetup = appPrefs.getBoolean("profilesetup", false);
		
		setupProfilePic();
		
        return rootView;
	}
	
	private void fillInTheDetails() {
		boolean doesItExist = false;
		db = context.openOrCreateDatabase("inspire_app.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		try
		{
		final String CREATE_TABLE_CITIES = "CREATE TABLE profile (id INTEGER PRIMARY KEY AUTOINCREMENT, isProfilePicSet INTEGER, fname TEXT, lname TEXT, email TEXT, address TEXT, city TEXT, state TEXT, country TEXT, pincode INTEGER, mobilenumber TEXT, pannumber TEXT);";
		db.execSQL(CREATE_TABLE_CITIES);
		}
		catch (SQLiteException e){ 
			doesItExist = true; // This means the table already exists
		}
		if(doesItExist)
		{
			Cursor cursor = db.rawQuery("SELECT id, isProfilePicSet, fname, lname, email, address, city, state, country, pincode, mobilenumber, pannumber FROM profile ORDER BY id DESC", null);
			if(cursor.moveToFirst())
			{
			if(cursor.getInt(cursor.getColumnIndex("isProfilePicSet")) == 1)
					{
				previewProfilePic("profile.png");
					}
			else
			{
				profile_pic.setImageResource(R.drawable.account_photo);
			}
			fname.setText(cursor.getString(cursor.getColumnIndex("fname")));
			lname.setText(cursor.getString(cursor.getColumnIndex("lname")));
			email.setText(cursor.getString(cursor.getColumnIndex("email")));
			address.setText(cursor.getString(cursor.getColumnIndex("address")));
			city.setText(cursor.getString(cursor.getColumnIndex("city")));
			state.setText(cursor.getString(cursor.getColumnIndex("state")));
			country.setText(cursor.getString(cursor.getColumnIndex("country")));
			pincode.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("pincode"))));
			mobileno.setText(cursor.getString(cursor.getColumnIndex("mobilenumber")));
			pannumb.setText(cursor.getString(cursor.getColumnIndex("pannumber")));
			}
		}
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == getActivity().RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getActivity().getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			picturePath = cursor.getString(columnIndex); // We found love.. no wait.. we found the path!
			FileOutputStream out;
		    try {
		        out = getActivity().openFileOutput("profile.png", Context.MODE_PRIVATE);
		        BitmapFactory.Options opt = new BitmapFactory.Options();
		        opt.inSampleSize=4; // reduced the image to 1/4 of the orignal size
		        Bitmap b = BitmapFactory.decodeFile(picturePath, opt);
		        getCircularBitmap(b).compress(Bitmap.CompressFormat.PNG, 90, out);
		        out.close();profileset = true;
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
		    FileInputStream fis = getActivity().openFileInput(name);
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
	private void storeDataPlz() {
		db = context.openOrCreateDatabase("inspire_app.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		try
		{
		final String CREATE_TABLE_CITIES = "CREATE TABLE profile (id INTEGER PRIMARY KEY AUTOINCREMENT, INTEGER isProfilePicSet, fname TEXT, lname TEXT, email TEXT, address TEXT, city TEXT, state TEXT, country TEXT, pincode INTEGER, mobilenumber TEXT, pannumber TEXT);";
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
		Toast.makeText(context, "Oops! You forgot to fill in some fields", Toast.LENGTH_LONG).show();
		return false;
		}
	}
}
