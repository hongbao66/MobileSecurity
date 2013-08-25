package com.jeffwan.mobilesecurity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.jeffwan.mobilesecurity.adapter.HomeAdapter;

/**
 * Created by jeffwan on 8/24/13.®
 */
public class HomeActivity extends Activity {
    private GridView gv_home;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        gv_home = (GridView) this.findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(this));

        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 8:
                        Intent intent = new Intent(HomeActivity.this, SettingCenterActivity.class);
                        startActivity(intent);
                        break;
                }

            }
        });

    }


}