package com.example.yanni.questionsystem_demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import adapter.GridItemAdapter;
import pojo.GridItemListInfo;
import pojo.TypeKnowledge;
import view.ShowLoadingDialog;

@ContentView(value = R.layout.activity_grid_item)
public class GridItemActivity extends AppCompatActivity {

    @ViewInject(value = R.id.mtoolbarback)
    private Toolbar toolbar;
    @ViewInject(value = R.id.griditem_listview)
    private ListView listView;
    public static List<GridItemListInfo> list;
    GridItemAdapter adapter;
    int gridposition;
    @ViewInject(value = R.id.griditem_refresh)
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ShowLoadingDialog.createLoadingDialog(GridItemActivity.this);
        TypeKnowledge type = (TypeKnowledge) getIntent().getSerializableExtra("type");
        String name = type.getName();
        gridposition = getIntent().getIntExtra("gridposition", 0);
        System.out.println(gridposition + "))))))))))))))))))))))))))))))");

        toolbar.setTitle(name);
        toolbar.setBackgroundResource(R.color.actionbar_bg);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);


        refreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);

        initUI();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initUI();
                        refreshLayout.setRefreshing(false);

                    }
                }, 2000);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GridItemListInfo info = list.get(position);
                Intent it = new Intent(GridItemActivity.this, ItemContentActivity.class);
                it.putExtra("info", info);
                it.putExtra("position", position);
                it.putExtra("size", list.size());
                it.putExtra("which", 1);
                startActivity(it);
            }
        });
    }

    boolean isAdd = false;

    private void initUI() {
        list = new ArrayList<>();
        getMes();
        adapter = new GridItemAdapter(this, list);
        if (!isAdd) {
            TextView textView = new TextView(GridItemActivity.this);
            textView.setText("已加载全部");
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            listView.addFooterView(textView);
            isAdd = true;
        }
        listView.setFooterDividersEnabled(false);
        listView.setAdapter(adapter);
    }

    private void getMes() {
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=list");
        params.addBodyParameter("catalogId", String.valueOf(gridposition + 1));
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int page = jsonObject.getInt("page");
                    if (page==1){
                        ShowLoadingDialog.loadingDialog.cancel();
                    }
                    JSONArray jsonArray = jsonObject.getJSONArray("content");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String answer = jsonObject1.getString("answer");
                        long pubTime = jsonObject1.getLong("pubTime");
                        int cataid = jsonObject1.getInt("cataid");
                        int typeid = jsonObject1.getInt("typeid");
                        int id = jsonObject1.getInt("id");
                        String content = jsonObject1.getString("content");
                        list.add(new GridItemListInfo(answer, pubTime, cataid, typeid, id, content));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("oneeeeeeer");
                ShowLoadingDialog.loadingDialog.cancel();
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
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
