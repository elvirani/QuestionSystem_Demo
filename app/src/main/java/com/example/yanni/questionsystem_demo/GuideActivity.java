package com.example.yanni.questionsystem_demo;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.GuideAdapter;
import guide.guide_first;
import guide.guide_second;
import guide.guide_third;

@ContentView(value = R.layout.activity_guide)
public class GuideActivity extends AppCompatActivity {
    @ViewInject(value = R.id.main_viewpager)
    private ViewPager viewPager;
    @ViewInject(value = R.id.img_01)
    private ImageView img_01;
    @ViewInject(value = R.id.img_02)
    private ImageView img_02;
    @ViewInject(value = R.id.img_03)
    private ImageView img_03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_guide);
        x.view().inject(this);

        List<Fragment> list = new ArrayList<>();
        list.add(new guide_first());
        list.add(new guide_second());
        list.add(new guide_third());

        viewPager.setAdapter(new GuideAdapter(getSupportFragmentManager(), list));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                setPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setPosition(int position) {
        img_01.setImageResource(R.drawable.page_indicator_unfocused);
        img_02.setImageResource(R.drawable.page_indicator_unfocused);
        img_03.setImageResource(R.drawable.page_indicator_unfocused);
        if (position == 0) {
            img_01.setImageResource(R.drawable.page_indicator_focused);
        } else if (position == 1) {
            img_02.setImageResource(R.drawable.page_indicator_focused);
        } else if (position == 2) {
            img_03.setImageResource(R.drawable.page_indicator_focused);
        }
    }


}
