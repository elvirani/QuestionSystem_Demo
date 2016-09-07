package com.example.yanni.questionsystem_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

import pojo.User;
import utils.SharedPreferencesUtils;

@ContentView(value = R.layout.activity_welcome)
public class WelcomeActivity extends AppCompatActivity {
    @ViewInject(value = R.id.welcome_linear)
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
// 设置动画透明度
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(3000);
        layout.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                User info = SharedPreferencesUtils.getInfo(WelcomeActivity.this);
                if (info == null) {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    finish();
                } else {
                    //自动登录
                    //  判断是否自动登录
                    if (SharedPreferencesUtils.judgeState(WelcomeActivity.this)) {
                        User infos = SharedPreferencesUtils.getInfo(WelcomeActivity.this);
                        HttpAutoLogin(infos.getUsername(), infos.getPassword());
//            Intent it = new Intent(this, TestMainActivity.class);
//            it.putExtra("nickname", SharedPreferencesUtils.getInfo(this).getNickname());
//            startActivity(it);
//            this.finish();
                    } else {
                        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                        finish();
                    }
                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

//  定时跳转
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//            }
//        }, 3000);
    }

    private void HttpAutoLogin(String name, String pass) {
        final RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/login");
        params.addBodyParameter("username", name);
        params.addBodyParameter("password", pass);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("user");
                    String nickname = jsonObject1.getString("nickname");
                    String username = jsonObject1.getString("username");
                    String password = jsonObject1.getString("password");
                    String success = jsonObject.getString("success");

                    if (success.equals("true")) {

                        SharedPreferencesUtils.saveNamePass(new User(username, password, nickname), WelcomeActivity.this);
                        Intent it = new Intent(WelcomeActivity.this, TestMainActivity.class);
                        it.putExtra("nickname", nickname);
                        startActivity(it);

                        WelcomeActivity.this.finish();
                    } else {

                        Toast.makeText(WelcomeActivity.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("onerror" + "============================");

                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }
}
