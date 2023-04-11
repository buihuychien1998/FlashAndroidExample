package com.demo.example.OS.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.demo.example.R;
import com.demo.example.OS.item.ItemAds;
import java.util.List;


public class AdapterAds extends ArrayAdapter<ItemAds> {
    public AdapterAds(Context context, int i, List<ItemAds> list) {
        super(context, i, list);
    }

    @Override 
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_ads, viewGroup, false);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        ItemAds item = getItem(i);
        if (!(item == null || item.getNam() == null)) {
            holder.tv.setText(item.getNam());
            Glide.with(view.getContext()).load(item.getImg()).into(holder.im);
        }
        return view;
    }

    
    private class Holder {
        ImageView im;
        TextView tv;

        Holder(View view) {
            this.im = (ImageView) view.findViewById(R.id.im_md_ads);
            this.tv = (TextView) view.findViewById(R.id.tv_md_ads);
        }
    }
}
