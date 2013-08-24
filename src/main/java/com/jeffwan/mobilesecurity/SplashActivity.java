package com.jeffwan.mobilesecurity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import com.jeffwan.mobilesecurity.engine.VersionInfoParser;

import com.jeffwan.mobilesecurity.domain.VersionInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity" ;
    private TextView tv_splash_version;
    private VersionInfo versionInfo;

    // package manger -> Android apk Manager
    private PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_splash_version = (TextView) this.findViewById(R.id.tv_splash_version);

        pm = getPackageManager();
        tv_splash_version.setText("Version"+ getAppVersion());




    }

    private class checkVersion implements Runnable{

        @Override
        public void run() {
            try {
                URL url = new URL(getResources().getString(R.string.serverUrl));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(5000);

                int code = conn.getResponseCode();
                if(code ==200){
                    InputStream is = conn.getInputStream();
                    versionInfo = VersionInfoParser.getUpdateInfo(is);

                    if(versionInfo !=null){
                        Log.i(TAG,"parse successfully");
                    }else {
                        Log.i(TAG, "parse fail");
                    }

                }else{
                    //TODO: server problem
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


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



}
