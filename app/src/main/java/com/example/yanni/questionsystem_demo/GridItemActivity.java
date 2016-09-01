package com.example.yanni.questionsystem_demo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

@ContentView(value = R.layout.activity_grid_item)
public class GridItemActivity extends AppCompatActivity {

    @ViewInject(value = R.id.mtoolbar)
    private Toolbar toolbar;
    @ViewInject(value = R.id.griditem_listview)
    private ListView listView;
   public static List<GridItemListInfo> list;
    int id, id1;
    GridItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_grid_item);
        x.view().inject(this);

        TypeKnowledge type = (TypeKnowledge) getIntent().getSerializableExtra("type");
        String name = type.getName();
        id = type.getId();

        toolbar.setTitle(name);
        toolbar.setBackgroundResource(R.color.actionbar_bg);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar); // 设置返回键可用

        list = new ArrayList<>();
        getMes();
        adapter = new GridItemAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GridItemListInfo info = list.get(position);
                Intent it = new Intent(GridItemActivity.this, ItemContentActivity.class);
                it.putExtra("info", info);
                it.putExtra("position", position);
                it.putExtra("size", list.size());
                startActivity(it);
            }
        });
    }

    private void getMes() {
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=list");

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//               System.out.println(result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("content");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        int cataid = jsonObject1.getInt("cataid");
                        if (cataid == id) {
                            String content = jsonObject1.getString("content");
                            int typeid = jsonObject1.getInt("typeid");
                            id1 = jsonObject1.getInt("id");
                            list.add(new GridItemListInfo(content, typeid, id1));
                            adapter.notifyDataSetChanged();
                        }
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
}
