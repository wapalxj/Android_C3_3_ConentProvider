package domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vero on 2016/5/30.
 */
public class ContactsUtils {
    public static List<ContactInfo> displayContaxts(Context context){
        List<ContactInfo> list=new ArrayList<>();
        Uri contact_uri=Uri.parse("content://com.android.contacts/raw_contacts");
        Uri data_uri=Uri.parse("content://com.android.contacts/data");
        ContentResolver resolver=context.getContentResolver();
        Cursor contact_cursor=resolver.query(contact_uri,new String[]{"contact_id"},null,null,null);
        while (contact_cursor.moveToNext()){
            String id=contact_cursor.getString(0);
            if (id==null){//为了解决进行删除之后再查找而产生的空指针
                continue;
            }
            ContactInfo info=new ContactInfo();
            Cursor dataCursor=resolver.query(data_uri,new String[]{"data1","mimetype"},"raw_contact_id=?",new String[]{id},null);
            while (dataCursor.moveToNext()){
                String data1=dataCursor.getString(0);
                String mimetype=dataCursor.getString(1);
                if (mimetype.equals("vnd.android.cursor.item/name")){
                    info.setName(data1);
                }else if (mimetype.equals("vnd.android.cursor.item/email_v2")){
                    info.setEmail(data1);
                }else if (mimetype.equals("vnd.android.cursor.item/phone_v2")){
                    info.setPhone(data1);
                }else if (mimetype.equals("vnd.android.cursor.item/im")){
                    info.setQq(data1);
                }
            }
            list.add(info);
            dataCursor.close();
        }
        contact_cursor.close();
        return list;
    }
}
