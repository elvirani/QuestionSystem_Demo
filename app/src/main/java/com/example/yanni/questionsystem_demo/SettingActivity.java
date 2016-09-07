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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

import utils.SharedPreferencesUtils;

@ContentView(value = R.layout.activity_setting)
public class SettingActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    EditText et;
    @ViewInject(value = R.id.setting_nicknametvshow)
    private TextView nicknametv;
    @ViewInject(value = R.id.mtoolbarback)
    private Toolbar toolbar;
    @ViewInject(value = R.id.setting_logintvshow)
    private TextView loginTvshow;
    @ViewInject(value = R.id.setting_showtvshow)
    private TextView showTvshow;
    @ViewInject(value = R.id.setting_login_checkbox)
    private CheckBox login_checkBox;
    @ViewInject(value = R.id.setting_show_checkbox)
    private CheckBox show_checkBox;
    String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        toolbar.setTitle(R.string.menu_setting);
        toolbar.setBackgroundResource(R.color.actionbar_bg);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        nickname = getIntent().getStringExtra("nickname");
        nicknametv.setText(nickname);
        login_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//               默认自动登录开启
                    loginTvshow.setText(R.string.pref_item_autologin_summary_on);
                } else {
                    loginTvshow.setText(R.string.pref_item_autologin_summary_off);
                }
                SharedPreferencesUtils.setState(SettingActivity.this, isChecked);

            }
        });
        login_checkBox.setChecked(SharedPreferencesUtils.getState(SettingActivity.this));

        show_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showTvshow.setText(R.string.pref_item_show_img_summary_on);
                } else {
                    showTvshow.setText(R.string.pref_item_show_img_summary_off);
                }
                SharedPreferencesUtils.setImgState(SettingActivity.this, isChecked);
            }
        });
        show_checkBox.setChecked(SharedPreferencesUtils.getImgState(SettingActivity.this));
    }

    @Event(value = {R.id.setting_nickname, R.id.setting_login, R.id.setting_show_checkbox, R.id.setting_exit, R.id.setting_clear, R.id.setting_us}, type = View.OnClickListener.class)
    private void setClick(View view) {

        switch (view.getId()) {
            case R.id.setting_nickname: {
                showDialogNickname();
            }
            break;
//            case R.id.setting_login: {
//                boolean checked = login_checkBox.isChecked();
//                if (checked) {
//                    login_checkBox.setChecked(false);
//                } else {
//                    login_checkBox.setChecked(true);
//                }
//                SharedPreferencesUtils.setState(SettingActivity.this, checked);
//            }
//            break;
//            case R.id.setting_show_checkbox: {
//                boolean checked = show_checkBox.isChecked();
//                if (checked) {
//                    show_checkBox.setChecked(false);
//                } else {
//                    show_checkBox.setChecked(true);
//                }
//            }
//            break;
            case R.id.setting_exit: {
                SharedPreferencesUtils.setState(SettingActivity.this, false);
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

    private void showDialogNickname() {
        LayoutInflater inflater = LayoutInflater.from(SettingActivity.this);
        View view = inflater.inflate(R.layout.dialog, null);
        TextView tv01 = (TextView) view.findViewById(R.id.dialog_cancel);
        TextView tv02 = (TextView) view.findViewById(R.id.dialog_ok);
        et = (EditText) view.findViewById(R.id.dialog_et);
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
                if (SharedPreferencesUtils.setName(SettingActivity.this, s)) {
                    nicknametv.setText(s);
                    Toast.makeText(SettingActivity.this, "设置成功!", Toast.LENGTH_SHORT).show();
                }

                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                startActivity(new Intent(SettingActivity.this, TestMainActivity.class));
                finish();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
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
