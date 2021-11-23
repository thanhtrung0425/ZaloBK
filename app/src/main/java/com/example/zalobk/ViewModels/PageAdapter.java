package com.example.zalobk.ViewModels;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.zalobk.Views.chatFragment;
import com.example.zalobk.Views.profileFragment;
import com.example.zalobk.Views.statusFragment;

public class PageAdapter extends FragmentPagerAdapter {

    int tabcount;

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new chatFragment();

            case 1:
                return new statusFragment();
            case  2:
                return new profileFragment();
            default:
                return  null;
        }

    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
