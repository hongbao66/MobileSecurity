package com.jeffwan.mobilesecurity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by jeffwan on 9/1/13.
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompleteReceiver" ;
    private SharedPreferences sp;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"Bootstrap successfully");
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        boolean protecting = sp.getBoolean("protecting", false);
        if (protecting) {
            // get saved SIM CARD
            String bindSim = sp.getString("sim","");
            // get current SIM CARD
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String currentSim = tm.getSimSerialNumber();
            if (bindSim.equals(currentSim)) {
                Log.i(TAG,"SIM CARD no changes");
            } else {
                Log.i(TAG,"SIM CARD changes, may be stolen");
                String number = sp.getString("safenumber","");
                Log.i(TAG,number);
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number,null,"your phone may be lost ",null,null);
            }

        } else {
            // Mobile An-ti safe is not set now
            Log.i(TAG, "An-ti safe is not setup yet now");
        }


    }
}
