package com.feicuiedu.gitdroid.github.home.pager;

import android.util.Log;

import com.feicuiedu.gitdroid.github.home.model.Language;
import com.feicuiedu.gitdroid.github.home.pager.model.Repo;
import com.feicuiedu.gitdroid.github.home.pager.model.RepoResult;
import com.feicuiedu.gitdroid.github.home.pager.view.PtrPageView;
import com.feicuiedu.gitdroid.network.GithubClient;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * 这里是MVP中P   做逻辑的
 * 具体要做的业务(下拉刷新获取数据, 上拉加载更多数据), 以及视图的触发
 * Created by Administrator on 2016/7/3.
 */
public class ReopListPresenter extends MvpNullObjectBasePresenter<PtrPageView> implements MvpPresenter<PtrPageView> {

    private Language mLanguage;
    private Call<RepoResult> reposCall;
    private int nextPage = 0;
    public ReopListPresenter( Language mLanguage) {
        this.mLanguage=mLanguage;
    }

    // 这是下拉刷新视图层的业务逻辑-----------------------------------------------------------
    public void loadData() {
        getView().showContentView(); // 显示内容(显示出列表)
        nextPage = 1; // 刷新永远是第1页
        reposCall = GithubClient.getInstance().searchRepo("language:"+mLanguage.getPath(), nextPage);
        reposCall.enqueue(reposCallback);
    }
    private Callback<RepoResult> reposCallback = new Callback<RepoResult>() {
        @Override public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            Log.d("chenchen", "onResponse:" + " ");
            getView().stopRefresh();// 视图停止刷新
            RepoResult repoResult = response.body();
            if(repoResult == null){
                getView().showEmptyView();
                return;
            }
            // 当前搜索的语言下，没有任何仓库
            if(repoResult.getTotalCount() <= 0){
                getView().refreshData(null);
                getView().showEmptyView();
                return;
            }
            // 取出当前搜索的语言下，所有仓库
            List<Repo> repoList = repoResult.getRepoList();
            getView().refreshData(repoList); // 视图数据刷新
            nextPage = 2; // 下拉刷新成功，当前为第1页,下一页则为第2页
        }

        @Override public void onFailure(Call<RepoResult> call, Throwable t) {
            getView().stopRefresh(); // 视图停止刷新
            getView().showErooView();
        }
    };


    // 这是上拉加载更多视图层的业务逻辑------------------------------------------------
    public void loadMore() {
        getView().showLoadMoreView();
        reposCall = GithubClient.getInstance().searchRepo("language:"+mLanguage.getPath(), nextPage);
        reposCall.enqueue(loadMoreCallback);
    }

    // 上拉加载的回调
    private Callback<RepoResult> loadMoreCallback = new Callback<RepoResult>() {
        @Override public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            getView().hideLoadMore();
            RepoResult result = response.body();
            if (result == null) {
                getView().showLoadMoreErro("result is null");
                return;
            }
            // 没有更多数据了
            if(result.getTotalCount() <= 0){
                getView(). showLoadEnd();
                return;
            }
            // 取出当前搜索的语言下，所有仓库
            List<Repo> repoList = result.getRepoList();
            getView().addMoreData(repoList);
            nextPage ++;
        }

        @Override public void onFailure(Call<RepoResult> call, Throwable t) {
            getView().hideLoadMore();
            getView().showLoadMoreErro(t.getMessage());
        }
    };


}
