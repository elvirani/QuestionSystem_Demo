package com.example.yanni.questionsystem_demo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import fragment.frag_collection;
import pojo.GridItemListInfo;
import view.ShowLoadingDialog;

@ContentView(value = R.layout.activity_item_content)
public class ItemContentActivity extends AppCompatActivity {
    @ViewInject(value = R.id.mtoolbarback)
    private Toolbar toolbar;
    @ViewInject(R.id.itemcontent_tvtitle)
    private TextView tvtitle;
    @ViewInject(value = R.id.itemcontent_tvcontent)
    private TextView tvcontent;
    @ViewInject(value = R.id.content_scroll)
    private ScrollView scrollView;
    @ViewInject(value = R.id.content_checkboxlinear)
    private LinearLayout linearLayout;
    GridItemListInfo info;
    int position, size, judgePosition;
    int which;
    String url;
    GridItemListInfo itemListInfo;
    Gson gson;
    private List<GridItemListInfo> collection_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ShowLoadingDialog.createLoadingDialog(ItemContentActivity.this);
        which = getIntent().getIntExtra("which", 1);
        if (which == 1) {
            info = (GridItemListInfo) getIntent().getSerializableExtra("info");
            position = getIntent().getIntExtra("position", 0);
            size = getIntent().getIntExtra("size", 0);
        } else if (which == 2) {
            info = (GridItemListInfo) getIntent().getSerializableExtra("infos");
            position = getIntent().getIntExtra("positions", 0);
            size = getIntent().getIntExtra("sizes", 0);
        } else if (which == 3) {
            info = (GridItemListInfo) getIntent().getSerializableExtra("infoss");
            position = getIntent().getIntExtra("positionss", 0);
            size = getIntent().getIntExtra("sizess", 0);
            collection_list = (List<GridItemListInfo>) getIntent().getSerializableExtra("collection_lists");
        }
        judgePosition = position;
        toolbar.setTitle("第" + (position + 1) + "/" + size + "道题");
        toolbar.setBackgroundResource(R.color.actionbar_bg);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        tvtitle.setText(info.getContent());
        url = "http://115.29.136.118:8080/web-question/app/question?method=findone";
        getMes(position, url);
    }

    @Event(value = {R.id.itemcontent_tv_above, R.id.itemcontent_tv_collection, R.id.itemcontent_tv_next}, type = View.OnClickListener.class)
    private void setClick(View view) {
        switch (view.getId()) {
            case R.id.itemcontent_tv_above: {
                url = "http://115.29.136.118:8080/web-question/app/question?method=prev";
                if (position == 0) {
                    judgePosition = position;
                    Toast.makeText(ItemContentActivity.this, "已经是第一题了", Toast.LENGTH_SHORT).show();
                } else {
                    ShowLoadingDialog.createLoadingDialog(ItemContentActivity.this);
                    position--;
                    getMes(position + 1, url);
                    toolbar.setTitle("第" + (position + 1) + "/" + size + "道题");
                    if (which == 1) {
                        tvtitle.setText(GridItemActivity.list.get(position).getContent());
                    } else if (which == 2) {
                        tvtitle.setText(SearchActivity.list.get(position).getContent());
                    } else if (which == 3) {
                        tvtitle.setText(collection_list.get(position).getContent());
                    }
                }
            }
            break;
            case R.id.itemcontent_tv_collection: {
                System.out.println("收藏了");
                judgeCollection();
            }
            break;
            case R.id.itemcontent_tv_next: {
                url = "http://115.29.136.118:8080/web-question/app/question?method=next";
                if (position == size - 1) {
                    Toast.makeText(ItemContentActivity.this, "已经是最后一题了", Toast.LENGTH_SHORT).show();
                } else {
                    ShowLoadingDialog.createLoadingDialog(ItemContentActivity.this);
                    position++;
                    getMes(position - 1, url);
                    toolbar.setTitle("第" + (position + 1) + "/" + size + "道题");
                    if (which == 1) {
                        tvtitle.setText(GridItemActivity.list.get(position).getContent());
                    } else if (which == 2) {
                        tvtitle.setText(SearchActivity.list.get(position).getContent());
                    } else if (which == 3) {
                        tvtitle.setText(collection_list.get(position).getContent());
                    }
                }
            }
            break;
        }
    }

    private void judgeCollection() {
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/mng/store?method=add");
        params.addBodyParameter("userId", String.valueOf(2));
        if (which == 1) {
            params.addBodyParameter("questionId", String.valueOf(GridItemActivity.list.get(position).getId()));
            System.out.println(String.valueOf(GridItemActivity.list.get(position).getId()) + "kkkkkkkkkk");
        } else if (which == 2) {
            params.addBodyParameter("questionId", String.valueOf(SearchActivity.list.get(position).getId()));
        } else if (which == 3) {
            params.addBodyParameter("questionId", String.valueOf(collection_list.get(position).getId()));
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result + "收藏数据");
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    boolean success = jsonObject.getBoolean("success");
                    String reason = jsonObject.getString("reason");
                    if (success) {
                        Toast.makeText(ItemContentActivity.this, reason, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ItemContentActivity.this, reason, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("onerror");
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    // 获取指定题目详情
    private void getMes(int pos, String url) {
        RequestParams params = new RequestParams(url);

        params.addBodyParameter("user_id", String.valueOf(2));
        if (which == 1) {
            params.addBodyParameter("id", String.valueOf(GridItemActivity.list.get(pos).getId()));
        } else if (which == 2) {
            params.addBodyParameter("id", String.valueOf(SearchActivity.list.get(pos).getId()));
        } else if (which == 3) {
            params.addBodyParameter("id", String.valueOf(collection_list.get(pos).getId()));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                        gson = new Gson();
                        itemListInfo = gson.fromJson(result, GridItemListInfo.class);
                        String answer = itemListInfo.getAnswer();
                        int typeid = itemListInfo.getTypeid();
                        checkType(typeid, answer);
                        ShowLoadingDialog.loadingDialog.cancel();
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
                }

        );
    }

    private void checkType(int typeid, String answer) {
        switch (typeid) {
            case 1://单选
            case 2://多选
                linearLayout.removeAllViews();
                scrollView.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                ArrayList<GridItemListInfo.Item> data = gson.fromJson(itemListInfo.getOptions(), new TypeToken<ArrayList<GridItemListInfo.Item>>() {
                }.getType());
                for (int i = 0; i < data.size(); i++) {
                    GridItemListInfo.Item item = data.get(i);
                    CheckBox checkBox = new CheckBox(ItemContentActivity.this);
                    checkBox.setText((i + 1) + ". " + item.title);
                    checkBox.setChecked(item.checked);
                    linearLayout.addView(checkBox);
                }
                break;
            case 3://判断
            case 4://简答
                scrollView.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                tvcontent.setText(answer);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
