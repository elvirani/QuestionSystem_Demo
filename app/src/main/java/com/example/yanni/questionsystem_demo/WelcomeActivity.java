package com.example.yanni.questionsystem_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

@ContentView(value = R.layout.activity_welcome)
public class WelcomeActivity extends AppCompatActivity {
@ViewInject(value = R.id.welcome_linear)
private LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_welcome);
        x.view().inject(this);

        AlphaAnimation animation = new AlphaAnimation(0.0f,1.0f);
        animation.setDuration(3000);
        layout.setAnimation(animation);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                finish();

            }
        },3000);



    }
}
