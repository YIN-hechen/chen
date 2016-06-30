package com.feicuiedu.gitdroid.home;

import android.os.AsyncTask;

import com.feicuiedu.gitdroid.view.PtrPageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * 这里是MVP中P   做逻辑的
 * 具体要做的业务(下拉刷新获取数据, 上拉加载更多数据), 以及视图的触发
 * Created by Administrator on 2016/7/3.
 */
public class ReopListPresenter {
   private PtrPageView ptrPageView;
    public ReopListPresenter(PtrPageView ptrPageView) {
        this.ptrPageView=ptrPageView;
    }


    // 这是下拉刷新视图层的业务逻辑-----------------------------------------------------------
    public void loadData() {
        new LoadDataTask().execute();
    }
    //这是下拉刷新的逻辑
    private static int count = 0;
   private class  LoadDataTask extends AsyncTask<Void,Void,List<String>> {
       @Override
       protected List<String> doInBackground(Void... params) {
           try {
               Thread.sleep(2000);
           } catch (InterruptedException e) {
               e.printStackTrace();///这里应该显示异常视图
           }
           int size=new Random().nextInt(10);
           ArrayList<String> data = new ArrayList<>();
           for (int i = 0; i < size; i++) {
               data.add("我是刷新的第" + (++count) + "条数据");
           }
         return data;
       }
       @Override
       protected void onPostExecute(List<String> strings) {
           super.onPostExecute(strings);
           int size = strings.size();
           if (size == 2) {
               ptrPageView.showEmptyView();//显示空白视图
           } else if (size % 3 == 0) {
               ptrPageView.showErooView();  //显示错误视图
           } else {
               ptrPageView.showContentView();
               ptrPageView.refreshData(strings);   //视图进行刷新后视图
           }
           ptrPageView.stopRefresh();  //结束刷新
       }
   }





    // 这是上拉加载更多视图层的业务逻辑------------------------------------------------
    public void loadMore() {
        new LoadMoreTask().execute();
    }
    private final class LoadMoreTask extends AsyncTask<Void, Void, List<String>> {

        @Override protected void onPreExecute() {
            super.onPreExecute();
               // 显示加载中...
            ptrPageView.showLoadMoreView();
        }

        @Override protected List<String> doInBackground(Void... params) {
            // 模拟加载更多时,网络连接
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final ArrayList<String> loadDatas = new ArrayList<String>();
            for (int i = 0; i < 10; i++) {
                loadDatas.add("我是加载的第" + i + "条数据");
            }
            return loadDatas;
        }

        @Override protected void onPostExecute(List<String> datas) {
            super.onPostExecute(datas);
            int size=new Random().nextInt(4);
            if (size==2){
                ptrPageView.showLoadEnd();
                return;
            }
            // 将加载到的数据添加到视图上
            ptrPageView.addMoreData(datas);
            // 隐藏加载中....
            ptrPageView.hideLoadMore();
        }
    }
















}
