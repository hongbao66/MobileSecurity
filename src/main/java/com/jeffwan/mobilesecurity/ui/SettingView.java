package com.jeffwan.mobilesecurity.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeffwan.mobilesecurity.R;

/**
 * Created by jeffwan on 8/25/13.
 */
public class SettingView extends RelativeLayout {
    private TextView tv_title;
    private TextView tv_description;
    private CheckBox cb_status;
    private String title;
    private String content_on;
    private String content_off;

    // code new SettingView use this constructor
    public SettingView(Context context) {
        super(context);
        initView(context);
    }

    //use this constructor in layout
    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

        //when system parse xml layout, already get all attrs in attrs object
        //mapping attrs array to our attrs array
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.setting_view);

        //get our data from layout xml
        title = a.getString(R.styleable.setting_view_title);
        content_on = a.getString(R.styleable.setting_view_content_on);
        content_off = a.getString(R.styleable.setting_view_content_off);

        setTitle(title);
        if (isChecked()){
            setDescription(content_on);
        }else {
            setDescription(content_off);
        }

        // release memory
        a.recycle();
    }


    public void setTitle(String text) {
        tv_title.setText(text);
    }


    public void setDescription(String text) {
        tv_description.setText(text);
    }

    public void setStatus(boolean checked){
        cb_status.setChecked(checked);
        if (checked){
            setDescription(content_on);
        }else {
            setDescription(content_off);
        }
    }

    public boolean isChecked(){
        return cb_status.isChecked();
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.ui_setting_view, this);
        tv_title = (TextView) view.findViewById(R.id.tv_setting_view_title);
        tv_description = (TextView) view.findViewById(R.id.tv_setting_view_description);
        cb_status = (CheckBox) view.findViewById(R.id.cb_setting_view_status);
        this.setBackgroundResource(R.drawable.status_selector);
    }


}
