package com.jeffwan.mobilesecurity.receiver;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import com.jeffwan.mobilesecurity.R;
import com.jeffwan.mobilesecurity.engine.GPSInfoProvider;

/**
 * Created by jeffwan on 9/1/13.
 */
public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"sms comes!!!");
        Object[] objs = (Object[])intent.getExtras().get("pdus");
        for (Object obj: objs) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
            String sender = smsMessage.getOriginatingAddress();
            String body = smsMessage.getMessageBody();

            if("#*location*#".equals(body)) {
                String location = GPSInfoProvider.getInstance(context).getLastLocation();
                Log.i(TAG,"return location of the phone: "+location);
                if (!TextUtils.isEmpty(location)){
                    SmsManager.getDefault().sendTextMessage(sender,null,location,null,null);
                }
                abortBroadcast();

            } else if("#*alarm*#".equals(body)) {
                Log.i(TAG,"paly alarm sound--ylzs");
                MediaPlayer mplayer = MediaPlayer.create(context, R.raw.ylzs);
                mplayer.setLooping(false);
                mplayer.setVolume(1.0f, 1.0f);
                mplayer.start();

                // abort the alarm message
                abortBroadcast();

            } else if("#*lockscreen*#".equals(body)) {
                DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
                dpm.resetPassword("123",0);
                dpm.lockNow();

                abortBroadcast();
            } else if("#*wipedata*#".equals(body)) {
                DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
                dpm.wipeData(0);

                abortBroadcast();
            }

        }

    }
}
