package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yanni.questionsystem_demo.ItemContentActivity;
import com.example.yanni.questionsystem_demo.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import adapter.GridItemAdapter;
import pojo.GridItemListInfo;
import view.ShowLoadingDialog;

/**
 * Created by yanni on 2016/8/29.
 */
public class frag_collection extends Fragment {
    public List<GridItemListInfo> list;
    GridItemAdapter adapter;
    ListView listview;
    View view;
    SwipeRefreshLayout refreshLayout;

    public frag_collection() {
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ShowLoadingDialog.createLoadingDialog(getContext());
        view = inflater.inflate(R.layout.activity_my_collection, null);
        listview = (ListView) view.findViewById(R.id.collection_listview);
        initUI();
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.collection_refresh);
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
        refreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getContext(), ItemContentActivity.class);
                it.putExtra("collection_lists", (Serializable) list);
                GridItemListInfo info = list.get(position);
                it.putExtra("infoss", info);
                it.putExtra("positionss", position);
                it.putExtra("sizess", list.size());
                it.putExtra("which", 3);
                startActivity(it);
            }
        });

        return view;
    }

    boolean isAdd = false;
    private void initUI() {
        list = new ArrayList<>();
        getMes();
        adapter = new GridItemAdapter(getContext(), list);
        if (!isAdd) {
            TextView textView = new TextView(getContext());
            textView.setText("已加载全部");
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            listview.addFooterView(textView);
            isAdd = true;
        }
        listview.setFooterDividersEnabled(false);
        listview.setAdapter(adapter);
    }

    private void getMes() {
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/mng/store?method=list");
        params.addBodyParameter("userId", String.valueOf(2));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result + "-----------------result");
                try {
                    JSONObject jsonObject = new JSONObject(result);
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
}
