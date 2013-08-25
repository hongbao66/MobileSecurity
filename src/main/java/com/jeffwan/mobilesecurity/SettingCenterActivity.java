package com.jeffwan.mobilesecurity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.jeffwan.mobilesecurity.ui.SettingView;

/**
 * Created by jeffwan on 8/25/13.
 */
public class SettingCenterActivity extends Activity {
    private SettingView sv_setting_update;
    private SharedPreferences sp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sv_setting_update = (SettingView) this.findViewById(R.id.sv_setting_update);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        // Initialize ,false --> close update
        boolean update = sp.getBoolean("update",false);
        sv_setting_update.setStatus(update);


        sv_setting_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sp.edit();
                if (sv_setting_update.isChecked()){
                    sv_setting_update.setStatus(false);
                    editor.putBoolean("update", false);

                }else{
                    sv_setting_update.setStatus(true);
                    editor.putBoolean("update",true);
                }
                editor.commit();
            }
        });

    }

}
