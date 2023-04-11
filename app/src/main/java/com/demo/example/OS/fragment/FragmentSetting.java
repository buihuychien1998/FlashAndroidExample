package com.demo.example.OS.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.demo.example.R;
import com.demo.example.OS.ActivitySettingFake;
import com.demo.example.OS.RecorderListActivity;
import com.demo.example.OS.SettingActivity;
import com.demo.example.OS.VideoActivity;
import com.demo.example.OS.until.MyShare;
import com.demo.example.OS.until.OtherUntil;


public class FragmentSetting extends Fragment implements View.OnClickListener {
    private ImageView imDark;
    private ImageView imSound;
    private TextView tvSet;
    private View[] v;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_setting, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        int[] iArr = {R.id.v, R.id.v1, R.id.v2, R.id.v3, R.id.v5, R.id.v6, R.id.v7, R.id.v8, R.id.v9, R.id.v10};
        this.v = new View[10];
        int i = 0;
        while (true) {
            View[] viewArr = this.v;
            if (i >= viewArr.length) {
                break;
            }
            viewArr[i] = view.findViewById(iArr[i]);
            i++;
        }
        this.tvSet = (TextView) view.findViewById(R.id.tv_title_lag);
        view.findViewById(R.id.tv_rate).setOnClickListener(this);
        view.findViewById(R.id.tv_wallpaper).setOnClickListener(this);
        view.findViewById(R.id.tv_fake).setOnClickListener(this);
        view.findViewById(R.id.tv_style).setOnClickListener(this);
        view.findViewById(R.id.tv_list).setOnClickListener(this);
        ImageView imageView = (ImageView) view.findViewById(R.id.im_status_pad);
        this.imSound = imageView;
        imageView.setOnClickListener(this);
        setImageSound();
        ImageView imageView2 = (ImageView) view.findViewById(R.id.im_dark);
        this.imDark = imageView2;
        imageView2.setOnClickListener(this);
        setImage();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_dark:
                MyShare.putDark(getContext(), true ^ MyShare.getDark(getContext()));
                Toast.makeText(getContext(), getString(R.string.reset_app), Toast.LENGTH_SHORT).show();
                if (MyShare.getDark(getContext())) {
                    this.imDark.setImageResource(R.drawable.switch_on);
                    return;
                } else {
                    this.imDark.setImageResource(R.drawable.switch_off);
                    return;
                }
            case R.id.im_status_pad:
                int soundPad = MyShare.getSoundPad(getContext());
                if (soundPad == 0) {
                    MyShare.putSoundPad(getContext(), 1);
                } else if (soundPad == 1) {
                    MyShare.putSoundPad(getContext(), 2);
                } else if (soundPad == 2) {
                    MyShare.putSoundPad(getContext(), 0);
                }
                setImageSound();
                return;
            case R.id.tv_fake:
                startActivity(new Intent(getContext(), ActivitySettingFake.class));
                if (getActivity() != null) {
                    getActivity().finish();
                    return;
                }
                return;
            case R.id.tv_list:
                startActivity(new Intent(getContext(), RecorderListActivity.class));
                return;
            case R.id.tv_rate:
                if (getContext() != null) {

                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getContext().getPackageName())));
                    return;
                }
                return;
            case R.id.tv_style:
                SettingActivity settingActivity2 = (SettingActivity) getActivity();
                if (settingActivity2 != null) {
                    settingActivity2.showFragment(new FragmentStyle(), true);
                    return;
                }
                return;
            case R.id.tv_wallpaper:
                if (OtherUntil.canWriteInMediaStore(getContext())) {
                    startActivity(new Intent(getContext(), VideoActivity.class));
                    return;
                } else if (getActivity() != null) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    private void setImageSound() {
        int soundPad = MyShare.getSoundPad(getContext());
        if (soundPad == 0) {
            this.imSound.setImageResource(R.drawable.pad_mute);
        } else if (soundPad == 1) {
            this.imSound.setImageResource(R.drawable.pad_sound);
        } else if (soundPad == 2) {
            this.imSound.setImageResource(R.drawable.pad_vibration);
        }
    }

    private void setImage() {
        int i = 0;
        if (MyShare.getDark(getContext())) {
            this.tvSet.setTextColor(-1);
            this.imDark.setImageResource(R.drawable.switch_on);
            View[] viewArr = this.v;
            int length = viewArr.length;
            while (i < length) {
                View view = viewArr[i];
                view.setBackgroundColor(view.getContext().getResources().getColor(R.color.color_divider_dark));
                i++;
            }
            return;
        }
        this.tvSet.setTextColor(-16777216);
        this.imDark.setImageResource(R.drawable.switch_off);
        View[] viewArr2 = this.v;
        int length2 = viewArr2.length;
        while (i < length2) {
            View view2 = viewArr2[i];
            view2.setBackgroundColor(view2.getContext().getResources().getColor(R.color.color_divider));
            i++;
        }
    }
}
