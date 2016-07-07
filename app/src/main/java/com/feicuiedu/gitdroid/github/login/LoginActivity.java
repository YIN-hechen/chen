package com.feicuiedu.gitdroid.github.login;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.github.main.MainActivity;
import com.feicuiedu.gitdroid.network.GitHubApi;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Administrator on 2016/7/5.
 *GitHub会有一个授权URL
 * 这个授权URL在访问后,将重定向另一个URL(授权登录界面)
 *
 *
 *
 *
 *
 *
 */
public class LoginActivity extends MvpActivity<LoginView,LoginPresenter> implements LoginView{
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.webView)WebView webView;
    @Bind(R.id.gifImageView)GifImageView gifImageView;
    private ActivityUtils activityUtils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        activityUtils=new ActivityUtils(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// 显示标题栏左上角的返回按钮
        initWebView();
    }


    // WebView的初始化
    private void initWebView() {
        // 删除所有的Cookie，主要是为了清除以前的登录历史记录
        CookieManager cookieManager=CookieManager.getInstance();
        cookieManager.removeAllCookie();
        // 授权登陆URL
        webView.loadUrl(GitHubApi.AUTH_URL);
        webView.setFocusable(true);//可聚焦
        webView.setFocusableInTouchMode(true);//可触摸

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
              if (newProgress==100){
                 gifImageView.setVisibility(View.GONE);
                  webView.setVisibility(View.VISIBLE);
              }
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override//资源重新加载(为了重定向)
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("setWebViewClient", "重定向开始了 ");
                Uri uri = Uri.parse(url);
                // 检测加载到的新URL是否是用我们规定好的CALL_BACK开头的
                if (GitHubApi.CALL_BACK.equals(uri.getScheme())){
                    // 获取授权码
                    String code = uri.getQueryParameter("code");
                    // 执行登陆的操作Presenter
                    getPresenter().login(code);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });




    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showProgress() {
        gifImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String mes) {
  activityUtils.showToast(mes);
    }

    @Override
    public void resetWeb() {
        initWebView();
    }

    @Override
    public void navigateToMain() {
     activityUtils.startActivity(MainActivity.class);
        finish();
    }
}
