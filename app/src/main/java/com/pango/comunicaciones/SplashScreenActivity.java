package com.pango.comunicaciones;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.pango.comunicaciones.controller.DataController;
import com.pango.comunicaciones.controller.ViddetController;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    //private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen_activity);

        final DataController obj = new DataController("url","get", SplashScreenActivity.this);
        obj.execute(String.valueOf(1),String.valueOf(33));

        final Handler h = new Handler();
        h.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (obj.getStatus() == AsyncTask.Status.FINISHED) {
                    Intent mainIntent = new Intent()
                            .setClass(SplashScreenActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }else {
                    h.postDelayed(this, 250);
                }


            }
        }, 250);


        /*TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Start the next activity
                Intent mainIntent = new Intent().setClass(
                        SplashScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);

                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                // finish();
            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);*/
    }

}