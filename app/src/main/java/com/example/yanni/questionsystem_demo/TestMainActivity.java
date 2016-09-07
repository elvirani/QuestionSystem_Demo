package com.example.yanni.questionsystem_demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.GuideAdapter;
import adapter.ListDrawerAdapter;
import fragment.frag_achievement;
import fragment.frag_collection;
import fragment.frag_gridview;
import guide.guide_first;
import guide.guide_second;
import guide.guide_third;
import pojo.ListItemInfo;
import utils.SharedPreferencesUtils;
import view.MyViewPager;

@ContentView(value = R.layout.activity_test_main)
public class TestMainActivity extends AppCompatActivity {

    @ViewInject(value = R.id.mtoolbar)
    private Toolbar toolbar;
    @ViewInject(value = R.id.drawerlayout_viewpager)
    private MyViewPager viewPager;
    @ViewInject(value = R.id.drawerlayout)
    private DrawerLayout drawerLayout;
    @ViewInject(value = R.id.test_listviewdrawer)
    private ListView listViewDrawer;
    @ViewInject(value = R.id.test_tv_nickname)
    private TextView tv_nickname;

    private ActionBarDrawerToggle mDrawerToggle;
    List<ListItemInfo> listItem;
    ListDrawerAdapter drawerAdapter;
    String putNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setToolBar();

        List<Fragment> list = new ArrayList<>();
        list.add(new frag_gridview());
        list.add(new frag_achievement());
        list.add(new frag_collection());

        viewPager.setAdapter(new GuideAdapter(getSupportFragmentManager(), list));
//        viewPager.setScrollble(false);
//设置drawlayout布局
        putNickname = getIntent().getStringExtra("nickname");
        tv_nickname.setText(SharedPreferencesUtils.getName(TestMainActivity.this, putNickname));

        listItem = new ArrayList<>();
        listItem.add(new ListItemInfo(R.drawable.home_nav_icon01, R.string.menu_exercise));
        listItem.add(new ListItemInfo(R.drawable.home_nav_icon03, R.string.menu_find));
        listItem.add(new ListItemInfo(R.drawable.home_nav_icon04, R.string.menu_achievement));
        listItem.add(new ListItemInfo(R.drawable.home_nav_icon03, R.string.menu_fav));
        drawerAdapter = new ListDrawerAdapter(TestMainActivity.this, listItem);
        listViewDrawer.setAdapter(drawerAdapter);
        listViewDrawer.post(new Runnable() {
            @Override
            public void run() {
                listViewDrawer.getChildAt(0).setBackgroundResource(R.color.list_item_bg_press);
            }
        });
    }

    private void setToolBar() {
        toolbar.setTitle(R.string.menu_exercise);
        toolbar.setBackgroundResource(R.color.actionbar_bg);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        //  设置 左上角有个图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        openOrCloseToggle();
    }

    //打开或关闭侧滑菜单
    private void openOrCloseToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(TestMainActivity.this, drawerLayout, R.string.menu_exercise, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                System.out.println("打开了侧滑菜单");
                listViewDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (position == 0) {
                            viewPager.setCurrentItem(0);
                            toolbar.setTitle(R.string.menu_exercise);
                            listViewDrawer.post(new Runnable() {
                                @Override
                                public void run() {
                                    listViewDrawer.getChildAt(0).setBackgroundResource(R.color.list_item_bg_press);
                                }
                            });
                        } else if (position == 1) {
                            startActivity(new Intent(TestMainActivity.this, SearchActivity.class));
                            setsta();
                        } else if (position == 2) {
                            viewPager.setCurrentItem(1);
                            toolbar.setTitle(R.string.menu_achievement);
                            setsta();
                        } else if (position == 3) {
                            viewPager.setCurrentItem(2);
                            toolbar.setTitle(R.string.menu_fav);
                            setsta();
                        }
                        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                    }
                });
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                System.out.println("关闭了侧滑菜单");
            }
        };
        // 使得箭头。三道杠 与 抽屉拉合保持同步状态
        mDrawerToggle.syncState();
        //  设置侧滑的监听事件
        drawerLayout.setDrawerListener(mDrawerToggle);

    }

    public void setsta() {
        listViewDrawer.post(new Runnable() {
            @Override
            public void run() {
                listViewDrawer.getChildAt(0).setBackgroundResource(R.color.list_item_bg_normal);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// 判断点击事件是否来自于app图标
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.sousuo: {
                startActivity(new Intent(TestMainActivity.this, SearchActivity.class));
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 点击返回键 退出drawlayout
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Event(value = {R.id.drawerlayout_tv_setting, R.id.drawerlayout_tv_exit}, type = View.OnClickListener.class)
    private void setClick(View view) {
        switch (view.getId()) {
            case R.id.drawerlayout_tv_setting: {
                Intent it = new Intent(TestMainActivity.this, SettingActivity.class);
                it.putExtra("nickname", tv_nickname.getText().toString());
                startActivity(it);
                finish();
            }
            break;
            case R.id.drawerlayout_tv_exit: {
                TestMainActivity.this.finish();
            }
            break;
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
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
