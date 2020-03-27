package com.byxiaorun.ctravel.Fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.byxiaorun.ctravel.Adapter.NoteAdapter;
import com.byxiaorun.ctravel.MainActivity;
import com.byxiaorun.ctravel.Utils.NoteDB;

/**
 * Created by byxiaorun on 2020/1/4
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private final int pcount = 3;
    private HomeFragment homeFragment = null;
    private RouteFragment routeFragment = null;
    private AboutFragment aboutFragment = null;
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        homeFragment = new HomeFragment();
        routeFragment = new RouteFragment();
        aboutFragment = new AboutFragment();
    }
    public void setroute(){
        routeFragment.re();
    }
    public void setnote(){
        homeFragment.re();
    }

    @Override
    public int getCount() {
        return pcount;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case MainActivity.PAGE_ONE:
                fragment = homeFragment;
                break;
            case MainActivity.PAGE_TWO:
                fragment = routeFragment;
                break;
            case MainActivity.PAGE_THREE:
                fragment = aboutFragment;
                break;
        }
        return fragment;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemPosition(Object object)
    {
        return POSITION_NONE;
    }

}
