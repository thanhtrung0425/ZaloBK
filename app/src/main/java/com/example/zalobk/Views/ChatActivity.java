package com.example.zalobk.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zalobk.R;
import com.example.zalobk.ViewModels.PageAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class ChatActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private TabItem mChats, mStatus, mProfile;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;

    androidx.appcompat.widget.Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tabLayout = findViewById(R.id.include);
        mChats = findViewById(R.id.chat);
        mStatus = findViewById(R.id.status);
        mProfile = findViewById(R.id.profile);

        viewPager = findViewById(R.id.fragmentcontainer);
        setSupportActionBar(mtoolbar);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0 || tab.getPosition()==1 || tab.getPosition()==2){
                    pageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settins:
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
}