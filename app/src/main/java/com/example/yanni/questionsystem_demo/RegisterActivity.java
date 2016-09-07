package com.example.yanni.questionsystem_demo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(value = R.layout.activity_register)
public class RegisterActivity extends AppCompatActivity {

    @ViewInject(value = R.id.mtoolbarback)
    private Toolbar toolbar;
    @ViewInject(value = R.id.register_username)
    private EditText et_username;
    @ViewInject(value = R.id.register_pass)
    private EditText et_pass;
    @ViewInject(value = R.id.register_nickname)
    private EditText et_nickname;
    @ViewInject(value = R.id.register_phone)
    private EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        //  设置标题栏
        toolbar.setTitle(R.string.btn_registe);
        toolbar.setBackgroundResource(R.color.actionbar_bg);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
    }

// 点完注册按钮   也无法向服务器存储数据。
    @Event(value = R.id.register_btn, type = View.OnClickListener.class)
    private void setClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn: {
                String username = et_username.getText().toString();
                String pass = et_pass.getText().toString();
                String nickname = et_nickname.getText().toString();
                String phone = et_phone.getText().toString();

                if (username.equals("") || pass.equals("") || nickname.equals("") || phone.equals("")) {
                    Toast.makeText(RegisterActivity.this, "请输入完整的信息！", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < username.length(); i++) {
                        char c = username.charAt(i);
                        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                            if (username.length() >= 3) {
                                HttpCon(username, pass, nickname, phone);
                            } else {
                                Toast.makeText(RegisterActivity.this, "用户名至少3位及以上！", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "用户名为字母！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
            break;
        }
    }

    private void HttpCon(String username, String pass, String nickname, String phone) {
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/registe");
        params.addBodyParameter("username", username);
        params.addBodyParameter("password", pass);
        params.addBodyParameter("nickname", nickname);
        params.addBodyParameter("telephone", phone);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String success = jsonObject.getString("success");
                    if (success.equals("true")){
                        Toast.makeText(RegisterActivity.this, "success", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this, "fail", Toast.LENGTH_SHORT).show();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
            }break;
        }
        return super.onOptionsItemSelected(item);
    }
}
