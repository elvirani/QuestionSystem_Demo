package guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yanni.questionsystem_demo.R;

/**
 * Created by yanni on 2016/8/29.
 */
public class guide_first extends Fragment {
    public guide_first() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second,null);
        view.setBackgroundResource(R.drawable.guide_1);
        return view;
    }
}
