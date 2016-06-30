package com.feicuiedu.gitdroid.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**是HotRepoFragment 布局中ViewPager的适配器
 * Created by Administrator on 2016/6/30.
 */
public class HotRepoPagerAdapter extends FragmentPagerAdapter{
     private List<String> languages;
    public HotRepoPagerAdapter(FragmentManager fm) {
        super(fm);
        languages=new ArrayList<>();
        languages.add("chen1");
        languages.add("chen2");
        languages.add("chen3");
        languages.add("chen4");
        languages.add("chen5");
        languages.add("chen6");
        languages.add("chen7");
    }

    @Override
    public Fragment getItem(int position) {

        return RepoFragment.getInstance(languages.get(position));
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return languages.get(position);
    }
}
