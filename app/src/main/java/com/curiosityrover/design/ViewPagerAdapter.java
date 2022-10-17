package com.curiosityrover.design;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.curiosityrover.fragments.DataFetchFragment;
import com.curiosityrover.fragments.ResultFragment;
import com.curiosityrover.fragments.SavedDataFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new DataFetchFragment();
            case 1: return new ResultFragment();
            case 2: return new SavedDataFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Home";
            case 1: return "Result";
            case 2: return "Saved";
        }
        return null;
    }
}
