package com.example.yanni.questionsystem_demo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

@ContentView(value = R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @ViewInject(value = R.id.login_username)
    private EditText et_username;
    @ViewInject(value = R.id.login_pass)
    private EditText et_pass;
    Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        x.view().inject(this);
    }

    @Event(value = {R.id.login_register, R.id.login_btn, R.id.login_forget_pwd}, type = View.OnClickListener.class)
    private void setClick(View v) {
        switch (v.getId()) {
            case R.id.login_register: {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
            break;
            case R.id.login_btn: {
                String name = et_username.getText().toString();
                String pass = et_pass.getText().toString();
                createLoadingDialog();
                HttpLogin(name, pass);
            }
            break;
            case R.id.login_forget_pwd: {
                String name = et_username.getText().toString();
                Intent it = new Intent(LoginActivity.this, ForgetPassActivity.class);
                it.putExtra("username", name);
                startActivity(it);
            }
            break;
        }
    }

    private void HttpLogin(String name, String pass) {
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/login");
        params.addBodyParameter("username", name);
        params.addBodyParameter("password", pass);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
//                    JSONObject jsonObject1 = jsonObject.getJSONObject("user");
//                    String username = jsonObject1.getString("username");
//                    System.out.println(username + "================");

                    String success = jsonObject.getString("success");
                    if (success.equals("true")) {
                        loadingDialog.cancel();
                        startActivity(new Intent(LoginActivity.this, TestMainActivity.class));
                    } else {
                        loadingDialog.cancel();
                        Toast.makeText(LoginActivity.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    public Dialog createLoadingDialog() {

        LayoutInflater inflate = LayoutInflater.from(LoginActivity.this);
        View view = inflate.inflate(R.layout.pub_dialog_loading, null);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.loading_linear);
        ImageView img = (ImageView) view.findViewById(R.id.loading_img);

        Animation animation = AnimationUtils.loadAnimation(
                LoginActivity.this, R.anim.load_animation);
        img.startAnimation(animation);
        loadingDialog = new Dialog(LoginActivity.this, R.style.loading_dialog);

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        loadingDialog.show();

        return loadingDialog;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
