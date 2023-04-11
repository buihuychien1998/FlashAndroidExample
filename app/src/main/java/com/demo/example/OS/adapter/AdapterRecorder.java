package com.demo.example.OS.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.example.R;
import com.demo.example.OS.custom.AvatarPeople;
import com.demo.example.OS.item.ItemRecorder;
import com.demo.example.OS.until.OtherUntil;
import java.util.ArrayList;



public class AdapterRecorder extends RecyclerView.Adapter<AdapterRecorder.Holder> {
    private ArrayList<ItemRecorder> arr;
    private boolean choose;
    private RecorderItemResult recorderItemResult;

    
    public interface RecorderItemResult {
        void onItemRecorderClick(int i);
    }

    public void setChoose(boolean z) {
        this.choose = z;
    }

    public boolean isChoose() {
        return this.choose;
    }

    public AdapterRecorder(ArrayList<ItemRecorder> arrayList, RecorderItemResult recorderItemResult) {
        this.arr = arrayList;
        this.recorderItemResult = recorderItemResult;
    }

    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recorder, viewGroup, false));
    }

    public void onBindViewHolder(Holder holder, int i) {
        ItemRecorder itemRecorder = this.arr.get(i);
        if (!this.choose || !itemRecorder.isChoose()) {
            holder.av.setImage(itemRecorder.getUri(), itemRecorder.getName());
        } else {
            holder.av.setImage(R.drawable.ic_check);
        }
        if (itemRecorder.getName() == null || itemRecorder.getName().isEmpty()) {
            holder.tvName.setText(itemRecorder.getNum());
        } else {
            holder.tvName.setText(itemRecorder.getName());
        }
        if (itemRecorder.getStatus() == 2) {
            holder.imStatus.setImageResource(R.drawable.ic_call_in_info);
        } else {
            holder.imStatus.setImageResource(R.drawable.ic_call_out_info);
        }
        holder.tvSize.setText(OtherUntil.getReadableFileSize(itemRecorder.getSize()));
        holder.tvTime.setText(OtherUntil.longToTime(holder.tvTime.getContext(), itemRecorder.getTime()));
    }

    @Override 
    public int getItemCount() {
        return this.arr.size();
    }

    
    
    public class Holder extends RecyclerView.ViewHolder {
        AvatarPeople av;
        ImageView imStatus;
        TextView tvName;
        TextView tvSize;
        TextView tvTime;

        Holder(View view) {
            super(view);
            this.av = (AvatarPeople) view.findViewById(R.id.im_avatar);
            this.tvName = (TextView) view.findViewById(R.id.tv_name);
            this.tvTime = (TextView) view.findViewById(R.id.tv_time);
            this.tvSize = (TextView) view.findViewById(R.id.tv_size);
            this.imStatus = (ImageView) view.findViewById(R.id.im_status);
            view.setOnClickListener(new View.OnClickListener() { 
                public void onClick(View view2) {
                    if (!AdapterRecorder.this.choose) {
                        AdapterRecorder.this.recorderItemResult.onItemRecorderClick(Holder.this.getLayoutPosition());
                        return;
                    }
                    ((ItemRecorder) AdapterRecorder.this.arr.get(Holder.this.getLayoutPosition())).setChoose(!((ItemRecorder) AdapterRecorder.this.arr.get(Holder.this.getLayoutPosition())).isChoose());
                    if (((ItemRecorder) AdapterRecorder.this.arr.get(Holder.this.getLayoutPosition())).isChoose()) {
                        Holder.this.av.setImage(R.drawable.ic_check);
                    } else {
                        Holder.this.av.setImage(((ItemRecorder) AdapterRecorder.this.arr.get(Holder.this.getLayoutPosition())).getUri(), ((ItemRecorder) AdapterRecorder.this.arr.get(Holder.this.getLayoutPosition())).getName());
                    }
                }
            });
        }
    }
}
