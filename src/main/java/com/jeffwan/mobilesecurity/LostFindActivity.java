package com.jeffwan.mobilesecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LostFindActivity extends Activity {
    private static final String TAG = "LostFindActivity";
    private SharedPreferences sp;
    private TextView tv_lostfind_number;
    private ImageView iv_lostding_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getSharedPreferences("config",MODE_PRIVATE);
        super.onCreate(savedInstanceState);

        if (isSetup()) {
            Log.i(TAG,"Load normal interface");
            setContentView(R.layout.activity_lost_find);
                tv_lostfind_number = (TextView) this.findViewById(R.id.tv_lostfind_number);
                iv_lostding_status = (ImageView) this.findViewById(R.id.iv_lostfind_status);
                tv_lostfind_number.setText(sp.getString("safenumber",""));
                boolean protecting = sp.getBoolean("protecting",false);
                if (protecting) {
                iv_lostding_status.setImageResource(R.drawable.lock);
            } else {
                iv_lostding_status.setImageResource(R.drawable.unlock);
            }


        } else {
            Log.i(TAG,"redirect to Setup Configuration");
            Intent intent = new Intent(this,Setup1Activity.class);
            startActivity(intent);
        }



    }

    public boolean isSetup() {

        return sp.getBoolean("setup",false);
    }

    public void reEntrySetup(View view) {
        Intent intent = new Intent(this, Setup1Activity.class);
        startActivity(intent);
        finish();
    }
    
}
