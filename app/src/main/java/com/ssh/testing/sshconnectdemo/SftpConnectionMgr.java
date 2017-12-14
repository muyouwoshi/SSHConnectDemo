package com.ssh.testing.sshconnectdemo;

import com.jcraft.jsch.JSchException;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhoujie on 2017/12/7.
 */

public class SftpConnectionMgr {
    private static volatile SftpConnectionMgr INSTANCE;

    public static SftpConnectionMgr getInstance() {
        if (INSTANCE == null) {
            synchronized (SftpConnectionMgr.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SftpConnectionMgr();
                }
            }
        }
        return INSTANCE;
    }

    private SftpConnectionMgr() {
    }

    public void getSftpUtil(final String user, final String host, final String keyPath, final SftpConnectCallBack callBack) {
        new Observable<SftpUtil>() {
            @Override
            protected void subscribeActual(Observer<? super SftpUtil> observer) {
                try {
                    SftpUtil util = new SftpUtil(user, host, keyPath);
                    boolean isConnect = util.connect();
                    if (isConnect) {
                        observer.onNext(util);
                    } else {
                        observer.onNext(null);
                    }
                } catch (JSchException e) {
                    observer.onError(e);
                }
                observer.onComplete();
            }
        }.subscribeOn(Schedulers.io()).subscribe(new Consumer<SftpUtil>() {
            @Override
            public void accept(SftpUtil sftpUtil) throws Exception {
                if (sftpUtil == null) {
                    callBack.onFaile();
                } else {
                    callBack.onSuccess(sftpUtil);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
                callBack.onFaile();
            }
        });
    }

    /**
     * 上传文件
     * @param directory 上传的目录
     * @param uploadFile 要上传的文件
     * @param sftp
     */
    public void upload(final String directory,final String uploadFile,final SftpUtil sftp) {
        new Observable<Boolean>() {
            @Override
            protected void subscribeActual(Observer<? super Boolean> observer) {
                try {
                    sftp.upload(directory,uploadFile);
                    observer.onNext(true);
                } catch (Exception e) {
                    observer.onError(e);
                }finally {
                    observer.onComplete();
                }
            }
        }.subscribeOn(Schedulers.io()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean uploadSuccess) throws Exception {
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });

    }

    /**
     * 下载文件
     * @param remoteDirectory 下载目录
     * @param downloadFileName 下载的文件
     * @param localPath 存在本地的路径
     * @param sftp
     */
    public void download(final String remoteDirectory,final String downloadFileName, final String localPath,final SftpUtil sftp) {
        new Observable<Boolean>() {
            @Override
            protected void subscribeActual(Observer<? super Boolean> observer) {
                try {
                    sftp.download(remoteDirectory,downloadFileName,localPath);
                    observer.onNext(true);
                } catch (Exception e) {
                    observer.onError(e);
                }finally {
                    observer.onComplete();
                }
            }
        }.subscribeOn(Schedulers.io()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean downloadSuccess) throws Exception {
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }
}
