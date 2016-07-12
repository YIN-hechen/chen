package com.feicuiedu.gitdroid.github.home.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.components.FooterView;
import com.feicuiedu.gitdroid.context.ContentInfoActivity;
import com.feicuiedu.gitdroid.github.home.model.Language;
import com.feicuiedu.gitdroid.github.home.pager.model.Repo;
import com.feicuiedu.gitdroid.github.home.pager.view.PtrPageView;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

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
 public  class RepoFragment extends MvpFragment<PtrPageView,ReopListPresenter> implements PtrPageView{
    @Bind(R.id.lvRepos)
    ListView listView;
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;//下拉刷新
    @Bind(R.id.emptyView)
    TextView emptyView;  //空白视图
    @Bind(R.id.errorView)
    TextView errorView;   //错误视图
    RepoAdapter mRepoAdapter;  //ListView的适配器
    private FooterView mFooterView;//这是上拉加载自定义的视图

    private ActivityUtils activityUtils;

    /**
     * 获取(每次重新创建)当前Fragment对象
     * <p/>
     * 当Fragment需要进行参数传递时，应该使用Bundle进行处理,我们这里就是将语言类型传入了(在获取语言仓库列表数据时要用到)
     * <p/>
     */
    //提供本类的对象.并把想要调用者的信息传进来了
    public static RepoFragment getInstance(Language language) {
        RepoFragment repoFragment = new RepoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("key_valus",language);
        repoFragment.setArguments(bundle);
        return repoFragment;
    }
    //把调用者传来的信息拿出来
    private Language getLanguage() {

        return (Language) getArguments().getSerializable("key_valus");
    }

    //mvp回调
    @Override
    public ReopListPresenter createPresenter() {
        return new ReopListPresenter(getLanguage());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils=new ActivityUtils(this);
        mRepoAdapter=new RepoAdapter();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list,container,false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        listView.setAdapter(mRepoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Repo item = mRepoAdapter.getItem(position);
                ContentInfoActivity.intent(getContext(),item);
            }
        });
        //下拉刷新的回调
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getPresenter().loadData();
            }
        });

        //上拉加载的回调(滑动最后位置调用)自定义刷新视图
        mFooterView=new FooterView(getContext());
        // 当加载更多失败时，用户点击FooterView，会再次触发加载
        mFooterView.setErrorClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                getPresenter().loadMore();
            }
        });
        // 使用了一个微型库Mugen来处理滚动监听
        Mugen.with(listView, new MugenCallbacks() {
            // ListView滚动到底部，触发加载更多，此处要执行加载更多的业务逻辑
            @Override//当listview最底下时调用
            public void onLoadMore() {
                Log.d("hjkl;", "onLoadMore: ");
                getPresenter().loadMore();
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
        // 如果当前页面没有数据，开始自动刷新,走的是下拉刷新
        if (mRepoAdapter.getCount() == 0) {
            ptrClassicFrameLayout.postDelayed(new Runnable() {
                @Override public void run() {
                    ptrClassicFrameLayout.autoRefresh();
                }
            }, 200);
        }
    }

    //点击空或错误视图进行重新刷新
    @OnClick({R.id.emptyView,R.id.errorView})
    public void autoRefresh(){
        ptrClassicFrameLayout.autoRefresh();//刷新
    }

    //当视图销毁时,ButterKnife也解除
    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
    public void refreshData(List<Repo> data) {
        mRepoAdapter.clear();
        if (data==null)
            return;
       mRepoAdapter.addAll(data);
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
    public void addMoreData(List<Repo> data) {
        if (data==null)
            return;
    mRepoAdapter.addAll(data);
    }
}
