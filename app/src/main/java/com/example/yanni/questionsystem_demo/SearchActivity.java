package com.example.yanni.questionsystem_demo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

import adapter.GridItemAdapter;
import pojo.GridItemListInfo;
import view.ShowLoadingDialog;

@ContentView(value = R.layout.activity_search)
public class SearchActivity extends AppCompatActivity {
    @ViewInject(value = R.id.search_linear)
    private LinearLayout linearLayout;
    @ViewInject(value = R.id.mtoolbarback)
    private Toolbar toolbar;
    @ViewInject(value = R.id.search_et)
    private EditText et;
    @ViewInject(value = R.id.search_listview)
    private ListView listView;
    @ViewInject(value = R.id.search_tvall)
    private TextView tvall;
    public static List<GridItemListInfo> list;
    GridItemAdapter adapter;
    String content;
    int typeid, ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        toolbar.setTitle("题目查找");
        toolbar.setBackgroundResource(R.color.actionbar_bg);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GridItemListInfo info = list.get(position);
                Intent it = new Intent(SearchActivity.this, ItemContentActivity.class);
                it.putExtra("infos", info);
                it.putExtra("positions", position);
                it.putExtra("sizes", list.size());
                it.putExtra("which", 2);
                startActivity(it);
            }
        });
    }

    @Event(value = R.id.search_btn, type = View.OnClickListener.class)
    private void setClick(View view) {
        switch (view.getId()) {
            case R.id.search_btn: {
                ShowLoadingDialog.createLoadingDialog(SearchActivity.this);
                String s = et.getText().toString();
                if (s.equals("")) {
                    ShowLoadingDialog.loadingDialog.cancel();
                    listView.setVisibility(View.GONE);
                    tvall.setText("已加载全部");
                } else {
                    listView.setVisibility(View.VISIBLE);
                    tvall.setVisibility(View.GONE);
                    list = new ArrayList<>();
                    getMes(s);
                    adapter = new GridItemAdapter(this, list);
                    TextView textView = new TextView(SearchActivity.this);
                    textView.setText("已加载全部");
                    textView.setGravity(Gravity.CENTER_HORIZONTAL);
                    listView.addFooterView(textView);
                    listView.setFooterDividersEnabled(false);
                    listView.setAdapter(adapter);
                }

            }
            break;
        }
    }

    private void getMes(String s) {
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=list");
        params.addBodyParameter("questionName", s);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("content");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        content = jsonObject1.getString("content");
                        typeid = jsonObject1.getInt("typeid");
                        ids = jsonObject1.getInt("id");
                        String answer = jsonObject1.getString("answer");
                        long pubTime = jsonObject1.getLong("pubTime");
                        int cataid = jsonObject1.getInt("cataid");
                        list.add(new GridItemListInfo(answer, pubTime, cataid, typeid, ids, content));
                        adapter.notifyDataSetChanged();
                        ShowLoadingDialog.loadingDialog.cancel();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
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
