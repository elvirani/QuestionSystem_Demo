package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yanni.questionsystem_demo.R;
import com.example.yanni.questionsystem_demo.TestMainActivity;

import java.util.List;

import pojo.ListItemInfo;

/**
 * Created by yanni on 2016/9/1.
 */
public class ListDrawerAdapter extends BaseAdapter {
    Context context;
    List<ListItemInfo> listItem;
    public ListDrawerAdapter(Context context, List<ListItemInfo> listItem) {
        this.listItem = listItem;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view==null){
            view = View.inflate(context, R.layout.listviewdrawer_item,null);
            holder = new ViewHolder();
            holder.tv = (TextView) view.findViewById(R.id.listviewdrawer_tv_title);
            holder.img = (ImageView) view.findViewById(R.id.listviewdrawer_img);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.img.setImageResource(listItem.get(position).getImgId());
        holder.tv.setText(listItem.get(position).getTitleId());
        return view;
    }

    private class ViewHolder {
        TextView tv;
        ImageView img;
    }
}
