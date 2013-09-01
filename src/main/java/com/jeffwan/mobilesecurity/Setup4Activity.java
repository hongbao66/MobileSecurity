package com.jeffwan.mobilesecurity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Setup4Activity extends BaseSetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
    }

    @Override
    public void showNext() {
        Intent intent = new Intent(this,LostFindActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.trans_in,R.anim.trans_out);
    }

    @Override
    public void showPre() {
        Intent intent = new Intent(this,Setup3Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.trans_pre_in,R.anim.trans_pre_out);
    }

    
}
