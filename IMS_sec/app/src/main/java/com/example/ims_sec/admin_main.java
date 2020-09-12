package com.example.ims_sec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.ims_sec.Admin_fragment.fgContacts;
import com.example.ims_sec.Admin_fragment.fgMessages;
import com.example.ims_sec.Admin_fragment.fgStocks;
import com.example.ims_sec.ViewPagerAdapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class admin_main extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        tabLayout = findViewById(R.id.tablayoutid);
        viewPager = findViewById(R.id.viewpager);


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new fgStocks(),"Stocks");
        adapter.AddFragment(new fgMessages(),"Messages");
        adapter.AddFragment(new fgContacts(),"Contacts");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.getTabAt(0).setIcon(R.drawable.stock);
        tabLayout.getTabAt(1).setIcon(R.drawable.message);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_action_name);




    }
}
