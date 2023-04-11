package com.demo.example.OS.dialog;

import android.content.Context;
import android.os.Bundle;

import com.demo.example.R;


public class DialogRecordSel extends BaseDialogIOS {
    private final SelResult selResult;

    
    public interface SelResult {
        void onExportFile();

        void onPlay();
    }

    public DialogRecordSel(Context context, SelResult selResult) {
        super(context);
        if (getWindow() != null) {
            getWindow().setNavigationBarColor(context.getResources().getColor(R.color.colorNavi));
            getWindow().setStatusBarColor(-1);
        }
        this.selResult = selResult;
    }

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addText(10, getContext().getString(R.string.play), getContext().getResources().getColor(R.color.c_red));
        addDivider();
        addText(11, getContext().getString(R.string.export_file), getContext().getResources().getColor(R.color.c_red));
    }

    @Override 
    void action(int i) {
        if (i == 10) {
            this.selResult.onPlay();
        } else if (i == 11) {
            this.selResult.onExportFile();
        }
    }
}
