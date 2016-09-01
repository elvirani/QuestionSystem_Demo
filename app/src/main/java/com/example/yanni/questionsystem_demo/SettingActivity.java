package com.example.yanni.questionsystem_demo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(value = R.layout.activity_setting)
public class SettingActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    EditText et;
    @ViewInject(value = R.id.setting_nicknametvshow)
    private TextView nicknametv;
    @ViewInject(value = R.id.mtoolbar)
    private Toolbar toolbar;
    @ViewInject(value = R.id.setting_logintvshow)
    private TextView loginTvshow;
    @ViewInject(value = R.id.setting_showtvshow)
    private TextView showTvshow;
    @ViewInject(value = R.id.setting_login_checkbox)
    private CheckBox login_checkBox;
    @ViewInject(value = R.id.setting_show_checkbox)
    private CheckBox show_checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_setting);
        x.view().inject(this);

        toolbar.setTitle(R.string.menu_setting);
        toolbar.setBackgroundResource(R.color.actionbar_bg);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar); // 设置返回键可用
    }

    @Event(value = {R.id.setting_nickname, R.id.setting_login, R.id.setting_show_checkbox, R.id.setting_exit, R.id.setting_clear, R.id.setting_us}, type = View.OnClickListener.class)
    private void setClick(View view) {

        switch (view.getId()) {
            case R.id.setting_nickname: {
                showDialogNickname();
            }
            break;
            case R.id.setting_login: {
                boolean checked = login_checkBox.isChecked();
                if (checked) {
                    login_checkBox.setChecked(false);
                } else {
                    login_checkBox.setChecked(true);
                }
            }
            break;
            case R.id.setting_show_checkbox: {
                boolean checked = show_checkBox.isChecked();
                if (checked) {
                    show_checkBox.setChecked(false);
                } else {
                    show_checkBox.setChecked(true);
                }
            }
            break;
            case R.id.setting_exit: {
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                finish();
            }
            break;
            case R.id.setting_clear: {
                Toast.makeText(SettingActivity.this, "执行清除缓存的操作", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.setting_us: {
                Toast.makeText(SettingActivity.this, "启动关于我们的界面...", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    @Event(value = {R.id.setting_login_checkbox, R.id.setting_show_checkbox}, type = CompoundButton.OnCheckedChangeListener.class)
    private void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.setting_login_checkbox: {
                if (isChecked) {
                    loginTvshow.setText(R.string.pref_item_autologin_summary_on);
                } else {
                    loginTvshow.setText(R.string.pref_item_autologin_summary_off);
                }
            }
            break;
            case R.id.setting_show_checkbox: {
                if (isChecked) {
                    showTvshow.setText(R.string.pref_item_show_img_summary_on);
                } else {
                    showTvshow.setText(R.string.pref_item_show_img_summary_off);
                }
            }
            break;
        }

    }

    private void showDialogNickname() {
        LayoutInflater inflater = LayoutInflater.from(SettingActivity.this);
        View view = inflater.inflate(R.layout.dialog, null);
        TextView tv01 = (TextView) view.findViewById(R.id.dialog_cancel);
        TextView tv02 = (TextView) view.findViewById(R.id.dialog_ok);
        et = (EditText) view.findViewById(R.id.dialog_et);
        String nickname = getIntent().getStringExtra("nickname");
        et.setText(nickname);

        builder = new AlertDialog.Builder(SettingActivity.this);
        final AlertDialog alertDialog = builder.create();

        tv01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        tv02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = et.getText().toString();
                nicknametv.setText(s);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }
}
