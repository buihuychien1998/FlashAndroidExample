package com.demo.example.OS.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.example.OS.AdAdmob;
import com.demo.example.R;
import com.demo.example.OS.adapter.AdapterStyle;
import com.demo.example.OS.item.ItemStyle;
import com.demo.example.OS.until.MyShare;

import java.util.ArrayList;


public class FragmentStyle extends Fragment {
    private AdapterStyle adapterStyle;
    private ArrayList<ItemStyle> arrStyle;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.arrStyle = new ArrayList<>();
        this.arrStyle.add(new ItemStyle(R.drawable.im_style_0, true));
        this.arrStyle.add(new ItemStyle(R.drawable.im_style_1, true));
        this.arrStyle.add(new ItemStyle(R.drawable.im_pixel, true));
        this.arrStyle.add(new ItemStyle(R.drawable.im_style4, true));


        AdAdmob adAdmob = new AdAdmob(getActivity());
        adAdmob.FullscreenAd(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_style, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (MyShare.getDark(getContext())) {
            ((TextView) view.findViewById(R.id.tv_title_lag)).setTextColor(-1);
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        AdapterStyle adapterStyle = new AdapterStyle(this.arrStyle, new AdapterStyle.StyleResult() {
            @Override
            public final void onItemStyleClick(int i) {
                FragmentStyle.this.lambda$onViewCreated$1$FragmentStyle(i);
            }
        });
        this.adapterStyle = adapterStyle;
        recyclerView.setAdapter(adapterStyle);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
    }

    public void lambda$onViewCreated$1$FragmentStyle(int i) {
        if (getContext() != null && i != MyShare.getStyle(getContext())) {
            MyShare.putStyle(getContext(), i);
            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
            this.adapterStyle.notifyDataSetChanged();

        }
    }


}
