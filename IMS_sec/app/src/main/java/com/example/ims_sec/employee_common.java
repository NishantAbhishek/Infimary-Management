package com.example.ims_sec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.ims_sec.Employee_fragment.EditItemFragment;
import com.example.ims_sec.Employee_fragment.EmployeeMessage;
import com.example.ims_sec.ViewPagerAdapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class employee_common extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_common);

        tabLayout = findViewById(R.id.tablayoutid);
        viewPager = findViewById(R.id.viewpager);


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new EditItemFragment(),"Items");
        adapter.AddFragment(new EmployeeMessage(),"Message");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.stock);
        tabLayout.getTabAt(1).setIcon(R.drawable.message);



    }
}
