package com.bzyness.bzyness.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bzyness.bzyness.fragment.NewBDetailsFragment;
import com.bzyness.bzyness.fragment.NewBLocFragment;
import com.bzyness.bzyness.fragment.NewBOthrDetailsFragment;
import com.bzyness.bzyness.fragment.NewBPhotosFragment;

/**
 * Created by Pervacio on 3/25/2017.
 */

public class NewBDetailsAdapter extends FragmentPagerAdapter {
    final static int PAGE_COUNT=4;
    final static String[] PAGE_TITLES={"Type","Photos","Location","Others"};
    Context context;

    public NewBDetailsAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return NewBDetailsFragment.newInstance(position+1);
            case 1:
                return NewBPhotosFragment.newInstance(position+1);
            case 2:
                return NewBLocFragment.newInstance(position+1);
            case 3:
                return NewBOthrDetailsFragment.newInstance(position+1);
            default:
                return NewBDetailsFragment.newInstance(position+1);
        }

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return PAGE_TITLES[position];
    }
}
