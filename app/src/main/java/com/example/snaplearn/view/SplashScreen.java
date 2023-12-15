package com.example.snaplearn.view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snaplearn.R;

public class SplashScreen extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    ImageView splashImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        mediaPlayer = MediaPlayer.create(this,R.raw.dummy_sound);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });

        // Start playing the sound
        mediaPlayer.start();
        splashImageView = findViewById(R.id.splash_image_view);
        Animation splash_animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        splashImageView.startAnimation(splash_animation);


        splash_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //..
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashScreen.this, Login.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //..
            }
        });

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashScreen.this, Login.class);
//                startActivity(intent);
//                finish();
//            }
//        },3000);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Release MediaPlayer resources when the activity is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }


}}
