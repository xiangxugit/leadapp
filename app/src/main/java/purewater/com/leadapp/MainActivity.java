package purewater.com.leadapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.app.ProgressDialog;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;

import java.io.File;
import org.xutils.x;

import purewater.com.leadapp.utils.RestUtils;

import static android.webkit.MimeTypeMap.getFileExtensionFromUrl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView intall;
    private ProgressDialog progressDialog;//进度条
    private Callback.Cancelable cancelable;//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    public void initView(){
          intall = (TextView) findViewById(R.id.intall);
          intall.setOnClickListener(this);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle(getString(R.string.download));
        //设置信息
        progressDialog.setMessage(getString(R.string.dowanloading));
        //设置信息格式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.intall:
                intall.setVisibility(View.GONE);
                String url = RestUtils.getUrl(RestUtils.LOGIN);

                doDownload(url);
                break;
        }
    }


    public void doDownload(String url){
        RequestParams requestParams = new RequestParams(url);
        requestParams.setAutoResume(true);//设置为断点续传
        requestParams.setAutoRename(false);
        String name = getFileExtensionFromUrl(url);
        requestParams.setSaveFilePath("/sdcard/xutils/"+name);
        requestParams.setExecutor(new PriorityExecutor(2, true));
        requestParams.setCancelFast(true);
        cancelable = x.http().get(requestParams, new Callback.ProgressCallback<File>()  {
            @Override
            public void onCancelled(CancelledException arg0) {
                Log.e("tag", "取消"+Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                Log.e("tag", "onError: 失败"+Thread.currentThread().getName());
                progressDialog.dismiss();
            }

            @Override
            public void onFinished() {
                Log.e("tag", "完成,每次取消下载也会执行该方法"+Thread.currentThread().getName());
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(File arg0) {
                Log.e("tag", "下载成功的时候执行"+Thread.currentThread().getName());


            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                if (isDownloading) {
                    progressDialog.setProgress((int) (current*100/total));
                    Log.e("tag", "下载中,会不断的进行回调:"+Thread.currentThread().getName());
                }
            }

            @Override
            public void onStarted() {
                Log.e("tag", "开始下载的时候执行"+Thread.currentThread().getName());
                progressDialog.show();
            }

            @Override
            public void onWaiting() {
                Log.e("tag", "等待,在onStarted方法之前执行"+Thread.currentThread().getName());
            }
        });
    }

}
