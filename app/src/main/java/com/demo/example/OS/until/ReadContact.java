package com.demo.example.OS.until;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;


public class ReadContact {
    public static String[] getNamePhoto(Context context, String str) {
        Cursor query;
        String[] strArr = new String[2];
        try {
            if (!str.isEmpty() && (query = context.getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(str)), new String[]{"display_name", "photo_uri"}, null, null, null)) != null) {
                while (query.moveToNext()) {
                    strArr[0] = query.getString(0);
                    strArr[1] = query.getString(1);
                }
                query.close();
            }
        } catch (Exception unused) {
        }
        if (strArr[0] == null) {
            strArr[0] = "";
        }
        if (strArr[1] == null) {
            strArr[1] = "";
        }
        return strArr;
    }
}
