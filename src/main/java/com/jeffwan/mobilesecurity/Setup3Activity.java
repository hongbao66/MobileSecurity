package com.jeffwan.mobilesecurity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Setup3Activity extends BaseSetupActivity {
    private EditText et_setup3_safenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        et_setup3_safenumber = (EditText) this.findViewById(R.id.et_setup3_saftnumber);
        et_setup3_safenumber.setText(sp.getString("safenumber",""));
    }

    @Override
    public void showNext() {
        String safenumber =  et_setup3_safenumber.getText().toString().trim();
        if (TextUtils.isEmpty(safenumber)) {
            Toast.makeText(getApplicationContext(),"please set safenumber first",1).show();
            return;
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("safenumber",safenumber);
        editor.commit();

        Intent intent = new Intent(this,Setup4Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.trans_in,R.anim.trans_out);

    }

    @Override
    public void showPre() {
        Intent intent = new Intent(this,Setup2Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.trans_pre_in,R.anim.trans_pre_out);
    }


    // select Contact



    public void selectContact(View view) {
        Intent intent = new Intent(this,SelectContactActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data!=null) {
            String number = data.getStringExtra("number");
            et_setup3_safenumber.setText(number);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
