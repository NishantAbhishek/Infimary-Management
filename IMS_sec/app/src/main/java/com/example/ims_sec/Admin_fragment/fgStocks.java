package com.example.ims_sec.Admin_fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ims_sec.Model.ContactModel;
import com.example.ims_sec.Model.ItemModel;
import com.example.ims_sec.R;
import com.example.ims_sec.ViewPagerAdapter.ContactViewHolder.ItemHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;


public class fgStocks extends Fragment
{
    private static final int getimage= 1;
    CardView addItem;
    Dialog addItemDialog;
    private  Uri ImageData;
    ImageView imgItemPicture;
    ProgressDialog starting;

    //Firbase UserInterface code
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter<ItemModel, ItemHolder> recyclerAdapter;
    private FirebaseRecyclerOptions<ItemModel> options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_fg_stocks, container, false);
        addItem = view.findViewById(R.id.add_item);

        recyclerView = view.findViewById(R.id.stockItemRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        DatabaseReference itemReference = FirebaseDatabase.getInstance().getReference().child("Items");

        options = new FirebaseRecyclerOptions.Builder<ItemModel>().setQuery(itemReference,ItemModel.class).build();

        recyclerAdapter = new FirebaseRecyclerAdapter<ItemModel, ItemHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ItemHolder itemHolder, int i, @NonNull ItemModel itemModel) {
//                contactHolder.UID.setText(" ID : "+contactModel.getUserId());
                itemHolder.itemName.setText("Item Name : "+itemModel.getItemName());
                itemHolder.itemType.setText("Item Type : "+itemModel.getItemType());
                itemHolder.numberOfItem.setText("Number of item needed : "+itemModel.getItemNumber());
                String Url = itemModel.getImageUrl();
                Glide.with(getContext()).load(Url).into(itemHolder.img);
            }

            @NonNull
            @Override
            public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                starting.dismiss();
                return new ItemHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_admin,parent,false));
            }
        };

        recyclerView.setAdapter(recyclerAdapter);

        addItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                addItemPopup();
                return true;
            }
        });

        return view;
    }

    public void addItemPopup()
    {
        final EditText edItemName,edItemNumber,edChooseItemtype;
        TextView txtCross;
        Button BtnsendData;

        addItemDialog = new Dialog(getContext());
        Toast.makeText(getContext(),getContext().toString(),Toast.LENGTH_LONG).show();

        addItemDialog.setContentView(R.layout.dialog_additem);

        edItemName = addItemDialog.findViewById(R.id.edItemName);
        txtCross = addItemDialog.findViewById(R.id.txtCross);
        imgItemPicture = addItemDialog.findViewById(R.id.imgItemPicture);
        edItemNumber = addItemDialog.findViewById(R.id.edItemNumber);
        edChooseItemtype = addItemDialog.findViewById(R.id.edChooseItemtype);
        BtnsendData = addItemDialog.findViewById(R.id.BtnsendData);

        addItemDialog.show();

        imgItemPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_GET_CONTENT);
                in.setType("image/*");
                startActivityForResult(in,getimage);

            }
        });

        txtCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemDialog.dismiss();
            }
        });

        BtnsendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Itemname = edItemName.getText().toString();
                String Itemtype = edChooseItemtype.getText().toString();
                String ItemNumber = edItemNumber.getText().toString();

                if(Itemname.equals("")&&ItemNumber.equals("")&&Itemtype.equals("")&&ImageData.equals(null)){
                    Toast.makeText(getContext(),"Something is missing",Toast.LENGTH_LONG).show();
                }else {
                    SendatatoFirebase(Itemname, Itemtype,ItemNumber,ImageData);
                }
            }
        });
    }

    private void SendatatoFirebase(final String Itemname, final String Itemtype, final String ItemNumber, Uri ImageData)
    {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading data.......");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Items").child(Itemname.toUpperCase());
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Images").child("image_"+ImageData.getLastPathSegment());

        storageReference.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                {
                    @Override
                    public void onSuccess(Uri uri)
                    {
                        HashMap<String ,String> data = new HashMap<>();
                        data.put("ItemName",Itemname);
                        data.put("ItemType",Itemtype);
                        data.put("ItemNumber",ItemNumber);
                        data.put("imageUrl",String.valueOf(uri));

                        reference.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getContext(),"Your data has been uploaded",Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }else{
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == getimage){
            if (resultCode == RESULT_OK){
                ImageData = data.getData();
                imgItemPicture.setImageURI(ImageData);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
        starting = new ProgressDialog(getContext());
        starting.setMessage("Preparing to start");
        starting.setCancelable(true);
        starting.show();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
        starting.dismiss();
    }

}