package com.codi6.proyect.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.codi6.proyect.R;

import java.util.Timer;
import java.util.TimerTask;

public class ActivitySplashScreen extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 2000;
    private static final long CACHE_EXPIRATION = 43000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentAndFullscreen();
        // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        addTimer();
    }

    private void setContentAndFullscreen() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
    }

    private void addTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                openMainActivity();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }


    private void openMainActivity() {
        final Intent intent = new Intent(this, ActivityMain.class);
        this.startActivity(intent);
        this.finish();
    }
}
