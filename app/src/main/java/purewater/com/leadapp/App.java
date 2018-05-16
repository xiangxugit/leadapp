package purewater.com.leadapp;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2018/5/14 0014.
 */

public class App  extends Application{
    // user your appid the key.
    private static final String APP_ID = "1000270";
    // user your appid the key.
    public static final String TAG = "com.xiaomi.mipushdemo";

    private static DemoHandler sHandler = null;
    private static MainActivity sMainActivity = null;



    @Override
    public void onCreate() {
        super.onCreate();

        //
        x.Ext.init(this);
        x.Ext.setDebug(true);

    }


    public static class DemoHandler extends Handler {

        private Context context;

        public DemoHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            String s = (String) msg.obj;

        }
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


    public static DemoHandler getHandler() {
        return sHandler;
    }

    public static void setMainActivity(MainActivity activity) {
        sMainActivity = activity;
    }


}
