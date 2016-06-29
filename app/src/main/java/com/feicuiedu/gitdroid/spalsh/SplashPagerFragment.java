package com.feicuiedu.gitdroid.spalsh;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.spalsh.pager.pager2;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2016/6/29.
 */
public class SplashPagerFragment extends Fragment implements ViewPager.OnPageChangeListener {

   @Bind(R.id.viewPager)ViewPager viewpager;
    @Bind(R.id.indicator)CircleIndicator indicator;//指示器(下面的指示点)
    @Bind(R.id.content)FrameLayout framlayout;    //整个布局的老大  用于显示背景色的变化
    @BindColor(R.color.colorGreen) int colorGreen;   //资源绿色
    @BindColor(R.color.colorRed) int colorRed;       //资源红色
    @BindColor(R.color.colorYellow) int colorYellow; //资源黄色
    ArgbEvaluator mEvaluator=new ArgbEvaluator();//ARGB取值器
    private SplashPagerAdapter mAdapter;//适配器
    @Bind(R.id.layoutPhone)FrameLayout layoutPhone;  //整个手机FrameLayout
    @Bind(R.id.ivPhoneBlank)ImageView ivPhoneBlank;   //手机空白界面
    @Bind(R.id.ivPhone)ImageView ivPhone;           //手机有字界面(隐藏了)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_splash_pager,container,false);

        return view ;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        mAdapter=new SplashPagerAdapter(getContext());
        viewpager.setAdapter(mAdapter);
        viewpager.addOnPageChangeListener(this);
        indicator.setViewPager(viewpager);

    }

    //ViewPager的点击回调
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        /*ViewPager在界面0与界面1之间切换时,position总是为0
         ViewPager在界面1与界面2之间切换时,position总是为1
        * */
        Log.d("hjkl", "position-- "+position+"positionOffset--"+positionOffset+"positionOffsetPixels--"+positionOffsetPixels);
       if (position==0){
           //界面0与界面1之间切换时的颜色变化
           int color = (int) mEvaluator.evaluate(positionOffset, colorGreen, colorRed);
           framlayout.setBackgroundColor(color);
           //界面0与界面1之间切换时的手机图片缩放
           float scale=0.3f+positionOffset*0.7f;
           layoutPhone.setScaleX(scale);
           layoutPhone.setScaleY(scale);
           //界面0与界面1之间切换时的手机图片有字内容的透明度变化
           ivPhone.setAlpha(positionOffset);
           //界面0与界面1之间切换时的手机图片位移变化
           int scroll= (int) ((positionOffset-1)*200);
           layoutPhone.setTranslationX(scroll);
           return;
       }
        if (position==1){
            //界面1与界面2之间切换时的颜色变化
            int color = (int) mEvaluator.evaluate(positionOffset, colorRed, colorYellow);
            framlayout.setBackgroundColor(color);
            //界面1与界面2之间切换时的手机图片位移变化
            layoutPhone.setTranslationX(-positionOffsetPixels);
            return;
        }
    }

    @Override
    public void onPageSelected(int position) {
  if (position==2){
     pager2 pager= (pager2) mAdapter.getView(position);
      pager.showAnimation();
  }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
