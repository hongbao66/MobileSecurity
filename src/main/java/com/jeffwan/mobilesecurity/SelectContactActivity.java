package com.jeffwan.mobilesecurity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeffwan.mobilesecurity.domain.ContactInfo;
import com.jeffwan.mobilesecurity.engine.ContactInfoService;

import java.util.List;

public class SelectContactActivity extends Activity {
    private ListView lv_select_contact;
    private List<ContactInfo> contactInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contact);
        lv_select_contact = (ListView) findViewById(R.id.lv_select_contact);
        contactInfos = ContactInfoService.getContactInfos(this);
        lv_select_contact.setAdapter(new ContactAdapter());

        //set click event on listview item
        lv_select_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                ContactInfo info = contactInfos.get(position);
                String number = info.getNumber();

                //transfer other date to other activity
                Intent data = new Intent();
                data.putExtra("number", number);
                setResult(100, data);

                //close current activity, then transfer data to caller, caller wwill execute onactivityResult
                finish();
            }
        });
    }


    private class ContactAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return contactInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ContactInfo info = contactInfos.get(position);
            View view = View.inflate(getApplicationContext(), R.layout.contact_item, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_contact_name);
            TextView tv_number = (TextView) view.findViewById(R.id.tv_contact_number);
            tv_name.setText(info.getName());
            tv_number.setText(info.getNumber());
            return view;
        }

    }

    
}
