package com.ofirelarat.tripil;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class tripDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        Bundle inputData=getIntent().getExtras();
        if (inputData==null)
            return;
        String[] input=inputData.getStringArray("input");
        Context context=getApplicationContext();

        TextView name=(TextView)findViewById(R.id.name_id);
        TextView star=(TextView)findViewById(R.id.star_id);
        ImageView img=(ImageView)findViewById(R.id.img_id);
        Resources res =context.getResources();

        name.setText(input[0]);
     //   star.setText(input[1]);
       // int id = res.getIdentifier(input[2],"drawable",context.getPackageName());
       // img.setImageResource(id);

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
