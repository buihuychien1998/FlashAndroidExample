package com.demo.example.OS;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.example.R;
import com.demo.example.OS.adapter.AdapterThemes;
import com.demo.example.OS.asysn.DownloadGif;
import com.demo.example.OS.asysn.GetAllThemeOnline;
import com.demo.example.OS.dialog.DialogGallery;
import com.demo.example.OS.dialog.LoadingDialog;
import com.demo.example.OS.item.ItemTheme;
import com.demo.example.OS.until.FileUltils;
import com.demo.example.OS.until.MyShare;
import com.demo.example.OS.until.OtherUntil;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


public class VideoActivity extends AppCompatActivity implements AdapterThemes.ThemeResult {
    private AdapterThemes adapterThemes;
    private ArrayList<ItemTheme> arr;
    private int count = 0;
    private LoadingDialog loadingDialog;
    private String path;
    private String pathPhoto;
    private String pathTheme;
    private View rlMain;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setNavigationBarColor(getColor(R.color.colorNavi));
        getWindow().setStatusBarColor(-1);
        setContentView(R.layout.activity_video);

        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.bannerAd), this);


        this.loadingDialog = new LoadingDialog(this);
        init();
        initData();
        setColorNavi();
    }

    private void init() {
        this.rlMain = findViewById(R.id.ll_main);
        this.path = OtherUntil.getStore(this) + "/.theme/";
        this.pathPhoto = OtherUntil.getStore(this) + "/.photo/";
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        ArrayList<ItemTheme> arrayList = new ArrayList<>();
        this.arr = arrayList;
        AdapterThemes adapterThemes = new AdapterThemes(arrayList, this);
        this.adapterThemes = adapterThemes;
        recyclerView.setAdapter(adapterThemes);
        recyclerView.setLayoutManager(new GridLayoutManager((Context) this, 2, RecyclerView.VERTICAL, false));
    }


    public void initData() {
        this.arr.clear();
        File file = new File(this.path);
        if (!file.exists()) {
            file.mkdirs();
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File file2 : listFiles) {
                this.arr.add(new ItemTheme(file2.getName().substring(0, file2.getName().indexOf(".")), file2.getPath(), ""));
            }
        }
        this.loadingDialog.show();
        new GetAllThemeOnline(this, new GetAllThemeOnline.ThemeResult() {
            @Override
            public final void onResult(ArrayList arrayList) {
                VideoActivity.this.lambda$initData$0$VideoActivity(arrayList);
            }
        }).execute(new Void[0]);
    }

    public void lambda$initData$0$VideoActivity(ArrayList arrayList) {
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                Iterator<ItemTheme> it = this.arr.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ItemTheme next = it.next();
                    if (next != null && next.getName().equals(((ItemTheme) arrayList.get(size)).getName())) {
                        arrayList.remove(size);
                        break;
                    }
                }
            }
            this.arr.addAll(arrayList);
            this.adapterThemes.notifyDataSetChanged();
            if (this.loadingDialog.isShowing()) {
                this.loadingDialog.cancel();
            }
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.im_photo) {
            new DialogGallery(this, new DialogGallery.GalleryResult() {
                @Override
                public void onPhoto() {
                    Intent addCategory = new Intent("android.intent.action.OPEN_DOCUMENT").setType("image/*").addCategory("android.intent.category.OPENABLE");
                    addCategory.putExtra("android.intent.extra.MIME_TYPES", new String[]{"image/jpeg", "image/png"});
                    VideoActivity videoActivity = VideoActivity.this;
                    videoActivity.startActivityForResult(Intent.createChooser(addCategory, videoActivity.getString(R.string.app_name)), 1);
                }

                @Override
                public void onVideo() {
                    Intent addCategory = new Intent("android.intent.action.OPEN_DOCUMENT").setType("video/*").addCategory("android.intent.category.OPENABLE");
                    addCategory.putExtra("android.intent.extra.MIME_TYPES", new String[]{"video/*"});
                    VideoActivity videoActivity = VideoActivity.this;
                    videoActivity.startActivityForResult(Intent.createChooser(addCategory, videoActivity.getString(R.string.app_name)), 2);
                }
            }).show();
        } else if (view.getId() == R.id.im_setting) {
            startActivity(new Intent(this, SettingActivity.class));
        }
    }

    @Override
    public void onApply(int i, String str) {
        ItemTheme itemTheme = this.arr.get(i);
        if (!itemTheme.getThumb().isEmpty()) {
            this.loadingDialog.show();
            new DownloadGif().download(this, itemTheme.getLink(), this.path, itemTheme.getName(), new DownloadGif.DownloadResult() {
                @Override
                public void result(String str2) {
                    VideoActivity.this.loadingDialog.setText(VideoActivity.this.getString(R.string.loading));
                    VideoActivity.this.initData();
                    VideoActivity.this.pathTheme = str2;
                    VideoActivity.this.showAds();
                }

                @Override
                public void progress(String str2) {
                    VideoActivity.this.loadingDialog.setText(str2);
                }
            });
            return;
        }
        this.pathTheme = itemTheme.getLink();
        showAds();
    }


    public void showAds() {
        int i = this.count + 1;
        this.count = i;
        action();
    }


    public void action() {
        Intent intent = new Intent(this, ShowActivity.class);
        intent.putExtra("data", this.pathTheme);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            try {
                if (intent.getData() != null) {
                    if (i == 1) {
                        File file = new File(this.pathPhoto);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        try {
                            File file2 = new File(this.pathPhoto + "/" + getString(R.string.app_name) + "/");
                            if (!file2.exists()) {
                                file2.mkdirs();
                            }
                            UCrop.of(intent.getData(), Uri.fromFile(new File(getCacheDir(), System.currentTimeMillis() + ".jpg"))).withMaxResultSize(1500, 1500).start(this);
                        } catch (Exception unused) {
                            Toast.makeText(this, "Error: Can not open image", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        MyShare.putPhoto(this, FileUltils.getPath(this, intent.getData()));
                    }
                    Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                    return;
                } else if (i == 69) {
                    try {
                        InputStream openInputStream = getContentResolver().openInputStream(UCrop.getOutput(intent));
                        Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream, null, null);
                        if (decodeStream != null) {
                            String str = System.currentTimeMillis() + ".jpg";
                            OtherUntil.saveFile(this.pathPhoto + str, decodeStream);
                            MyShare.putPhoto(this, this.pathPhoto + str);
                            openInputStream.close();
                            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                } else {
                    return;
                }
            } catch (Exception unused2) {
                Toast.makeText(this, "Error: Can not open image", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "Error: Can not open image", Toast.LENGTH_SHORT).show();
        }
    }

    private void setColorNavi() {
        if (MyShare.getDark(this)) {
            this.rlMain.setBackgroundColor(getColor(R.color.color_divider_dark));
            getWindow().setNavigationBarColor(getColor(R.color.color_divider_dark));
            getWindow().getDecorView().setSystemUiVisibility(0);
            getWindow().setStatusBarColor(getColor(R.color.color_divider_dark));
            return;
        }
        this.rlMain.setBackgroundColor(-1);
        getWindow().setNavigationBarColor(getColor(R.color.colorNavi));
        getWindow().setStatusBarColor(-1);
        getWindow().getDecorView().setSystemUiVisibility(8192);
    }


}
