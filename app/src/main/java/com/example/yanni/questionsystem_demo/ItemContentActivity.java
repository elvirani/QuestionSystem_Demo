package com.example.yanni.questionsystem_demo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;

import pojo.GridItemListInfo;

@ContentView(value = R.layout.activity_item_content)
public class ItemContentActivity extends AppCompatActivity {
    @ViewInject(value = R.id.mtoolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.itemcontent_tvtitle)
    private TextView tvtitle;
    @ViewInject(value = R.id.itemcontent_tvcontent)
    private TextView tvcontent;
    GridItemListInfo info;
    int position, size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_item_content);
        x.view().inject(this);

        info = (GridItemListInfo) getIntent().getSerializableExtra("info");
        position = getIntent().getIntExtra("position", 0);
        size = getIntent().getIntExtra("size", 0);

        toolbar.setTitle("第" + (position + 1) + "/" + size + "道题");
        toolbar.setBackgroundResource(R.color.actionbar_bg);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar); // 设置返回键可用

        tvtitle.setText(info.getContent());
        getMes();
    }

    @Event(value = {R.id.itemcontent_tv_above, R.id.itemcontent_tv_collection, R.id.itemcontent_tv_next}, type = View.OnClickListener.class)
    private void setClick(View view) {
        switch (view.getId()) {
            case R.id.itemcontent_tv_above: {
                if (position == 0) {
                    Toast.makeText(ItemContentActivity.this, "已经是第一题了", Toast.LENGTH_SHORT).show();
                } else {
                    position--;
                    toolbar.setTitle("第" + (position + 1) + "/" + size + "道题");
                    tvtitle.setText(GridItemActivity.list.get(position).getContent());
                }

            }
            break;
            case R.id.itemcontent_tv_collection: {

            }
            break;
            case R.id.itemcontent_tv_next: {
                if (position == size - 1) {
                    Toast.makeText(ItemContentActivity.this, "已经是最后一题了", Toast.LENGTH_SHORT).show();
                } else {
                    position++;
                    toolbar.setTitle("第" + (position + 1) + "/" + size + "道题");
                    tvtitle.setText(GridItemActivity.list.get(position).getContent());
                }
            }
            break;
        }
    }

    private void getMes() {
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=findone");

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result + "success");
                try {
                    JSONObject object = new JSONObject(result);
                    int id = object.getInt("id");
                    if (id == info.getId()) {
                        String answer = object.getString("answer");
                        tvcontent.setText(answer);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("onError");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                System.out.println("onCancelled");
            }

            @Override
            public void onFinished() {
                System.out.println("onFinished");
            }
        });
    }
}
