package com.ofirelarat.tripil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class trips extends AppCompatActivity {

    ListView listview;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "NameKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int[] id={1,2};
        String[] names = {"yonatan","ofir elarat"};
        String[] stars ={"tel aviv,stars:4.5","eilat,stars:4"};
        String[] imgs = {"home","home"};

        listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(new CostumAdapter(this, id, names, stars, imgs));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(52, 152, 219)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                if (sharedPreferences.getString("NameKey", null) != null) {
                    Intent i = new Intent(getApplicationContext(), AddTrip.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "you have to loged in first", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), login.class);
                    startActivity(i);
                }
            }
        });

        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int tripId = (Integer)parent.getItemAtPosition(position);
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
