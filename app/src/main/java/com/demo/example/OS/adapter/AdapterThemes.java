package com.demo.example.OS.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.demo.example.R;
import com.demo.example.OS.item.ItemTheme;
import java.util.ArrayList;



public class AdapterThemes extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<ItemTheme> arr;
    private final ThemeResult themeResult;

    
    public interface ThemeResult {
        void onApply(int i, String str);
    }

    public AdapterThemes(ArrayList<ItemTheme> arrayList, ThemeResult themeResult) {
        this.arr = arrayList;
        this.themeResult = themeResult;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gv, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Holder holder = (Holder) viewHolder;
        ItemTheme itemTheme = this.arr.get(i);
        if (itemTheme.getThumb().isEmpty()) {
            holder.tvApply.setText(R.string.apply);
            holder.tvApply.setTextColor(holder.tvApply.getContext().getColor(R.color.c_selected));
            Glide.with(holder.gifImageView.getContext()).load(itemTheme.getLink()).placeholder((int) R.drawable.ic_load_theme).into(holder.gifImageView);
            return;
        }
        holder.tvApply.setText(R.string.download);
        holder.tvApply.setTextColor(holder.tvApply.getContext().getColor(R.color.c_red));
        Glide.with(holder.gifImageView.getContext()).load(itemTheme.getThumb()).placeholder((int) R.drawable.ic_load_theme).into(holder.gifImageView);
    }

    @Override 
    public int getItemCount() {
        return this.arr.size();
    }

    
    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView gifImageView;
        TextView tvApply;

        Holder(View view) {
            super(view);
            this.gifImageView = (ImageView) view.findViewById(R.id.gif_view);
            this.tvApply = (TextView) view.findViewById(R.id.tv_apply);
            int dimension = (int) view.getContext().getResources().getDimension(R.dimen.pa);
            int i = (view.getContext().getResources().getDisplayMetrics().widthPixels - (dimension * 3)) / 2;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i, (i * 640) / 360);
            layoutParams.addRule(14);
            layoutParams.setMargins(0, dimension, 0, 0);
            this.gifImageView.setLayoutParams(layoutParams);
            this.gifImageView.setOnClickListener(this);
        }

        public void onClick(View view) {
            AdapterThemes.this.themeResult.onApply(getLayoutPosition(), this.tvApply.getText().toString());
        }
    }
}
