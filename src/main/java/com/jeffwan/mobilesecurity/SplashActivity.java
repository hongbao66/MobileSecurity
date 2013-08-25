package com.jeffwan.mobilesecurity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jeffwan.mobilesecurity.engine.VersionInfoParser;

import com.jeffwan.mobilesecurity.domain.VersionInfo;
import com.jeffwan.mobilesecurity.utils.DownLoadManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity" ;
    private static final int PARSE_SUCCESS = 1;
    private static final int PARSE_ERROR = 2;
    private static final int SEVER_ERROR = 3;
    private static final int NETWORK_ERROR = 4;
    private static final int DOWNLOAD_SUCCESS = 5;
    private static final int DOWNLOAD_ERROR = 6;
    private static final int SDCARD_ERROR = 7;
    private TextView tv_splash_version;
    private VersionInfo versionInfo;

    // package manger -> Android apk Manager
    private PackageManager pm;

    private ProgressDialog pd;


    //define a message handler used for -- let child thread send message to main thread
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PARSE_SUCCESS:
                    Toast.makeText(getApplicationContext(),"parse success",1).show();
                    // check version on server & client
                    // only same or not same, no >=  becase the server version always lead you.
                    if (getAppVersion().equals(versionInfo.getVerison())){
                        Log.i(TAG,"version are same,going to home page");
                        loadHomeUI();

                    }else{
                        Log.i(TAG,"version are not same,show messagebox");
                        showUpdateDialog();
                    }

                    break;
                case PARSE_ERROR:
                    Toast.makeText(getApplicationContext(),"parse eroor",1).show();
                    //even if error exists, we need to enter the messagebox
                    loadHomeUI();
                    break;
                case SEVER_ERROR:
                    Toast.makeText(getApplicationContext(),"server error",1).show();
                    break;
                case NETWORK_ERROR:
                    Toast.makeText(getApplicationContext(),"network error",1).show();
                    break;
                case DOWNLOAD_ERROR:
                    Toast.makeText(getApplicationContext(),"download error",1).show();
                    break;
                case SDCARD_ERROR:
                    Toast.makeText(getApplicationContext(),"sdcard error",1).show();
                    loadHomeUI();
                    break;
                case DOWNLOAD_SUCCESS:
                    Toast.makeText(getApplicationContext(),"downlaod success",1).show();
                    File file = (File) msg.obj;
                    installApk(file);
                    break;
            }

        }
    };

    private void installApk(File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_splash_version = (TextView) this.findViewById(R.id.tv_splash_version);

        pm = getPackageManager();
        tv_splash_version.setText("Version"+ getAppVersion());

        new Thread(new CheckVersion()).start();



    };

    private class CheckVersion implements Runnable{

        @Override
        public void run() {
            SharedPreferences sp = getSharedPreferences("config",MODE_PRIVATE);
            boolean update = sp.getBoolean("update",false);
            if (!update){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loadHomeUI();
                return;
            }

            //if exists,get if not, create
            Message msg = Message.obtain();
            long startTime = System.currentTimeMillis();


            try {
                URL url = new URL(getResources().getString(R.string.serverUrl));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(5000);

                int code = conn.getResponseCode();
                if(code ==200){
                    InputStream is = conn.getInputStream();
                    versionInfo = VersionInfoParser.getUpdateInfo(is);

                    //here we can not handle logic in a child Thread, so we send msg back to handle

                    if(versionInfo !=null){
                        Log.i(TAG,"parse successfully，server version"+versionInfo.getVerison());
                        msg.what = PARSE_SUCCESS;
                    }else {
                        Log.i(TAG, "parse fail");
                        msg.what = PARSE_ERROR;
                    }

                }else{
                    //TODO: server problem
                    Log.i(TAG,"Server internal error");
                    msg.what = SEVER_ERROR;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.i(TAG,"url not valid");
                msg.what = SEVER_ERROR;
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG,"FILE NOT FOUND");
                msg.what = NETWORK_ERROR;
            } finally {

                long endTime = System.currentTimeMillis();
                long useTime = endTime - startTime;

                //prevent redirect to fast
                if(useTime < 2000){
                    try {
                        Thread.sleep(2000 - useTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            //send the main thread the server information.
            handler.sendMessage(msg);
        }
    }



    public String getAppVersion(){
        //get information of apk
        PackageInfo packageInfo;

        try {
//          packageInfo = pm.getPackageInfo("com.jeffwan.mobilesecurity",0);
            // we use packageName instead of package name
            packageInfo = pm.getPackageInfo(getPackageName(),0);
            String Version = packageInfo.versionName;
            return Version;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            //can't reach  -- use when error never happen
            return "";
        }
    }

    public void loadHomeUI(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("update reminder");
        builder.setMessage(versionInfo.getDescription());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //downlaod new version, replace install
                Log.i(TAG,"new version, download and replace install"+ versionInfo.getUrl());
                pd = new ProgressDialog(SplashActivity.this);
                pd.setTitle("update reminder");
                pd.setMessage("downloading");
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // only set this style can show ProgressDialog successfully
                pd.show(); // must in main thread


                // actually, app show the old dialog for compatible
                new Thread() {
                    public void run(){
                        Message msg = Message.obtain();
                        try {
                            File file = DownLoadManager.downLoad(versionInfo.getUrl(),"/sdcard/new.apk",pd);
                            if (file!= null){
                                msg.what = DOWNLOAD_SUCCESS;
                                msg.obj = file;

                            }else {
                                msg.what = DOWNLOAD_ERROR;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            msg.what = SDCARD_ERROR;

                        }finally {
                            handler.sendMessage(msg);
                            pd.dismiss();//could in child thread, we can also dismiss in the DownaloadMangaer
                        }
                    }
                }.start();
            }

        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                loadHomeUI();
            }
        });
        builder.show();

    }

}
