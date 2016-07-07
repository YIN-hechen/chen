package com.feicuiedu.gitdroid.github.home.pager.view;

/**
 * 下拉刷新视图抽象接口
 * Created by Administrator on 2016/7/1.
 */
public interface PtrView <T>{

    //显示内容视图
    void showContentView();
    //显示错误视图
    void showErooView();
    //显示空白视图
    void showEmptyView();
    //刷新数据后视图
    void refreshData(T t);
    //停止刷新
    void stopRefresh();
}
