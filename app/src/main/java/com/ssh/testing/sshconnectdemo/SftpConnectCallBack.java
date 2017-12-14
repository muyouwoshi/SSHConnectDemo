package com.ssh.testing.sshconnectdemo;

/**
 * Created by zhoujie on 2017/12/7.
 */

interface SftpConnectCallBack {
    public void onSuccess(SftpUtil util);
    public void onFaile();
}
