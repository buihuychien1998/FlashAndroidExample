package com.demo.example.OS;

import android.app.role.RoleManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.demo.example.R;
import com.demo.example.OS.adapter.AdapterPer;
import com.demo.example.OS.dialog.DialogSetting;
import com.demo.example.OS.until.OtherUntil;


public class ActivityPerM extends AppCompatActivity {
    private ImageView imDone;
    private ViewPager viewPager;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setNavigationBarColor(getColor(R.color.colorNavi));
        getWindow().setStatusBarColor(-1);
        setContentView(R.layout.activity_per_m);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp);
        this.viewPager = viewPager;
        viewPager.setAdapter(new AdapterPer(getSupportFragmentManager()));
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { 
            @Override 
            public void onPageScrollStateChanged(int i) {
            }

            @Override 
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override 
            public void onPageSelected(int i) {
                TelecomManager telecomManager;
                if (i != 0 && (telecomManager = (TelecomManager) ActivityPerM.this.getSystemService("telecom")) != null && !ActivityPerM.this.getPackageName().equals(telecomManager.getDefaultDialerPackage())) {
                    ActivityPerM.this.viewPager.setCurrentItem(0);
                }
            }
        });
        this.imDone = (ImageView) findViewById(R.id.im_done);
    }

    public void onDoneClick(View view) {
        startActivity(new Intent(this, ActivitySplash.class));
        finish();
    }

    public void action(int i) {
        if (i == 0) {
            TelecomManager telecomManager = (TelecomManager) getSystemService("telecom");
            if (telecomManager == null || getPackageName().equals(telecomManager.getDefaultDialerPackage())) {
                this.viewPager.setCurrentItem(1);
                return;
            }
            if (Build.VERSION.SDK_INT >= 29) {
                RoleManager roleManager = (RoleManager) getSystemService(RoleManager.class);
                if (roleManager.isRoleAvailable("android.app.role.DIALER") && !roleManager.isRoleHeld("android.app.role.DIALER")) {
                    startActivityForResult(roleManager.createRequestRoleIntent("android.app.role.DIALER"), 1);
                    return;
                }
            }
            requestDefault();
        } else if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    if (!OtherUntil.checkPer(this, "android.permission.READ_EXTERNAL_STORAGE") || !OtherUntil.checkPer(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                        ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                    } else {
                        this.imDone.setVisibility(View.VISIBLE);
                    }
                }
            } else if (!OtherUntil.checkPer(this, "android.permission.READ_CONTACTS")) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_CONTACTS"}, 1);
            } else {
                this.viewPager.setCurrentItem(3);
            }
        } else if (!OtherUntil.checkPer(this, "android.permission.CALL_PHONE")) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CALL_PHONE"}, 1);
        } else {
            this.viewPager.setCurrentItem(2);
        }
    }

    @Override 
    protected void onResume() {
        int i;
        super.onResume();
        TelecomManager telecomManager = (TelecomManager) getSystemService("telecom");
        if (telecomManager != null && !getPackageName().equals(telecomManager.getDefaultDialerPackage())) {
            i = 0;
        } else if (!OtherUntil.checkPer(this, "android.permission.CALL_PHONE")) {
            i = 1;
        } else if (!OtherUntil.checkPer(this, "android.permission.READ_CONTACTS")) {
            i = 2;
        } else {
            i = (OtherUntil.checkPer(this, "android.permission.READ_EXTERNAL_STORAGE") || OtherUntil.checkPer(this, "android.permission.WRITE_EXTERNAL_STORAGE")) ? -1 : 3;
        }
        if (i == -1) {
            this.imDone.setVisibility(View.VISIBLE);
            return;
        }
        this.imDone.setVisibility(View.GONE);
        this.viewPager.setCurrentItem(i);
    }

    @Override 
    protected void onActivityResult(int i, int i2, Intent intent) {
        Intent intent2;
        super.onActivityResult(i, i2, intent);
        TelecomManager telecomManager = (TelecomManager) getSystemService("telecom");
        if ((telecomManager != null && getPackageName().equals(telecomManager.getDefaultDialerPackage())) || i2 != 0) {
            return;
        }
        if (i == 123) {
            try {
                if (Build.VERSION.SDK_INT >= 24) {
                    intent2 = new Intent("android.settings.MANAGE_DEFAULT_APPS_SETTINGS");
                } else {
                    intent2 = new Intent("android.settings.APPLICATION_SETTINGS");
                }
                startActivityForResult(intent2, 12);
            } catch (ActivityNotFoundException unused) {
                new DialogSetting(this).show();
            }
        } else if (i == 12) {
            new DialogSetting(this).show();
        }
    }

    private void requestDefault() {
        try {
            Intent intent = new Intent("android.telecom.action.CHANGE_DEFAULT_DIALER");
            intent.putExtra("android.telecom.extra.CHANGE_DEFAULT_DIALER_PACKAGE_NAME", getPackageName());
            startActivityForResult(intent, 123);
        } catch (ActivityNotFoundException unused) {
            new DialogSetting(this).show();
        }
    }
}
