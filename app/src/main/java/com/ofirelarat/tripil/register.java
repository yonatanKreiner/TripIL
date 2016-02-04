package com.ofirelarat.tripil;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DBHelper(this);
    }

    public void onClickRegister(View view){
        EditText userName=(EditText)findViewById(R.id.userName);
        EditText pass=(EditText)findViewById(R.id.pass);
        EditText firstName=(EditText)findViewById(R.id.firstName);
        EditText lastName=(EditText)findViewById(R.id.lastName);
        EditText mail=(EditText)findViewById(R.id.mail);
        EditText phone=(EditText)findViewById(R.id.phone);

        User user = new User();
        user.setUsername(userName.getText().toString());
        user.setPassword(pass.getText().toString());
        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setMail(mail.getText().toString());
        user.setPhone(phone.getText().toString());

     //  String p= dbHandler.getPassUser(userName.getText().toString());
     // if(p.equals("not exist")) {
            db.AddUser(user);
            Intent i = new Intent(this, trips.class);
            startActivity(i);
      //  }
       // else
        //    Toast.makeText(this,"user name already exist",Toast.LENGTH_LONG).show();
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
