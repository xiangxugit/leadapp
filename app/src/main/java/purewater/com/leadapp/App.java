package purewater.com.leadapp;

import android.app.Application;
import org.xutils.x;

/**
 * Created by Administrator on 2018/5/14 0014.
 */

public class App  extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //
        x.Ext.init(this);
        x.Ext.setDebug(true);

    }
}
