package adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yanni.questionsystem_demo.R;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.List;

import pojo.TypeKnowledge;

/**
 * Created by yanni on 2016/8/30.
 */
public class GridAdapter extends BaseAdapter {
    Context context;
    List<TypeKnowledge> list;
LayoutInflater inflater;
    public GridAdapter(Context context, List<TypeKnowledge> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.gridview_item,null);
            holder.textView = (TextView) view.findViewById(R.id.grid_title);
            holder.img = (ImageView) view.findViewById(R.id.grid_icon);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(list.get(i).getName());
        getPicture(i,holder);
        return view;
    }

    private void getPicture(int i, ViewHolder holder) {
        String url = "http://115.29.136.118/web-question/"+list.get(i).getIcon();
        x.image().bind(holder.img, url);
    }

    private class ViewHolder {
        TextView textView;
        ImageView img;
    }
}
