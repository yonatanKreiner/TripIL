package com.ofirelarat.tripil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class tripDetails extends AppCompatActivity {

    TextView name;
    TextView date;
    TextView hotels;
    TextView attraction;
    TextView travelGuide;
    EditText description;
    RatingBar ratingBar;
    Trip trip;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "NameKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        Bundle inputData=getIntent().getExtras();
        if (inputData==null)
            return;
        String input = inputData.getString("input");
        Context context=getApplicationContext();

        name = (TextView) findViewById(R.id.name_id);
        date = (TextView) findViewById(R.id.date_id);
        hotels = (TextView) findViewById(R.id.hotels_id);
        attraction = (TextView) findViewById(R.id.attraction_id);
        travelGuide = (TextView) findViewById(R.id.travelG);
        description = (EditText) findViewById(R.id.description_id);
        ImageView img = (ImageView) findViewById(R.id.img_id);
        Resources res = context.getResources();
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (sharedPreferences.getString("NameKey", null) != null) {
                    trip.onRate(ratingBar.getRating());
                }
            }
        });
    }

    public void onClickHotels(View view) {
        final String[] hotelStr = hotels.getText().toString().split(",");
        final int[] i = {0};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(hotelStr[i[0]]);
        builder.setCancelable(true)
                .setMessage("blablabla")
                .setPositiveButton("next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Navigate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(intent);
                    }
                });
        final AlertDialog dialog=builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // dialog.dismiss();
                if (i[0] < hotelStr.length - 1)
                    i[0]++;
                else i[0] = 0;
                dialog.setTitle(hotelStr[i[0]]);
                dialog.setMessage("nanana");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_details, menu);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.getString("NameKey", null) != null) {
            MenuItem menuItem = menu.findItem(R.id.logIn);
            menuItem.setTitle("LogOut");
        }
        else{
            MenuItem menuItem = menu.findItem(R.id.logIn);
            menuItem.setTitle("LogIn");
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
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
