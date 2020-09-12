package com.example.ims_sec.ViewPagerAdapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.ims_sec.Model.ItemEmployee;
import com.example.ims_sec.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import static java.lang.Integer.*;
public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.Viewholder>
{
    private List<ItemEmployee> itemList;
    private Context mContext;
    public EmployeeAdapter(List itemList, Context mContext){
        this.itemList = itemList;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_admin_edit,parent,false);
        return new EmployeeAdapter.Viewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        Glide.with(mContext).load(itemList.get(position).getImageUrl()).into(holder.itemImg);
        holder.itemName.setText(itemList.get(position).getItemName());
        holder.itemType.setText(itemList.get(position).getItemType());
        holder.numberOfitem.setText(itemList.get(position).getItemNumber());

        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Opendialog(itemList.get(position).getItemName().toUpperCase(), itemList.get(position).getItemNumber());
            }
        });
    }

    private void Opendialog(final String nodename,final  String numberOfItem){
        NumberPicker numberPicker = new NumberPicker(mContext);
        Button buttonConfirm = new Button(mContext);

        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_remove_item);

        numberPicker = dialog.findViewById(R.id.numberPiceker);
        buttonConfirm = dialog.findViewById(R.id.confirm);
        dialog.show();

        final int numberRemaining = parseInt(numberOfItem);

        String[] data = new String[numberRemaining];

        numberPicker.setMaxValue(numberRemaining-1);
        numberPicker.setMinValue(0);

        for (int a = 0; a < (numberRemaining) ;a++){
            data[a] = Integer.toString(a);
        }

        numberPicker.setDisplayedValues(data);


        final NumberPicker finalNumberPicker = numberPicker;
        final int[] picked = new int[1];

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int pickerValue = finalNumberPicker.getValue();
                picked[0] = pickerValue;

                Toast.makeText(mContext,"PickerValue is" + picked[0] ,Toast.LENGTH_SHORT).show();



            }
        });


        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Items").child(nodename);
                HashMap<String,Object> update = new HashMap<>();
                update.put("ItemNumber",Integer.toString(numberRemaining-picked[0]));
                reference.updateChildren(update);
            }
        });
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        public ImageView itemImg;
        public TextView itemName,itemType,numberOfitem;
        public Button btnedit;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.imgItemPicture_item);
            itemName = itemView.findViewById(R.id.tvItemName_item);
            itemType = itemView.findViewById(R.id.tvChooseItemtype_item);
            numberOfitem = itemView.findViewById(R.id.tvItemNumber_item);
            btnedit = itemView.findViewById(R.id.btn_editItem);
        }
    }
}
