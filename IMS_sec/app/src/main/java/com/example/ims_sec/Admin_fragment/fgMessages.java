package com.example.ims_sec.Admin_fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ims_sec.Model.ContactModel;
import com.example.ims_sec.Model.MessageModel;
import com.example.ims_sec.R;
import com.example.ims_sec.ViewPagerAdapter.ContactViewHolder.ItemHolder;
import com.example.ims_sec.ViewPagerAdapter.ContactViewHolder.MessageHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;


public class fgMessages extends Fragment {

    private ImageButton send;
    private EditText message;
    private Boolean dataSent = true;



    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerOptions<MessageModel> options;
    FirebaseRecyclerAdapter<MessageModel, MessageHolder> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fg_messages, container, false);
        send = view.findViewById(R.id.btnSend);
        message = view.findViewById(R.id.message);

        recyclerView = view.findViewById(R.id.recyclerMessage);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Message");

        options = new FirebaseRecyclerOptions.Builder<MessageModel>().setQuery(reference,MessageModel.class).build();

        adapter = new FirebaseRecyclerAdapter<MessageModel, MessageHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MessageHolder messageHolder, int i, @NonNull MessageModel messageModel) {
                messageHolder.Message.setText(messageModel.getMessage());
                messageHolder.sender.setText(messageModel.getSender());
                messageHolder.dateAndtime.setText(messageModel.getTimeandDate());

            }
            @NonNull
            @Override
            public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                return new MessageHolder(LayoutInflater.from(getContext()).inflate(R.layout.message_item,parent,false));


            }
        };
        recyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(message.getText().equals(null)){
                    Toast.makeText(getContext(),"Please write some message",Toast.LENGTH_LONG).show();

                }else{
                   dataSent =  sendMessage(message.getText().toString());

                    if(dataSent==true){
                        message.setText("");
                    }
                }
            }
        });
        return view;
    }
    private boolean sendMessage(final String message){
        String adminName = "admin";
        String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());

        dataSent = false;

        HashMap<String,String> data = new HashMap<>();
        data.put("Sender",adminName);
        data.put("Message",message);
        data.put("TimeandDate",currentDateTime);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Message").child(currentDateTime);

        reference.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"Your message has been sent",Toast.LENGTH_SHORT).show();
                    dataSent = true;

                }else {
                    dataSent = false;
                }
            }
        });

        return dataSent;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

