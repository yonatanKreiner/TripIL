package com.ofirelarat.tripil;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

public class AddTrip extends AppCompatActivity {

    public static String path= Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final int RESULT_LOAD_IMAGE = 1;
    private boolean didUpload = false;
    private ImageView img;
    private EditText attraction;
    private EditText hotels;
    private Spinner dropdown;
    private Spinner spinnerDay;
    private Spinner spinnerMonth;
    private Spinner spinnerYear;
    private Spinner spinnerDayR;
    private Spinner spinnerMonthR;
    private Spinner spinnerYearR;

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "NameKey";
    public static final String picName="picName";
    boolean flag = false;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        didUpload = false;
        setContentView(R.layout.activity_add_trip);
        db = new DBHelper(this);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(sharedPreferences.getString("NameKey",null)==null) {
            Toast.makeText(getApplicationContext(), "you have to loged in first", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), login.class);
            startActivity(i);
        }

        int iPic=sharedPreferences.getInt(picName, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(picName);
        editor.commit();
        editor.putInt(picName, iPic);
        editor.commit();

        img=(ImageView)findViewById(R.id.imageID);
        attraction=(EditText)findViewById(R.id.attraction_id);
        hotels=(EditText)findViewById(R.id.hotel_id);

        dropdown = (Spinner)findViewById(R.id.spinner);
        String[] items = new String[]{"choose","Jerusalem","Tel Aviv","Eilat","Haifa", "Central", "Northern","Southern"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        spinnerDay = (Spinner)findViewById(R.id.spinnerDay);
        String[] itemsDays = new String[31];
        for(int i=0;i<31;i++){
            itemsDays[i]=String.valueOf(i+1);
        }
        ArrayAdapter<String> adapterDays = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsDays);
        spinnerDay.setAdapter(adapterDays);

        spinnerMonth = (Spinner)findViewById(R.id.spinnerMonth);
        String[] itemsMonth = new String[12];
        for(int i=0;i<12;i++){
            itemsMonth[i]=String.valueOf(i+1);
        }
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsMonth);
        spinnerMonth.setAdapter(adapterMonth);

        spinnerYear = (Spinner)findViewById(R.id.spinnerYear);
        String[] itemsYear = new String[16];
        for(int i=15;i>=0;i--){
            itemsYear[15-i]=String.valueOf(i+2001);
        }
        ArrayAdapter<String> adapterYear= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsYear);
        spinnerYear.setAdapter(adapterYear);

        spinnerDayR = (Spinner)findViewById(R.id.spinnerDayR);
        spinnerDayR.setAdapter(adapterDays);
        spinnerMonthR = (Spinner)findViewById(R.id.spinnerMonthR);
        spinnerMonthR.setAdapter(adapterMonth);
        spinnerYearR = (Spinner)findViewById(R.id.spinnerYearR);
        spinnerYearR.setAdapter(adapterYear);
    }

    public void onClickUpload(View view){
        flag = false;
        didUpload = true;
        Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    public void onClickCamera(View view){
        flag = true;
        didUpload = true;
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    public void onClickAddAttraction(View view){
        String txt=attraction.getText().toString()+", ";
        attraction.setText(txt);
        attraction.setSelection(attraction.getText().length());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public void onClickAddHotel(View view){
        String txt=hotels.getText().toString()+", ";
        hotels.setText(txt);
        hotels.setSelection(hotels.getText().length());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(!flag) {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                img.setImageURI(selectedImage);
            }
        }
        else{
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(bp);
        }
    }


    public void onClickSubmit(View view){
        String userName = sharedPreferences.getString("NameKey", null);
        String arrivalDate = spinnerDay.getSelectedItem().toString()+ "/" +
                spinnerMonth.getSelectedItem().toString() + "/" +
                spinnerYear.getSelectedItem().toString();
        String returnDate = spinnerDayR.getSelectedItem().toString() + "/" +
                spinnerMonthR.getSelectedItem().toString() + "/" +
                spinnerYearR.getSelectedItem().toString();
        String area = dropdown.getSelectedItem().toString();
        String hotel = hotels.getText().toString();
        String attractions = attraction.getText().toString();
        EditText travelG = (EditText)findViewById(R.id.travelG);
        String travelGuide = travelG.getText().toString();
        EditText descriptionE = (EditText)findViewById(R.id.description);
        String description = descriptionE.getText().toString();
        img.buildDrawingCache();
        String path = null;
        Bitmap bmap = img.getDrawingCache();
        path = saveToInternalStorage(bmap);
        Trip trip = new Trip(userName, arrivalDate, returnDate, area, hotel, attractions, travelGuide, description, path);

        if(db.AddTrip(trip)){
            Intent i = new Intent(this, trips.class);
            startActivity(i);
        } else{
            Toast.makeText(this, "user name already exist", Toast.LENGTH_LONG).show();
        }
    }

    private String saveToInternalStorage(Bitmap finalBitmap) {
        String ret;

        if(didUpload) {
            sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            int iPic = sharedPreferences.getInt(picName, 0);
            iPic++;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(picName);
            editor.commit();
            editor.putInt(picName, iPic);
            editor.commit();
            String root = getFilesDir().toString();
            File myDir = new File(root + "/saved_images");
            myDir.mkdirs();
            String fname = "Image-" + iPic + ".jpg";
            File file = new File(myDir, fname);

            if (file.exists())
                file.delete();

            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            ret = file.getPath().toString();
        } else{
            return "";
        }

        return ret;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_details, menu);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if (sharedPreferences.getString("NameKey", null) != null) {
            MenuItem menuItem = menu.findItem(R.id.logIn);
            menuItem.setTitle("LogOut");
        } else{
            MenuItem menuItem = menu.findItem(R.id.logIn);
            menuItem.setTitle("LogIn");
            MenuItem menuItemT = menu.findItem(R.id.MyTrips);
            menuItemT.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;

        switch (item.getItemId()){
            case R.id.back:
                i = new Intent(getApplicationContext(), trips.class);
                startActivity(i);

                return true;
            case R.id.logIn:
                i = new Intent(getApplicationContext(), login.class);
                startActivity(i);

                return true;
            case R.id.MyTrips:
                i = new Intent(getApplicationContext(), myTrips.class);
                startActivity(i);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
