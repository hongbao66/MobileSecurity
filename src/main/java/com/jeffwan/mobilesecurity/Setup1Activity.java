package com.jeffwan.mobilesecurity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Setup1Activity extends BaseSetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }


    @Override
    public void showNext() {
        Intent intent = new Intent(this,Setup2Activity.class);
        startActivity(intent);
        finish(); // close this Activity
        overridePendingTransition(R.anim.trans_in,R.anim.trans_out);
    }

    @Override
    public void showPre() {

    }
}
