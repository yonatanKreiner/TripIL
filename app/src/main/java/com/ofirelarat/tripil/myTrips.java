package com.ofirelarat.tripil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class myTrips extends AppCompatActivity {
    ListView listview;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "NameKey";
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(52, 152, 219)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        db = new DBHelper(this);
        listview = (ListView) findViewById(R.id.ListView1);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String userName=sharedPreferences.getString("NameKey", null);
        Trip[] trips = db.FindTripsByUser(userName);
        if(trips != null) {
            listview.setAdapter(new CostumAdapter(this, trips));
        }
        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String tripId =  String.valueOf(parent.getItemAtPosition(position));
                        Intent i = new Intent(getApplicationContext(), tripDetails.class);
                        i.putExtra("input", tripId);
                        startActivity(i);
                    }
                }
        ) ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_details, menu);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        MenuItem menuItemT = menu.findItem(R.id.MyTrips);
        menuItemT.setVisible(false);
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
            case R.id.MyTrips:
                i = new Intent(getApplicationContext(), myTrips.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
