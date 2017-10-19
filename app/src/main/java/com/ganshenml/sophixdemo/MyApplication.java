package com.ganshenml.sophixdemo;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Created by Administrator on 2017/10/17.
 */

public class MyApplication extends Application {
    public static final String TAG = "MyApplication";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        initSophix();
    }

    private void initSophix() {
// initialize最好放在attachBaseContext最前面，初始化直接在Application类里面，切勿封装到其他类
        SophixManager.getInstance().setContext(this)
                .setAppVersion(Common.getAppVersionName(this))
//                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            Toast.makeText(getApplicationContext(), "补丁加载成功", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "补丁加载成功");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                            Toast.makeText(getApplicationContext(), "新补丁生效需要重启.", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "新补丁生效需要重启.");
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            Toast.makeText(getApplicationContext(), "更新错误——>" + code, Toast.LENGTH_LONG).show();
                            Log.e(TAG, "更新错误——>" + code);
                        }
                    }
                }).setSecretMetaData(Config.ALI_APPID, Config.ALI_APPSECRET, Config.ALI_RSA).initialize();
// queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();
    }


}
