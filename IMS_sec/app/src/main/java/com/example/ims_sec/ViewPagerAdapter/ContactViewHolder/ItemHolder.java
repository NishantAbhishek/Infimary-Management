package com.example.ims_sec.ViewPagerAdapter.ContactViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ims_sec.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemHolder extends RecyclerView.ViewHolder
{
    public TextView itemName;
    public TextView numberOfItem;
    public TextView itemType;
    public ImageView img;

    public ItemHolder(@NonNull View itemView) {
        super(itemView);
        itemName = itemView.findViewById(R.id.tvItemName_item);
        itemType = itemView.findViewById(R.id.tvChooseItemtype_item);
        numberOfItem = itemView.findViewById(R.id.tvItemNumber_item);
        img = itemView.findViewById(R.id.imgItemPicture_item);
    }

}
