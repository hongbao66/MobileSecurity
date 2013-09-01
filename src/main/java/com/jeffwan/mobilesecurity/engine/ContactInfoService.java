package com.jeffwan.mobilesecurity.engine;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.jeffwan.mobilesecurity.domain.ContactInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeffwan on 9/1/13.
 */
public class ContactInfoService {

    public static List<ContactInfo> getContactInfos(Context context){
        //create a contacts list
        List<ContactInfo> contactInfos = new ArrayList<ContactInfo>();
        ContentResolver resolver = context.getContentResolver();
        // raw_contact table --- uri
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        // data table -- uri
        Uri dataUri = Uri.parse("content://com.android.contacts/data");
        Cursor cursor = resolver.query(uri, new String[] { "contact_id" },
                null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            if (id != null) {
                Cursor dataCursor = resolver.query(dataUri, new String[] {
                        "data1", "mimetype" }, "raw_contact_id=?",
                        new String[] { id }, null);
                ContactInfo contactInfo = new ContactInfo();
                while (dataCursor.moveToNext()) {
                    String data = dataCursor.getString(0);
                    String mimetype = dataCursor.getString(1);
                    if("vnd.android.cursor.item/name".equals(mimetype)){
                        contactInfo.setName(data);
                    }else if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
                        contactInfo.setNumber(data);
                    }
                }
                contactInfos.add(contactInfo);
                dataCursor.close();
            }
        }
        cursor.close();
        return contactInfos;
    }


}
