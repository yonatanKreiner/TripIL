package com.ofirelarat.tripil;

import android.content.Intent;
import android.net.Uri;
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

import java.util.Vector;

public class AddTrip extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE=1;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        img=(ImageView)findViewById(R.id.imageID);
        attraction=(EditText)findViewById(R.id.attraction_id);
        hotels=(EditText)findViewById(R.id.hotel_id);

        dropdown = (Spinner)findViewById(R.id.spinner);
        String[] items = new String[]{"choose","Jerusalem","Tel Aviv","Eilat","Haifa", "Central", "Northern","Southern"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        spinnerDay = (Spinner)findViewById(R.id.spinnerDay);
        String[] itemsDays = new String[31];
        for(int i=0;i<31;i++){
            itemsDays[i]=String.valueOf(i+1);
        }
        ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsDays);
        spinnerDay.setAdapter(adapterDays);

        spinnerMonth = (Spinner)findViewById(R.id.spinnerMonth);
        String[] itemsMonth = new String[12];
        for(int i=0;i<12;i++){
            itemsMonth[i]=String.valueOf(i+1);
        }
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsMonth);
        spinnerMonth.setAdapter(adapterMonth);

        spinnerYear = (Spinner)findViewById(R.id.spinnerYear);
        String[] itemsYear = new String[16];
        for(int i=15;i>=0;i--){
            itemsYear[15-i]=String.valueOf(i+2001);
        }
        ArrayAdapter<String> adapterYear= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsYear);
        spinnerYear.setAdapter(adapterYear);

        spinnerDayR = (Spinner)findViewById(R.id.spinnerDayR);
        spinnerDayR.setAdapter(adapterDays);
        spinnerMonthR = (Spinner)findViewById(R.id.spinnerMonthR);
        spinnerMonthR.setAdapter(adapterMonth);
        spinnerYearR = (Spinner)findViewById(R.id.spinnerYearR);
        spinnerYearR.setAdapter(adapterYear);
    }

    public void onClickUploadIMG(View view){
        Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
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
        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data!=null){
            Uri selectedImage=data.getData();
            img.setImageURI(selectedImage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
