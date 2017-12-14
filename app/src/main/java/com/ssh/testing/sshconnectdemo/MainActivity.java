package com.ssh.testing.sshconnectdemo;

import android.Manifest;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    SftpUtil upUtil,upUtil1;
    String keyPath, user = "ws.zhoujie.brw", host = "bj-a-internal.brainpp.ml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET}, 0);
        keyPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/sshkey/brainpp_id_rsa";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SftpConnectionMgr.getInstance().getSftpUtil(user, host, keyPath, new SftpConnectCallBack() {
            @Override
            public void onSuccess(SftpUtil util) {
                upUtil = util;
            }

            @Override
            public void onFaile() {

            }
        });
        SftpConnectionMgr.getInstance().getSftpUtil(user, host, keyPath, new SftpConnectCallBack() {
            @Override
            public void onSuccess(SftpUtil util) {
                upUtil1 = util;
            }

            @Override
            public void onFaile() {

            }
        });
    }

    public void upload(View view) {
        String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/vivo1.2.78.622问题图片/Anna.zip";
        String path1 = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/vivo1.2.78.622问题图片/ANDREY.zip";
        SftpConnectionMgr.getInstance().upload("/home/zhoujie/test", path, upUtil);
        SftpConnectionMgr.getInstance().upload("/home/zhoujie/test", path1, upUtil1);
    }

    public void download(View view) {
        SftpConnectionMgr.getInstance().download("/home/zhoujie/picture", "brainpp_id_rsa", Environment.getExternalStorageDirectory().getAbsolutePath(), upUtil);
    }
}
