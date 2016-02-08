package com.ofirelarat.tripil;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler myHandler = new Handler();
        myHandler.postDelayed(mMyRunnable, 2000);
    }

    private Runnable mMyRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            Intent i = new Intent(getApplicationContext(),login.class);
            startActivity(i);
        }
    };
}
