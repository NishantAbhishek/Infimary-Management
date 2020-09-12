package com.example.ims_sec.ViewPagerAdapter.ContactViewHolder;

import android.view.View;
import android.widget.TextView;

import com.example.ims_sec.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageHolder extends RecyclerView.ViewHolder {
    public TextView Message;
    public TextView dateAndtime;
    public TextView sender;


    public MessageHolder(@NonNull View itemView) {
        super(itemView);
        Message = itemView.findViewById(R.id.tvMessage);
        dateAndtime = itemView.findViewById(R.id.tvDateTime);
        sender = itemView.findViewById(R.id.tvName);

    }
}
