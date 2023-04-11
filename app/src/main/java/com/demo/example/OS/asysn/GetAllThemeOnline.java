package com.demo.example.OS.asysn;

import android.content.Context;
import android.os.AsyncTask;
import com.demo.example.OS.dialog.LoadingDialog;
import com.demo.example.OS.item.ItemTheme;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class GetAllThemeOnline extends AsyncTask<Void, Void, ArrayList<ItemTheme>> {
    private Context context;
    private LoadingDialog dialogLoading;
    private ThemeResult themeResult;

    
    public interface ThemeResult {
        void onResult(ArrayList<ItemTheme> arrayList);
    }

    public GetAllThemeOnline(Context context, ThemeResult themeResult) {
        this.context = context;
        this.themeResult = themeResult;
        LoadingDialog loadingDialog = new LoadingDialog(context);
        this.dialogLoading = loadingDialog;
        loadingDialog.show();
    }

    
    public ArrayList<ItemTheme> doInBackground(Void... voidArr) {
        ArrayList<ItemTheme> arrayList = null;
        ArrayList<ItemTheme> arrayList2 = new ArrayList<>();
        try {
            arrayList = (ArrayList) new Gson().fromJson(loadData(this.context), new TypeToken<ArrayList<ItemTheme>>() { 
            }.getType());
        } catch (Exception unused) {
        }
        return arrayList != null ? arrayList : arrayList2;
    }

    
    public void onPostExecute(ArrayList<ItemTheme> arrayList) {
        super.onPostExecute( arrayList);
        if (this.dialogLoading.isShowing()) {
            this.dialogLoading.cancel();
        }
        this.themeResult.onResult(arrayList);
    }

    private String loadData(Context context) {
        try {
            InputStream open = context.getAssets().open("th.json");
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return new String(bArr);
        } catch (IOException unused) {
            return "";
        }
    }
}
