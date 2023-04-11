package com.demo.example.OS;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.example.R;
import com.demo.example.OS.adapter.AdapterRecorder;
import com.demo.example.OS.dialog.DialogRecordSel;
import com.demo.example.OS.item.ItemRecorder;
import com.demo.example.OS.until.OtherUntil;
import com.demo.example.OS.until.ReadContact;
import com.demo.example.OS.until.SaveUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;


public class RecorderListActivity extends AppCompatActivity implements AdapterRecorder.RecorderItemResult, DialogRecordSel.SelResult {
    private AdapterRecorder adapterRecorder;
    private ArrayList<ItemRecorder> arr;
    private ArrayList<ItemRecorder> arrDel;
    private int pos;
    private TextView tvCancel;
    private TextView tvChoose;
    private TextView tvDel;

    
    @Override
    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setNavigationBarColor(getColor(R.color.colorNavi));
        getWindow().setStatusBarColor(-1);
        setContentView(R.layout.activity_recorder_list);
        initView();
        getData();

        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.FullscreenAd(this);


    }

    private void getData() {
        final Handler handler = new Handler(new Handler.Callback() { 
            @Override 
            public final boolean handleMessage(Message message) {
                return RecorderListActivity.this.m10x487161e6(message);
            }
        });
        new Thread(new Runnable() { 
            @Override 
            public final void run() {
                RecorderListActivity.this.m11x624b9024(handler);
            }
        }).start();
    }

    
    public  boolean m10x487161e6(Message message) {
        if (this.arr.size() == 0) {
            findViewById(R.id.tv_empty).setVisibility(View.VISIBLE);
            return true;
        }
        this.adapterRecorder.notifyDataSetChanged();
        return true;
    }

    public void m11x624b9024(Handler handler) {
        File[] listFiles = new File(OtherUntil.getPathSave(this)).listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File file : listFiles) {
                try {
                    int indexOf = file.getName().indexOf("_");
                    String substring = file.getName().substring(0, indexOf);
                    int parseInt = Integer.parseInt(file.getName().substring(indexOf + 1, indexOf + 2));
                    String[] namePhoto = ReadContact.getNamePhoto(this, substring);
                    this.arr.add(new ItemRecorder(file.getPath(), namePhoto[0], substring, namePhoto[1], file.length(), file.lastModified(), parseInt));
                } catch (Exception unused) {
                }
            }
            Collections.sort(this.arr, new Comparator<ItemRecorder>() {
                @Override
                public int compare(ItemRecorder o1, ItemRecorder o2) {
                    return RecorderListActivity.lambda$getData$1(o1, o2);

                }
            });
        }
        handler.sendEmptyMessage(1);
    }


    
    public static  int lambda$getData$1(ItemRecorder itemRecorder, ItemRecorder itemRecorder2) {
        return ((int) itemRecorder2.getTime()) - ((int) itemRecorder.getTime());
    }

    private void initView() {
        this.tvChoose = (TextView) findViewById(R.id.tv_choose);
        this.tvCancel = (TextView) findViewById(R.id.tv_cancel);
        this.tvDel = (TextView) findViewById(R.id.tv_del);
        this.arr = new ArrayList<>();
        this.arrDel = new ArrayList<>();
        this.adapterRecorder = new AdapterRecorder(this.arr, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setAdapter(this.adapterRecorder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void update() {
        if (this.adapterRecorder.isChoose()) {
            this.tvChoose.setVisibility(View.GONE);
            this.tvCancel.setVisibility(View.VISIBLE);
            this.tvDel.setVisibility(View.VISIBLE);
            return;
        }
        this.tvChoose.setVisibility(View.VISIBLE);
        this.tvCancel.setVisibility(View.GONE);
        this.tvDel.setVisibility(View.GONE);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.tv_choose) {
            this.adapterRecorder.setChoose(true);
        } else {
            this.adapterRecorder.setChoose(false);
            this.arrDel.clear();
            for (int size = this.arr.size() - 1; size >= 0; size--) {
                if (this.arr.get(size).isChoose()) {
                    if (view.getId() == R.id.tv_cancel) {
                        this.arr.get(size).setChoose(false);
                    } else {
                        this.arrDel.add(this.arr.get(size));
                        this.arr.remove(size);
                    }
                }
            }
            if (view.getId() == R.id.tv_del) {
                delFile();
            }
            this.adapterRecorder.notifyDataSetChanged();
        }
        update();
    }

    @Override 
    public void onItemRecorderClick(int i) {
        this.pos = i;
        new DialogRecordSel(this, this).show();
    }

    private void delFile() {
        new Thread(new Runnable() { 
            @Override 
            public final void run() {
                RecorderListActivity.this.lambda$delFile$3$RecorderListActivity();
            }
        }).start();
    }

    public  void lambda$delFile$3$RecorderListActivity() {
        Iterator<ItemRecorder> it = this.arrDel.iterator();
        while (it.hasNext()) {
            new File(it.next().getData()).delete();
        }
    }

    @Override 
    public void onPlay() {
        File file = new File(this.arr.get(this.pos).getData());
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file), "audio/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    @Override 
    public void onExportFile() {


        final Handler handler = new Handler(new Handler.Callback() { 
            @Override 
            public final boolean handleMessage(Message message) {
                return RecorderListActivity.this.m12x934614b9(message);
            }
        });
        new Thread(new Runnable() { 
            @Override 
            public final void run() {
                RecorderListActivity.this.m13x20332bd8(handler);
            }
        }).start();


    }

    public  boolean m12x934614b9(Message message) {
        if (message.what == 1) {
            Toast.makeText(this, "Save Path =" + path_uri.getPath(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    Uri path_uri;
    public  void m13x20332bd8(Handler handler) {
        File file = new File(this.arr.get(this.pos).getData());
        try {
            byte[] bArr = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bArr);
            fileInputStream.close();
            path_uri = SaveUtils.saveSound(this, bArr, file.getName().substring(0, file.getName().lastIndexOf(".")));
            handler.sendEmptyMessage(1);
        } catch (Exception unused) {
            handler.sendEmptyMessage(2);
        }
    }

}
