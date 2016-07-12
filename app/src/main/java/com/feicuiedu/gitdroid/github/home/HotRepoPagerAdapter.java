package com.feicuiedu.gitdroid.github.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.feicuiedu.gitdroid.github.home.model.Language;
import com.feicuiedu.gitdroid.github.home.pager.RepoFragment;

import java.util.List;

/**是HotRepoFragment 布局中ViewPager的适配器
 * Created by Administrator on 2016/6/30.
 */
public class HotRepoPagerAdapter extends FragmentPagerAdapter{
     private List<Language> languages;
    public HotRepoPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
       languages=Language.getDefaultLanguage(context);
    }

    @Override
    public Fragment getItem(int position) {

        return RepoFragment.getInstance(languages.get(position));
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override  //这是返回上面的标题
    public CharSequence getPageTitle(int position) {
        return languages.get(position).getName();
    }
}
