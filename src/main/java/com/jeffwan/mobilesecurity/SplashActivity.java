package com.jeffwan.mobilesecurity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class SplashActivity extends Activity {
    private TextView tv_splash_version;

    // package manger -> Android apk Manager
    private PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_splash_version = (TextView) this.findViewById(R.id.tv_splash_appname);

        pm = getPackageManager();
        tv_splash_version.setText("Version"+ getAppVersion());



    }


    public String getAppVersion(){
        //get information of apk
        PackageInfo packageInfo;

        try {
//          packageInfo = pm.getPackageInfo("com.jeffwan.mobilesecurity",0);
            packageInfo = pm.getPackageInfo(getPackageName(),0);
            String Version = packageInfo.versionName;
            return Version;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            //can't reach
            return "";
        }
    }



}
