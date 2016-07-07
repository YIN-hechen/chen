package com.feicuiedu.gitdroid.github.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/7/5.
 *
 *
 *
 *  这是登录页面的视图抽象
 */
public interface LoginView extends MvpView{

//显示进度条
    void showProgress();
    //显示 错误信息
    void showMessage(String mes);
    //重置Web
    void resetWeb();
    //导航至主页面
    void navigateToMain();


}
