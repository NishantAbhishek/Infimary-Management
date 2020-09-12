package com.example.ims_sec.Employee_fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ims_sec.Model.ItemEmployee;
import com.example.ims_sec.R;
import com.example.ims_sec.ViewPagerAdapter.EmployeeAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditItemFragment extends Fragment {
    private List<ItemEmployee> employeeItem;
    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_edit_item, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        employeeItem = new ArrayList<>();
        addingItems();

        adapter = new EmployeeAdapter(employeeItem,getContext());
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
        return view;


    }

    private void addingItems(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Items");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ItemEmployee item = snapshot.getValue(ItemEmployee.class);
                    employeeItem.add(item);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();

    }
}
