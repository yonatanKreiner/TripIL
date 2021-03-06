package com.ofirelarat.tripil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class tripDetails extends AppCompatActivity implements DAL.AsyncListener{
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
    DBHelper db;
    private List<Hotel> hotelsDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        Bundle inputData=getIntent().getExtras();
        if (inputData==null)
            return;
        int input = inputData.getInt("input");

        name = (TextView) findViewById(R.id.name_id);
        date = (TextView) findViewById(R.id.date_id);
        hotels = (TextView) findViewById(R.id.hotels_id);
        attraction = (TextView) findViewById(R.id.attraction_id);
        travelGuide = (TextView) findViewById(R.id.travelG);
        description = (EditText) findViewById(R.id.description_id);
        ImageView img = (ImageView) findViewById(R.id.img_id);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);

        db = new DBHelper(this);
        trip=db.FindTripsById(input);

        if(trip==null){
            Intent intent = new Intent(getApplicationContext(), trips.class);
            startActivity(intent);
        }

        for (String hotel:trip.getHotels()) {
            new DAL("Hotels", hotel, this).execute();
        }


        name.setText(trip.getUsername());
        date.setText(trip.getArrivalDate()+"-"+trip.getReturnDate());
        hotels.setText(Common.ArrayToString(trip.getHotels()));
        attraction.setText(Common.ArrayToString(trip.getAttractions()));
        travelGuide.setText(trip.getTravelGuide());
        description.setText(trip.getDescription());
        String path=trip.getPictures()[0];
        loadImageFromStorage(path);

        EditText re=(EditText)findViewById(R.id.editText);
        Button btn=(Button)findViewById(R.id.btnId);
        ratingBar.setRating(trip.getStars());
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.getString("NameKey", null) == null) {
            ratingBar.setIsIndicator(true);
            re.setVisibility(View.INVISIBLE);
            btn.setVisibility(View.INVISIBLE);
        }
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (sharedPreferences.getString("NameKey", null) != null) {
                        trip.onRate(ratingBar.getRating());
                    }
                    db.UpdateTrip(trip);
                    ratingBar.setRating(trip.getStars());
                    ratingBar.setIsIndicator(true);
                }
            });

        ListView listview = (ListView) findViewById(R.id.listView);
        review[] r = db.FindReviewByTripId(trip.getId());

        if(r != null) {
            listview.setAdapter(new CostumAdapterReview(this,r));
        }
    }

    private void loadImageFromStorage(String path)
    {
        File imageFile=new File(path);
        if(imageFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(path);
            ImageView img=(ImageView)findViewById(R.id.img_id);
            img.setImageBitmap(myBitmap);
        }

    }

    public void onClickHotels(View view) {
        final int[] i = {0};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(trip.getHotels()[i[0]]);
        builder.setCancelable(true)
                .setMessage(Common.GetStringFromMap(hotelsDetails.get(0).getAttributes()))
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
                if (i[0] < trip.getHotels().length - 1)
                    i[0]++;
                else i[0] = 0;
                dialog.setTitle(trip.getHotels()[i[0]]);
                dialog.setMessage("nanana");
            }
        });

    }

    public void onClickSend(View view){
        String reviewE=((EditText)findViewById(R.id.editText)).getText().toString();
        review r=new review(trip.getId(),sharedPreferences.getString("NameKey", null),reviewE);
        db.AddReview(r);

        ListView listview = (ListView) findViewById(R.id.listView);
        review[] rs = db.FindReviewByTripId(trip.getId());

        if(rs != null) {
            listview.setAdapter(new CostumAdapterReview(this, rs));
        }

        ((EditText)findViewById(R.id.editText)).setText("");
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

    @Override
    public void updateData(JSONArray fields, JSONArray records) {
        try {
            JSONObject temp;
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < records.length(); i++) {
                temp = records.getJSONObject(i);
                map.put(temp.getString("SpecificationAttributeName"), temp.getString("CustomValue"));
                /*switch (records.getString(i)){
                    case "Shortdescription":
                    case "Shortdescription":
                    case "Shortdescription":
                    case "Shortdescription":
                    case "Shortdescription":
                    case "Shortdescription":
                    case "rank":
                }*/
            }
            Hotel help = new Hotel(map);
            hotelsDetails.add(help);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
