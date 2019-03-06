package com.weex.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;


public class SplashActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();
    }
    View textView = findViewById(R.id.fullscreen_content);
      ObjectAnimator rotationY = ObjectAnimator.ofFloat(textView, "rotationY", 0f, 360f);
      ObjectAnimator scaleX = ObjectAnimator.ofFloat(textView, "scaleX", 0.5f,0.75f , 1f);
      ObjectAnimator scaleY = ObjectAnimator.ofFloat(textView, "scaleY", 0.5f,0.75f , 1f);
      ObjectAnimator alpha = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f);
      AnimatorSet animatorSet = new AnimatorSet();
      animatorSet.play(rotationY).with(alpha).with(scaleX).with(scaleY);
      // 动画执行时长2s，默认300ms
      animatorSet.setDuration(1500);
      animatorSet.addListener(new AnimatorListenerAdapter() {
          @Override
          public void onAnimationEnd(Animator animation) {
              super.onAnimationEnd(animation);
              Intent intent = new Intent(SplashActivity.this, WXPageActivity.class);
              Uri data = getIntent().getData();
              if (data != null) {
                  intent.setData(data);
              }
              intent.putExtra("from", "splash");
              startActivity(intent);
              finish();
          }
      });
      animatorSet.start();
  }
}
