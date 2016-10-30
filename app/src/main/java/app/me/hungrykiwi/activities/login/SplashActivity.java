package app.me.hungrykiwi.activities.login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import app.me.hungrykiwi.R;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.handler = new Handler();
        this.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                splash();
            }
        }, 500);

    }

    public void splash() {
        ImageView image = (ImageView)this.findViewById(R.id.logo);
        ImageView image2 = (ImageView)this.findViewById(R.id.logo2);
        image.setVisibility(View.VISIBLE);
        image2.setVisibility(View.VISIBLE);
        image.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hungry_logo_transition));
        image2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hungry_logo2_transition));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                finish();
            }
        }, 4000);
    }
}
