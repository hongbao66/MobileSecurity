package com.jeffwan.mobilesecurity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeffwan.mobilesecurity.adapter.HomeAdapter;
import com.jeffwan.mobilesecurity.utils.MD5Utils;

/**
 * Created by jeffwan on 8/24/13.®
 */
public class HomeActivity extends Activity {
    private GridView gv_home;
    private SharedPreferences sp;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("config",MODE_PRIVATE);

        setContentView(R.layout.activity_home);


        gv_home = (GridView) this.findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(this));

        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:  //Safe
                        // if user set password
                        if(isSetupPwd()){
                            //show normal dialogß
                            showNormalDialog();
                        }else {
                            //show initial dialog
                            showSetupPwdDialog();
                        }

                        break;
                    case 8:  //Setting
                        Intent intent = new Intent(HomeActivity.this, SettingCenterActivity.class);
                        startActivity(intent);
                        break;
                }
            }


        });
    }



    private boolean isSetupPwd() {
        String password = sp.getString("password","");
        if (TextUtils.isEmpty(password)){
            return false;
        } else {
            return true;
        }
    }

    private AlertDialog dialog;

    private void showSetupPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please setup password");
        //here,it's a custom dialog, so we can't use builder.setMessage. we use builder.setView instead
        View view = View.inflate(getApplicationContext(),R.layout.dialog_setup_pwd,null);
        final EditText et_password = (EditText) view.findViewById(R.id.et_password);
        final EditText et_password_confirm = (EditText) view.findViewById(R.id.et_password_confirm);

        //here we can't use AlertDialog default button event, if we click, it will dismiss which we can not control
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        Button bt_ok = (Button) view.findViewById(R.id.bt_ok);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = et_password.getText().toString().trim();
                String password_confirm = et_password_confirm.getText().toString().trim();

                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password_confirm)) {
                    Toast.makeText(getApplicationContext(),"password can't be empty",1).show();
                    return;
                }

                if (password.equals(password_confirm)){
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("password", MD5Utils.encode(password));
                    editor.commit();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(),"Two password are not same",1).show();
                    return;
                }

            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                return ;
            }
        });

        builder.setView(view);
        dialog = builder.show();
    }

    private void showNormalDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please input password");
        //here,it's a custom dialog, so we can't use builder.setMessage. we use builder.setView instead
        View view = View.inflate(getApplicationContext(),R.layout.dialog_normal,null);
        final EditText et_password = (EditText) view.findViewById(R.id.et_password);

        //here we can't use AlertDialog default button event, if we click, it will dismiss which we can not control
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        Button bt_ok = (Button) view.findViewById(R.id.bt_ok);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = et_password.getText().toString().trim();
                String password_store =  sp.getString("password","");

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(),"password can't be empty",1).show();
                    return;
                }

                if (MD5Utils.encode(password).equals(password_store)){

                    dialog.dismiss();
                    //enter the interface

                } else {
                    Toast.makeText(getApplicationContext(),"password is wrong",1).show();
                    return;
                }

            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                return ;
            }
        });

        builder.setView(view);
        dialog = builder.show();
    }





}