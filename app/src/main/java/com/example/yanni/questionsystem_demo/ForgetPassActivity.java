package com.example.yanni.questionsystem_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(value = R.layout.activity_forget_pass)
public class ForgetPassActivity extends AppCompatActivity {

    @ViewInject(value = R.id.forget_username)
    private EditText et_username;
    @ViewInject(value = R.id.forget_pass)
    private EditText et_pass;
    @ViewInject(value = R.id.forget_passagain)
    private EditText et_passagain;
    @ViewInject(value = R.id.forget_phone)
    private EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_forget_pass);
        x.view().inject(this);

        String username = getIntent().getStringExtra("username");
        et_username.setText(username);

    }

    @Event(value = {R.id.forget_btn}, type = View.OnClickListener.class)
    private void setClick(View view) {
        switch (view.getId()) {
            case R.id.forget_btn: {
                String pass = et_pass.getText().toString();
                String passagain = et_passagain.getText().toString();
                String username = et_username.getText().toString();
                String phone = et_phone.getText().toString();
                if (pass.equals("") || passagain.equals("") || username.equals("") || phone.equals("")) {
                    Toast.makeText(ForgetPassActivity.this, "请输入完整信息", Toast.LENGTH_SHORT).show();
                }
                if (!pass.equals(passagain)) {
                    Toast.makeText(ForgetPassActivity.this, "两次输入的密码不相同", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
