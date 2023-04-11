package com.demo.example.OS.until;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.demo.example.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveUtils {

    public static Uri saveSound(android.content.Context r5, byte[] r6, String r7) throws java.io.IOException {


        Log.e("SaveUtils", "" + r7);
        try {
            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC + "/" + r5.getString(R.string.app_name));
            File dir = new File(root + File.separator);
            if (!dir.exists()) dir.mkdir();

            
            File file = new File(root + File.separator + r7 + ".m4a");
            file.createNewFile();

            FileOutputStream out = new FileOutputStream(file);
            out.write(r6);
            out.close();
            return Uri.fromFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


        




































































































    }
}
