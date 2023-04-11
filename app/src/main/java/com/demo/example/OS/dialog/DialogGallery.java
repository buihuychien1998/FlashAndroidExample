package com.demo.example.OS.dialog;

import android.content.Context;
import android.os.Bundle;

import com.demo.example.R;


public class DialogGallery extends BaseDialogIOS {
    private final GalleryResult galleryResult;

    
    public interface GalleryResult {
        void onPhoto();

        void onVideo();
    }

    public DialogGallery(Context context, GalleryResult galleryResult) {
        super(context);
        if (getWindow() != null) {
            getWindow().setNavigationBarColor(context.getResources().getColor(R.color.colorNavi));
            getWindow().setStatusBarColor(-1);
        }
        this.galleryResult = galleryResult;
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addText(10, getContext().getString(R.string.photo), getContext().getResources().getColor(R.color.c_red));
        addDivider();
        addText(11, getContext().getString(R.string.video), getContext().getResources().getColor(R.color.c_red));
    }

    @Override
        
    void action(int i) {
        if (i == 10) {
            this.galleryResult.onPhoto();
        } else if (i == 11) {
            this.galleryResult.onVideo();
        }
    }
}
