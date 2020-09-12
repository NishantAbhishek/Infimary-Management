package com.example.ims_sec.ViewPagerAdapter.ContactViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ims_sec.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactHolder extends RecyclerView.ViewHolder {
    public TextView txtUserName;
    public TextView txtPhone;
    public TextView UID;
    public ImageView icon;



    public ContactHolder(@NonNull View itemView) {
        super(itemView);
        txtUserName = itemView.findViewById(R.id.list_contact_name);
        txtPhone = itemView.findViewById(R.id.list_contact_phone);
        UID = itemView.findViewById(R.id.list_contact_ID);
        icon = itemView.findViewById(R.id.list_contact_callImage);
    }






}
