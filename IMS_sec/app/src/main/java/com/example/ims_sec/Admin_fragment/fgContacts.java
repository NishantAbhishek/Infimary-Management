package com.example.ims_sec.Admin_fragment;

import android.app.ProgressDialog;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ims_sec.Model.ContactModel;
import com.example.ims_sec.R;
import com.example.ims_sec.ViewPagerAdapter.ContactViewHolder.ContactHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class fgContacts extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter <ContactModel, ContactHolder> adapter;
    private FirebaseRecyclerOptions<ContactModel> options;
    ProgressDialog pd;








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_fg_contacts, container, false);
        recyclerView  = view.findViewById(R.id.recyclerview_contacts);

        recyclerView.setHasFixedSize(true);//claiming whether it should have fixed size or not
        layoutManager = new LinearLayoutManager(getContext());//how the data will be arranged
        recyclerView.setLayoutManager(layoutManager);//setting the recycler view in layout manager

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

        options = new FirebaseRecyclerOptions.Builder<ContactModel>().
                setQuery(reference,ContactModel.class).build();
        //The firebaseRecyclerOptions controls how FirebaseUIpopulates the recyclerview.It passes the query and the  Java class that will be instantiated to hold the data
        adapter = new FirebaseRecyclerAdapter<ContactModel, ContactHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ContactHolder contactHolder, int i, @NonNull ContactModel contactModel) {
                contactHolder.UID.setText(" ID : "+contactModel.getUserId());
                contactHolder.txtPhone.setText(contactModel.getPhone());
                contactHolder.txtUserName.setText(contactModel.getName());
            }
            @NonNull
            @Override
            public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                pd.dismiss();
                return new ContactHolder(LayoutInflater.from(getContext()).inflate(R.layout.contact_item,parent,false));
            }
        };
        recyclerView.setAdapter(adapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        pd = new ProgressDialog(getContext());
        pd.setMessage("Retrieving Contacts......");
        pd.setCancelable(false);
        pd.show();
        adapter.startListening();
    }
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}
