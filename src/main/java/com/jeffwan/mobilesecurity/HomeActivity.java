package com.jeffwan.mobilesecurity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by jeffwan on 8/24/13.
 */
public class HomeActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("I am the home Screen");
        setContentView(tv);
    }


}