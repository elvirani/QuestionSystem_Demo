package com.example.yanni.questionsystem_demo;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import pojo.User;
import utils.SharedPreferencesUtils;
import view.ShowLoadingDialog;

@ContentView(value = R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @ViewInject(value = R.id.mtoolbar)
    private Toolbar toolbar;
    @ViewInject(value = R.id.login_username)
    private EditText et_username;
    @ViewInject(value = R.id.login_pass)
    private EditText et_pass;
    Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

//  设置标题栏
        toolbar.setTitle(R.string.btn_login);
        toolbar.setBackgroundResource(R.color.actionbar_bg);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

////  判断是否自动登录
//        if (SharedPreferencesUtils.judgeState(this)) {
//            User info = SharedPreferencesUtils.getInfo(this);
//            HttpAutoLogin(info.getUsername(),info.getPassword());
////            Intent it = new Intent(this, TestMainActivity.class);
////            it.putExtra("nickname", SharedPreferencesUtils.getInfo(this).getNickname());
////            startActivity(it);
////            this.finish();
//        }
    }

    @Event(value = {R.id.login_register, R.id.login_btn, R.id.login_forget_pwd}, type = View.OnClickListener.class)
    private void setClick(View v) {
        switch (v.getId()) {
//  注册
            case R.id.login_register: {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
            break;
//  登录
            case R.id.login_btn: {
                String name = et_username.getText().toString();
                String pass = et_pass.getText().toString();
                ShowLoadingDialog.createLoadingDialog(LoginActivity.this);
                HttpLogin(name, pass);
            }
            break;
//  忘记密码
            case R.id.login_forget_pwd: {
                String name = et_username.getText().toString();
                Intent it = new Intent(LoginActivity.this, ForgetPassActivity.class);
                it.putExtra("username", name);
                startActivity(it);
            }
            break;
        }
    }

    // 发送用户名+密码 实现登录操作
    private void HttpLogin(String name, String pass) {
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
                        ShowLoadingDialog.loadingDialog.cancel();
                        SharedPreferencesUtils.saveNamePass(new User(username, password, nickname), LoginActivity.this);
                        Intent it = new Intent(LoginActivity.this, TestMainActivity.class);
                        it.putExtra("nickname", nickname);
                        startActivity(it);

                        LoginActivity.this.finish();
                    } else {
                        ShowLoadingDialog.loadingDialog.cancel();
                        Toast.makeText(LoginActivity.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("onerror" + "============================");
                ShowLoadingDialog.loadingDialog.cancel();
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

                        SharedPreferencesUtils.saveNamePass(new User(username, password, nickname), LoginActivity.this);
                        Intent it = new Intent(LoginActivity.this, TestMainActivity.class);
                        it.putExtra("nickname", nickname);
                        startActivity(it);

                        LoginActivity.this.finish();
                    } else {

                        Toast.makeText(LoginActivity.this, "fail", Toast.LENGTH_SHORT).show();
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

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
