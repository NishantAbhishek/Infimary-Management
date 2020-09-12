package com.example.ims_sec.Employee_fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ims_sec.Model.MessageModel;
import com.example.ims_sec.R;
import com.example.ims_sec.ViewPagerAdapter.ContactViewHolder.MessageHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;


public class EmployeeMessage extends Fragment {

    private String Username;
    private EditText message;
    private ImageButton send;

    private FirebaseRecyclerAdapter<MessageModel, MessageHolder> adapter;
    private FirebaseRecyclerOptions<MessageModel> options;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_employee_messages, container, false);

        message = view.findViewById(R.id.message);
        send = view.findViewById(R.id.btnSend);

        recyclerView = view.findViewById(R.id.recyclerMessage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

                if(message.getText().toString()!=null){
                    sendMessage(message.getText().toString());
                }else {
                    Toast.makeText(getContext(),"Please fill all the details",Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    private void sendMessage(final String message){

        String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Message").child(currentDateTime);

        HashMap<String,String> data = new HashMap<>();
        data.put("Message",message);
        data.put("Sender",Username);
        data.put("TimeandDate",currentDateTime);

        reference.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"Message successfully sent",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();

        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Username = dataSnapshot.child("Name").toString();
                Toast.makeText(getContext(),"Welcome back "+Username,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
