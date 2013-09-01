package com.jeffwan.mobilesecurity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by jeffwan on 9/1/13.
 */
public abstract class BaseSetupActivity extends Activity {
    private static final String TAG = "BaseSetupActivity" ;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float v1, float v2) {
                if (Math.abs(v1)< 200 ){
                    Log.i(TAG,"you move so slow");
                    return false;
                }

                if (Math.abs(e1.getRawY()-e2.getRawY()) > 200) {
                    Log.i(TAG,"not support Y move ");
                    return false;
                }

                if (e1.getRawX() - e2.getRawX() > 200) {
                    showNext();
                    return false;
                }

                if(e1.getRawX() - e2.getRawX() <200 ){
                    showPre();
                    return  false;
                }


                return super.onFling(e1,e2,v1,v2);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void next (View view) {
        showNext();

    }

    public void pre (View view) {
        showPre();

    }

    public abstract void showNext();

    public abstract void showPre();
}
