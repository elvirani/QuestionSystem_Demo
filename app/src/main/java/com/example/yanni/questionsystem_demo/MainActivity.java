package com.example.yanni.questionsystem_demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

// 判断app 是否是第一次打开运行  SharedPreferences存储数据

        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_APPEND);

        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun",true);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (isFirstRun){
            Log.i("info","第一次运行");
            editor.putBoolean("isFirstRun",false);
            editor.commit();
            startActivity(new Intent(MainActivity.this,GuideActivity.class));

        }else{
            Log.i("info","不是第一次运行");
            startActivity(new Intent(MainActivity.this,WelcomeActivity.class));

        }
        finish();

    }


}
