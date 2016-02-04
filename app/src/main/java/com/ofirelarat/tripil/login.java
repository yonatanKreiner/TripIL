package com.ofirelarat.tripil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class login extends AppCompatActivity {
    MyDBHandler dbHandler;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "NameKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHandler = new MyDBHandler(this);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void onClickLogin(View view){
        EditText userName = (EditText)findViewById(R.id.userName);
        EditText pass = (EditText)findViewById(R.id.pass);

   //     Bundle extras=getIntent().getExtras();
     //   if(extras!=null) {
       //     int Value = extras.getInt("id");
         //   if (Value > 0) {
                String Upass = dbHandler.getPassUser(userName.getText().toString());
                if (Upass.equals("not exist"))
                    Toast.makeText(this, "user not exist", Toast.LENGTH_LONG).show();
                else if (Upass.equals(pass.getText().toString())) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Name, userName.getText().toString());
                    editor.commit();
                    Intent i = new Intent(this, trips.class);
                    startActivity(i);
                } else {
                    Toast.makeText(this, "wrong password", Toast.LENGTH_LONG).show();
                }
           // }
       // }
    }

    public void onClickRegister(View view){
        Intent i=new Intent(this,register.class);
        startActivity(i);
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
