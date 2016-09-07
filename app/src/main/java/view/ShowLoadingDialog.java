package view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.yanni.questionsystem_demo.R;

/**
 * Created by yanni on 2016/9/6.
 */
public class ShowLoadingDialog {
    public static Dialog loadingDialog;
    public static Dialog createLoadingDialog(Context context) {

        LayoutInflater inflate = LayoutInflater.from(context);
        View view = inflate.inflate(R.layout.pub_dialog_loading, null);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.loading_linear);
        ImageView img = (ImageView) view.findViewById(R.id.loading_img);

        Animation animation = AnimationUtils.loadAnimation(
                context, R.anim.load_animation);
        img.startAnimation(animation);
        loadingDialog = new Dialog(context, R.style.loading_dialog);

//        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        loadingDialog.show();

        return loadingDialog;
    }

}
