package com.demo.example.OS;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.demo.example.R;
import com.demo.example.OS.fragment.FragmentSetting;
import com.demo.example.OS.until.MyShare;


public class SettingActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_setting);


        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.bannerAd), this);
        adAdmob.FullscreenAd(this);

        setColorNavi();
        showFragment(new FragmentSetting(), false);

    }

    public void showFragment(Fragment fragment, boolean z) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.frame, fragment);
        if (z) {
            beginTransaction.addToBackStack(fragment.getTag());
        }
        beginTransaction.commit();
    }

    private void setColorNavi() {
        if (MyShare.getDark(this)) {
            findViewById(R.id.rl_main).setBackgroundColor(-16777216);
            getWindow().setNavigationBarColor(getColor(R.color.colorNavi_dark));
            getWindow().getDecorView().setSystemUiVisibility(0);
            getWindow().setStatusBarColor(-16777216);
            return;
        }
        findViewById(R.id.rl_main).setBackgroundColor(-1);
        getWindow().setNavigationBarColor(getColor(R.color.colorNavi));
        getWindow().setStatusBarColor(-1);
        getWindow().getDecorView().setSystemUiVisibility(8192);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
