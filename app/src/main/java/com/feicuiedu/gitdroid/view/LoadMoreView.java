package com.feicuiedu.gitdroid.view;

/**
 * Created by Administrator on 2016/7/2.
 */
public interface LoadMoreView<T> {
    //显示加载中
    void showLoadMoreView();
    //显示加载错误视图
    void showLoadMoreErro(String str);
    //显示没有更多的数据视图
    void showLoadEnd();
    //隐藏加载更多的视图
    void hideLoadMore();
    //添加数据后的界面
    void addMoreData(T data);
;}
