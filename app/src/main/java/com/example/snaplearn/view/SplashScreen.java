package com.example.snaplearn.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snaplearn.R;

public class SplashScreen extends AppCompatActivity {
    ImageView splashImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


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
}
