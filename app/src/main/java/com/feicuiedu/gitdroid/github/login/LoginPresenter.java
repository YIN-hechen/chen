package com.feicuiedu.gitdroid.github.login;

import android.util.Log;

import com.feicuiedu.gitdroid.github.login.model.AccessTokenResult;
import com.feicuiedu.gitdroid.github.login.model.CurrentUser;
import com.feicuiedu.gitdroid.github.login.model.User;
import com.feicuiedu.gitdroid.network.GitHubApi;
import com.feicuiedu.gitdroid.network.GithubClient;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/5.
 *
 *   P   处理登录做逻辑的   在登录过程中,触发调用LoginView
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView>{


    private Call<AccessTokenResult> TokenCall;
    private Call<User> userCall;

    public void login (String code){
         getView().showProgress();
        TokenCall = GithubClient.getInstance().getOAuthToken(GitHubApi.CLIENT_ID, GitHubApi.CLIENT_SECRET, code);
        TokenCall.enqueue(tokenCallback);

    }

    // 获取AccessToken的回调
    private Callback<AccessTokenResult> tokenCallback = new Callback<AccessTokenResult>() {
        @Override public void onResponse(Call<AccessTokenResult> call, Response<AccessTokenResult> response) {
            // 保存token到内存里面
            String token = response.body().getAccessToken();
            Log.d("LoginPresenter", "token"+token);
            CurrentUser.setAccessToken(token);
//            //再次业务操作,再使用令牌去获取当前已认证的用户信息,从而拿到名字 头像
            if (userCall != null) userCall.cancel();
            userCall = GithubClient.getInstance().getUserInfo();
            userCall.enqueue(userCallback);
        }

        @Override public void onFailure(Call<AccessTokenResult> call, Throwable t) {
            Log.d("LoginPresenter", "失败调用");
            getView().showMessage("Fail:" + t.getMessage());
            // 失败，重置WebView
            getView().showProgress();
            getView().resetWeb();

        }
    };

    // 获取用户信息的回调
    private Callback<User> userCallback = new Callback<User>() {
        @Override public void onResponse(Call<User> call, Response<User> response) {
            // 保存user到内存里面
            User user = response.body();
            CurrentUser.setUser(user);
            // 导航至主页面
            getView().showMessage("登陆成功");
            getView().navigateToMain();
        }

        @Override public void onFailure(Call<User> call, Throwable t) {
            // 清除缓存的用户信息，
            CurrentUser.clear();
            getView().showMessage("Fail:" + t.getMessage());
            // 重置WebView
            getView().showProgress();
            getView().resetWeb();
        }
    };
    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance) {
            if (TokenCall != null) TokenCall.cancel();
            if (userCall != null) userCall.cancel();
        }
    }


}
