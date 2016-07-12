package com.feicuiedu.gitdroid.context;

import android.util.Base64;
import android.util.Log;

import com.feicuiedu.gitdroid.github.home.pager.model.Repo;
import com.feicuiedu.gitdroid.network.GithubClient;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 业务  (逻辑)
 * Created by Administrator on 2016/7/9.
 */
public class RepoInfoPresenter extends MvpNullObjectBasePresenter<RepoInfoView> {
    private Call<RepoContentResult> repoContentCall;
    private Call<ResponseBody> mdCall;

    public void getReadme(Repo repo) {
        getView().showProgress();//先进行加载视图
        String login = repo.getOwner().getLogin();//拿到登录名账号
        Log.d("RepoInfoPresenter", "getReadme: "+login);
        String name = repo.getName();//拿到仓库名
        if (repoContentCall != null) repoContentCall.cancel();//进行清除
        repoContentCall = GithubClient.getInstance().getReadme(login, name);
        repoContentCall.enqueue(repoContentCallback);
    }

        Callback<RepoContentResult> repoContentCallback = new Callback<RepoContentResult>() {
            @Override
            public void onResponse(Call<RepoContentResult> call, Response<RepoContentResult> response) {
                String content = response.body().getContent();//直接得到内容
                // BASE64解码
                byte[] data = Base64.decode(content, Base64.DEFAULT);
                String mdContent = new String(data);
                // 根据Markdown格式的内容获取HTML格式的内容
                RequestBody body = RequestBody.create(MediaType.parse("text/plain"), mdContent);
                if (mdCall != null) mdCall.cancel();//进行清除
                if (mdCall != null) mdCall.cancel();
                mdCall = GithubClient.getInstance().markdown(body);
                mdCall.enqueue(mdCallback);
            }

            @Override
            public void onFailure(Call<RepoContentResult> call, Throwable t) {
                getView().hideProgress();//隐藏
                getView().showMessage("Error:" + t.getMessage());//显示错误
            }
        };
    private Callback<ResponseBody> mdCallback = new Callback<ResponseBody>() {
        @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                String htmlContent = response.body().string();//拿到HTML格式的内容
                getView().setData(htmlContent);
                getView().hideProgress();
            } catch (IOException e) {
                onFailure(call, e);
            }
        }

        @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage("Error:" + t.getMessage());
        }
    };



}
