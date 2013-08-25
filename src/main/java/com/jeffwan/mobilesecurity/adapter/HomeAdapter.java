package com.jeffwan.mobilesecurity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeffwan.mobilesecurity.R;

/**
 * Created by jeffwan on 8/25/13.
 */
public class HomeAdapter extends BaseAdapter{


    private Context context;

    private String[] names = {"Safe", "callmsgsafe", "app", "taskmanager", "netmanager", "Anti-Virus",
            "sysoptimize", "AdvTools", "Settings"};

    private int[] icons = {R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app,
            R.drawable.taskmanager,R.drawable.netmanager, R.drawable.trojan,
            R.drawable.sysoptimize, R.drawable.atools, R.drawable.settings};

    public HomeAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = View.inflate(context, R.layout.grid_home_item,null);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_home_item_icon);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_home_item_name);
        iv_icon.setImageResource(icons[i]);
        tv_name.setText(names[i]);
        return view;
    }
}
