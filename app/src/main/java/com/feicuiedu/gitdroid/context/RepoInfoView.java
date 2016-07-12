package com.feicuiedu.gitdroid.context;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**  视图
 * Created by Administrator on 2016/7/9.
 */
public interface RepoInfoView extends MvpView{

    void showProgress();  //显示正在加载
    void hideProgress();   //隐藏加载
    void setData(String data); //显示数据
    void showMessage(String msg); //显示错误(连接网络)
}
