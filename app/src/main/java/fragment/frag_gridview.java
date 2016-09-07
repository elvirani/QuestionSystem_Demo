package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.yanni.questionsystem_demo.GridItemActivity;
import com.example.yanni.questionsystem_demo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.GridAdapter;
import pojo.TypeKnowledge;
import view.ShowLoadingDialog;

/**
 * Created by yanni on 2016/9/4.
 */
public class frag_gridview extends Fragment {
    GridView gridView;
    List<TypeKnowledge> list;
    private GridAdapter adapter;
    SwipeRefreshLayout refreshLayout;

    public frag_gridview() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gridfragementlayout, null);
        gridView = (GridView) view.findViewById(R.id.test_gridview);

        ShowLoadingDialog.createLoadingDialog(getContext());
        list = new ArrayList<>();

        initUI();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TypeKnowledge typeKnowledge = list.get(position);
                Intent it = new Intent(getContext(), GridItemActivity.class);
                it.putExtra("type", typeKnowledge);
                it.putExtra("gridposition", position);
                startActivity(it);
            }
        });

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.grid_refresh);
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
        return view;
    }

    private void initUI() {
        getMes();
        adapter = new GridAdapter(getContext(), list);
        gridView.setAdapter(adapter);
    }

    private void getMes() {
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/catalog?method=list");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray array = new JSONArray(result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String icon = jsonObject.getString("icon");
                        String name = jsonObject.getString("name");
                        list.add(new TypeKnowledge(id, icon, name));
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
