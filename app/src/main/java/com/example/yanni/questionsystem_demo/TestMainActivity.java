package com.example.yanni.questionsystem_demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

import adapter.GridAdapter;
import adapter.ListDrawerAdapter;
import pojo.ListItemInfo;
import pojo.TypeKnowledge;

@ContentView(value = R.layout.activity_test_main)
public class TestMainActivity extends AppCompatActivity {

    @ViewInject(value = R.id.test_gridview)
    private GridView gridView;
    List<TypeKnowledge> list;
    private GridAdapter adapter;
    @ViewInject(value = R.id.mtoolbar)
    private Toolbar toolbar;
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
//        setContentView(R.layout.activity_test_main);
        x.view().inject(this);

        putNickname = getIntent().getStringExtra("nickname");
        tv_nickname.setText(putNickname);
        //  5.0 之后更适用 ToolBar
//        toolbar = (Toolbar) findViewById(R.id.mtoolbar);
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        toolbar.setTitle(R.string.menu_exercise);
        toolbar.setBackgroundResource(R.color.actionbar_bg);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar); // 设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        //  设置 左上角有个图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        openOrCloseToggle();

        list = new ArrayList<>();
        getMes();
        adapter = new GridAdapter(TestMainActivity.this, list);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("-----grid-----------");
                TypeKnowledge typeKnowledge = list.get(position);
                Intent it = new Intent(TestMainActivity.this, GridItemActivity.class);
                it.putExtra("type", typeKnowledge);
                startActivity(it);
            }
        });

        listItem = new ArrayList<>();
        listItem.add(new ListItemInfo(R.drawable.home_nav_icon01, R.string.menu_exercise));
        listItem.add(new ListItemInfo(R.drawable.home_nav_icon03, R.string.menu_find));
        listItem.add(new ListItemInfo(R.drawable.home_nav_icon04, R.string.menu_achievement));
        listItem.add(new ListItemInfo(R.drawable.home_nav_icon03, R.string.menu_fav));
        drawerAdapter = new ListDrawerAdapter(TestMainActivity.this, listItem);
        listViewDrawer.setAdapter(drawerAdapter);
    }

    private void openOrCloseToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(TestMainActivity.this, drawerLayout, R.string.menu_exercise, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                System.out.println("打开了侧滑菜单");
                listViewDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println("-----listviewdrawer-----------");
                        if (position == 0) {
                            startActivity(new Intent(TestMainActivity.this, TestMainActivity.class));
                            finish();
                        } else if (position == 1) {
                            startActivity(new Intent(TestMainActivity.this, SearchSubjectActivity.class));
                        } else if (position == 2) {
                            startActivity(new Intent(TestMainActivity.this, SearchSubjectActivity.class));
                        } else if (position == 3) {
                            startActivity(new Intent(TestMainActivity.this, SearchSubjectActivity.class));
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

    private void getMes() {
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/catalog?method=list");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("-------------onSuccess---------");
                try {
                    JSONArray array = new JSONArray(result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String icon = jsonObject.getString("icon");
                        String name = jsonObject.getString("name");
                        list.add(new TypeKnowledge(id, icon, name));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                System.out.println("------------onError-------------");
            }

            @Override
            public void onCancelled(CancelledException cex) {
//                System.out.println("------------onCancelled-------------");
            }

            @Override
            public void onFinished() {
//                System.out.println("------------onFinished-------------");
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
        switch (item.getItemId()) {
            case R.id.sousuo:
                Toast.makeText(TestMainActivity.this, "跳转页面", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Event(value = {R.id.drawerlayout_tv_setting, R.id.drawerlayout_tv_exit}, type = View.OnClickListener.class)
    private void setClick(View view) {
        switch (view.getId()) {
            case R.id.drawerlayout_tv_setting: {
                Intent it = new Intent(TestMainActivity.this, SettingActivity.class);
                it.putExtra("nickname", putNickname);
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


}
