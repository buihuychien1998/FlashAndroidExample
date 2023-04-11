package com.demo.example.OS.asysn;

import android.app.Activity;
import android.widget.Toast;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import java.io.File;


public class DownloadGif {

    
    public interface DownloadResult {
        void progress(String str);

        void result(String str);
    }

    public void download(final Activity activity, String str, String str2, String str3, final DownloadResult downloadResult) {
        Ion.with(activity).load(str).progress(new ProgressCallback() { 
            @Override 
            public void onProgress(final long j, final long j2) {
                activity.runOnUiThread(new Runnable() { 
                    @Override 
                    public void run() {
                        DownloadResult downloadResult2 = downloadResult;
                        downloadResult2.progress(((j * 100) / j2) + "%");
                    }
                });
            }
        }).write(new File(str2 + "/" + str3 + ".mp4")).setCallback(new FutureCallback<File>() { 
            public void onCompleted(Exception exc, File file) {
                if (exc != null) {
                    Toast.makeText(activity, "Error downloading files, please check the path or network connection", Toast.LENGTH_SHORT).show();
                    downloadResult.result("");
                    return;
                }
                downloadResult.result(file.toString());
            }
        });
    }
}
