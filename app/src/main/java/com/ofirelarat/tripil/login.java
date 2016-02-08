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
    DBHelper db;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "NameKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DBHelper(this);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Name);
        editor.commit();
    }

    public void onClickLogin(View view){
        String userName = ((EditText)findViewById(R.id.userName)).getText().toString();
        String pass = ((EditText)findViewById(R.id.pass)).getText().toString();

        User user = db.FindUser(userName, pass);

        if(user == null){
            Toast.makeText(this, "user or password is incorrect", Toast.LENGTH_LONG).show();
        } else{
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Name, user.getUsername());
            editor.commit();
            Intent i = new Intent(this, trips.class);
            startActivity(i);
        }
    }

    public void onClickRegister(View view){
        Intent i=new Intent(this,register.class);
        startActivity(i);
    }

    public void onClickGuest(View view){
        Intent i=new Intent(this,trips.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_details, menu);
        MenuItem menuI = menu.findItem(R.id.back);
        menuI.setVisible(false);
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
