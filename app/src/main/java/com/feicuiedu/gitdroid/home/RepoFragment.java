package com.feicuiedu.gitdroid.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.view.PtrPageView;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * ViewPager中要切换的Fragment
 * Created by Administrator on 2016/6/30.
 */
public class RepoFragment extends Fragment implements PtrPageView{
    @Bind(R.id.lvRepos)
    ListView listView;
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;//下拉刷新
    @Bind(R.id.emptyView)
    TextView emptyView;  //空白视图
    @Bind(R.id.errorView)
    TextView errorView;   //错误视图
    private ArrayAdapter<String> adapter;
    private ArrayList<String> data;
    private FooterView mFooterView;//这是上拉加载自定义的视图
    private ReopListPresenter mReopListPresenter;//这是给视图做逻辑的


    public static RepoFragment getInstance(String valus) {
        RepoFragment repoFragment = new RepoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_valus", valus);
        repoFragment.setArguments(bundle);
        return repoFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加的数据
        data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("我是第" + i + "条数据");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mReopListPresenter=new ReopListPresenter(this);

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        //下拉刷新的回调
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override//下拉刷新的回调方法
            public void onRefreshBegin(PtrFrameLayout frame) {
               mReopListPresenter.loadData();
            }
        });
        mFooterView=new FooterView(getContext());

        //上拉加载的回调(滑动最后位置调用)
        Mugen.with(listView, new MugenCallbacks() {
            @Override//当listview最底下时调用
            public void onLoadMore() {
                Log.d("hjkl;", "onLoadMore: ");
             mReopListPresenter.loadMore();
            }

            @Override//是否正在加载 ,用来避免重复加载
            public boolean isLoading() {
                Log.d("hjkl;", "isLoading: ");
                Log.d("hjkl;", "isLoading: "+(listView.getFooterViewsCount()>0&&mFooterView.isLoading()));
                return listView.getFooterViewsCount()>0&&mFooterView.isLoading();
            }

            @Override//是否所有数据都已加载完
            public boolean hasLoadedAllItems() {
                Log.d("hjkl;", "hasLoadedAllItems: ");
                Log.d("hjkl;", "hasLoadedAllItems: "+(listView.getFooterViewsCount()>0&&mFooterView.isComplete()));
                return listView.getFooterViewsCount()>0&&mFooterView.isComplete();
            }
        }).start();
    }


//点击空或错误视图进行重新刷新
    @OnClick({R.id.emptyView,R.id.errorView})
    public void autoRefresh(){
        ptrClassicFrameLayout.autoRefresh();//刷新
    }


    //这是下拉刷新视图的实现-------------------------------------------------------------------------
    @Override//内容视图
    public void showContentView() {
        ptrClassicFrameLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }
    @Override//错误视图
    public void showErooView() {
        ptrClassicFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }
    @Override//空视图
    public void showEmptyView() {
        ptrClassicFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }
    @Override//刷新数据后视图
    public void refreshData(List<String> data) {
        adapter.addAll(data);
    }
    @Override// 下拉刷新完成
    public void stopRefresh() {
        ptrClassicFrameLayout.refreshComplete();
    }


    //这是上拉加载视图的实现-------------------------------------------------------------------------

    @Override//显示加载中视图
    public void showLoadMoreView() {
        if (listView.getFooterViewsCount()==0){
            listView.addFooterView(mFooterView);
        }
        mFooterView.showLoading();
    }

    @Override//显示错误视图
    public void showLoadMoreErro(String str) {
        if (listView.getFooterViewsCount()==0){
            listView.addFooterView(mFooterView);
        }

        mFooterView.showError(str);
    }

    @Override//显示没有更多数据视图
    public void showLoadEnd() {
        if (listView.getFooterViewsCount()==0){
            listView.addFooterView(mFooterView);
        }
        mFooterView.showComplete();
    }

    @Override//隐藏视图
    public void hideLoadMore() {
        listView.removeFooterView(mFooterView);//加载完隐藏掉

    }

    @Override//加载完数据视图
    public void addMoreData(List<String> data) {
    adapter.addAll(data);
    }
}
