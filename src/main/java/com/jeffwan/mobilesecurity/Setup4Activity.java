package com.jeffwan.mobilesecurity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Setup4Activity extends BaseSetupActivity {
    private CheckBox cb_setup4_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);

        cb_setup4_status = (CheckBox) this.findViewById(R.id.cb_setup4_status);
        boolean protecting = sp.getBoolean("protecting",false);
        cb_setup4_status.setChecked(protecting);
        cb_setup4_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("protecting", isChecked);
                editor.commit();
            }
        });
    }

    @Override
    public void showNext() {
        if (cb_setup4_status.isChecked()) {
            Intent intent = new Intent(this,LostFindActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.trans_in,R.anim.trans_out);

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Recommendation");
            builder.setMessage("Mobile Safe can keep your phone security, recommend you open it");
            builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("setup",true);
                    editor.putBoolean("protecting",true);
                    editor.commit();
                    finish();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.show();

        }

    }

    @Override
    public void showPre() {
        Intent intent = new Intent(this,Setup3Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.trans_pre_in,R.anim.trans_pre_out);
    }

    
}
