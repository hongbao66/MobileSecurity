package com.jeffwan.mobilesecurity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.jeffwan.mobilesecurity.adapter.HomeAdapter;

/**
 * Created by jeffwan on 8/24/13.
 */
public class HomeActivity extends Activity {
    private GridView gv_home;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        gv_home = (GridView) this.findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(this));
    }


}