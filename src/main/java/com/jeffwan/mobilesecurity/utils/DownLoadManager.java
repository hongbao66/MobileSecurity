package com.jeffwan.mobilesecurity.utils;

import android.app.ProgressDialog;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jeffwan on 8/24/13.
 */
public class DownLoadManager {
    public static File downLoad (String path, String savedPath, ProgressDialog pd) throws Exception {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);

            int code = conn.getResponseCode();
            if (code == 200){
                pd.setMax(conn.getContentLength());

                File file = new File(savedPath);
                FileOutputStream fos = new FileOutputStream(file);
                InputStream is = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                int total = 0;
                while((len = is.read(buffer))!= -1) {
                    fos.write(buffer,0,len);
                    total += len;
                    pd.setProgress(total);
                }
                is.close();
                fos.close();
                return file;

            }else {
                return null;
            }


        }else {
            throw new IllegalAccessException("sd card is not available now");
        }

    }
}
