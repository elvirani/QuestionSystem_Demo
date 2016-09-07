package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yanni.questionsystem_demo.R;

import java.util.List;

import pojo.GridItemListInfo;

/**
 * Created by yanni on 2016/9/1.
 */
public class GridItemAdapter extends BaseAdapter {
    Context context;
    List<GridItemListInfo> list;
    public GridItemAdapter(Context context, List<GridItemListInfo> list) {
        this.context =context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view==null){
            view = View.inflate(context, R.layout.griditemlist_item,null);
            holder = new ViewHolder();
            holder.tv_content = (TextView) view.findViewById(R.id.griditemlist_tv);
            holder.tv_type = (TextView) view.findViewById(R.id.griditemlist_tvtype);
            holder.tv_time = (TextView) view.findViewById(R.id.griditemlist_tvtime);
            view.setTag(holder);
        }else{
            holder  = (ViewHolder) view.getTag();
        }
        holder.tv_content.setText(list.get(position).getContent());
        if (list.get(position).getTypeid()==1){
            holder.tv_type.setText("单选题");
        }else if (list.get(position).getTypeid()==2){
            holder.tv_type.setText("多选题");
        }else if (list.get(position).getTypeid()==3){
            holder.tv_type.setText("判断题");
        }else if (list.get(position).getTypeid()==4){
            holder.tv_type.setText("简答题");
        }
        return view;
    }

    private class ViewHolder {
        TextView tv_content;
        TextView tv_type;
        TextView tv_time;
    }
}
