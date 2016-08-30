package application;

import android.app.Application;

import org.xutils.x;

/**
 * Created by yanni on 2016/8/29.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
//        x.Ext.setDebug(true);
    }
}
