package com.feicuiedu.gitdroid.context;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.github.home.pager.model.Repo;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/9.
 */
public class ContentInfoActivity extends MvpActivity<RepoInfoView,RepoInfoPresenter> implements RepoInfoView{
    private ActivityUtils mActivityUtils;  //工具类
    private Repo mRepo;//获得仓库信息(从调用者那)

    @Bind(R.id.ivIcon) ImageView ivIcon;    //图标
    @Bind(R.id.tvRepoInfo) TextView tvRepoInfo;  //描述
    @Bind(R.id.tvRepoStars) TextView tvRepoStars;//关注的数量
    @Bind(R.id.tvRepoName) TextView tvRepoName;  //全名
    @Bind(R.id.webView) WebView webView;  //呵呵
    @Bind(R.id.progressBar)ProgressBar progressBar; //呵呵
    @Bind(R.id.toolbar) Toolbar toolbar; //呵呵
    public static String KEY="KEY";
   public static void intent (Context context,@NonNull Repo mRepo){
       Intent intent = new Intent(context, ContentInfoActivity.class);
       intent.putExtra(KEY,mRepo);
       context.startActivity(intent);
   }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityUtils=new ActivityUtils(this);//工具类
        setContentView(R.layout.activity_repo_info);
         getPresenter().getReadme(mRepo);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
       mRepo = (Repo) getIntent().getSerializableExtra(KEY);//得到仓库信息
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置toolbar的返回键
        ImageLoader.getInstance().displayImage(mRepo.getOwner().getAvatar(), ivIcon);//设置图标
        getSupportActionBar().setTitle(mRepo.getName());//设置toolbar的标题为仓库的名字
        tvRepoName.setText(mRepo.getFullName());//设置全名
        tvRepoInfo.setText(mRepo.getDescription());//设置描述
        tvRepoStars.setText(String.format("关注:%d  拷贝:%d",mRepo.getStargazersCount(), mRepo.getForksCount()));//设置点击 拷贝数量

    }

    @NonNull
    @Override
    public RepoInfoPresenter createPresenter() {
        return new RepoInfoPresenter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
       webView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        webView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setData(String data) {
        webView.loadData(data,"text/html","UTF-8");
    }

    @Override
    public void showMessage(String msg) {
        mActivityUtils.showToast(msg);
    }
}
