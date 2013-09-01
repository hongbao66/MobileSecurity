package com.jeffwan.mobilesecurity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Setup2Activity extends BaseSetupActivity {
    private TextView tv_setup2_sim_number;
    private TelephonyManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        tv_setup2_sim_number = (TextView) this.findViewById(R.id.tv_setup2_sim_card);
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String text = sp.getString("sim","");
        if (TextUtils.isEmpty(text)) {
            tv_setup2_sim_number.setText("Sim Card not bind now!");
        } else {
            tv_setup2_sim_number.setText("Sim Card:"+text);
        }

    }

    @Override
    public void showNext() {
        if (TextUtils.isEmpty(sp.getString("sim",""))) {
            Toast.makeText(getApplicationContext(),"please bind sim first",1).show();
            return;
        }

        Intent intent = new Intent(this,Setup3Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.trans_in,R.anim.trans_out);
    }

    @Override
    public void showPre() {
        Intent intent = new Intent(this,Setup1Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.trans_pre_in,R.anim.trans_pre_out);
    }

    public void bindSimCard(View view) {
        String savedSim = sp.getString("sim","");
        SharedPreferences.Editor editor = sp.edit();

        if (TextUtils.isEmpty(savedSim)){
            String sim = tm.getSimSerialNumber();
            editor.putString("sim",sim);
            editor.commit();
            tv_setup2_sim_number.setText("Sim Card:"+sim);
        } else {
            editor.putString("sim","");
            editor.commit();
            tv_setup2_sim_number.setText("Sim Card has already ubinded");
        }


    }

    
}
