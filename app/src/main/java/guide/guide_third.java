package guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yanni.questionsystem_demo.GuideActivity;
import com.example.yanni.questionsystem_demo.LoginActivity;
import com.example.yanni.questionsystem_demo.R;

/**
 * Created by yanni on 2016/8/29.
 */
public class guide_third extends Fragment implements View.OnClickListener{
    public guide_third() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third,null);
        TextView tv = (TextView) view.findViewById(R.id.third_tv);
        tv.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getContext(), LoginActivity.class));
//       带有fragment 的activity  关闭当前页面
        getActivity().finish();
    }
}
