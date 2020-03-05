package com.srtarek00055.mp3player;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageSlideAdapter extends FragmentStatePagerAdapter {
    public PageSlideAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SongFragment();
            case 1:
                return new AlbumFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Song List";
            case 1:
                return "Album List";
            default:
                return null;
        }
    }
}