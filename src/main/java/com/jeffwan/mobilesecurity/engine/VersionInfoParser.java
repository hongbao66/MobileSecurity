package com.jeffwan.mobilesecurity.engine;

import android.util.Xml;

import com.jeffwan.mobilesecurity.domain.VersionInfo;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jeffwan on 8/21/13.
 */
public class VersionInfoParser {

    public static VersionInfo getUpdateInfo(InputStream is) {
        XmlPullParser parser = Xml.newPullParser();
        VersionInfo info = new VersionInfo();
        try {
            parser.setInput(is,"UTF-8");
            int type = parser.getEventType();
            while(type != XmlPullParser.END_DOCUMENT){
                switch (type){
                    case XmlPullParser.START_TAG:
                        if ("version".equals(parser.getName())){
                            String version = parser.nextText();
                            info.setVerison(version);
                        }else if ("description".equals(parser.getName())){
                            String description = parser.nextText();
                            info.setDescription(description);
                        }else if ("url".equals(parser.getName())){
                            String url = parser.nextText();
                            info.setUrl(url);
                        }
                        break;
                }
                type = parser.next();
            }
            is.close();
            return info;

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}
