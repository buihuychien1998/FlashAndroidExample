package com.demo.example.OS.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.example.R;
import com.demo.example.OS.item.ItemStyle;
import com.demo.example.OS.until.MyShare;
import java.util.ArrayList;



public class AdapterStyle extends RecyclerView.Adapter<AdapterStyle.Holder> {
    private final ArrayList<ItemStyle> arr;
    private final StyleResult styleResult;

    
    public interface StyleResult {
        void onItemStyleClick(int i);
    }

    public AdapterStyle(ArrayList<ItemStyle> arrayList, StyleResult styleResult) {
        this.arr = arrayList;
        this.styleResult = styleResult;
    }

    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_style, viewGroup, false));
    }

    public void onBindViewHolder(Holder holder, int i) {
        ItemStyle itemStyle = this.arr.get(i);
        if (itemStyle != null) {
            holder.imStyle.setImageResource(itemStyle.getImage());
            if (MyShare.getStyle(holder.imStatus.getContext()) == i) {
                holder.imStatus.setImageResource(R.drawable.ic_choose);
            } else if (itemStyle.isFree()) {
                holder.imStatus.setImageResource(R.drawable.ic_free);
            } else {
                holder.imStatus.setImageResource(R.drawable.ic_pro);
            }
        }
    }

    @Override 
    public int getItemCount() {
        return this.arr.size();
    }

    
    
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imStatus;
        ImageView imStyle;

        Holder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.imStyle = (ImageView) view.findViewById(R.id.image);
            int dimension = ((int) (((float) view.getContext().getResources().getDisplayMetrics().widthPixels) - (view.getContext().getResources().getDimension(R.dimen.magin_style) * 6.0f))) / 2;
            this.imStyle.setLayoutParams(new LinearLayout.LayoutParams(dimension, (dimension * 1440) / 720));
            this.imStatus = (ImageView) view.findViewById(R.id.im_status);
        }

        public void onClick(View view) {
            AdapterStyle.this.styleResult.onItemStyleClick(getLayoutPosition());
        }
    }
}
