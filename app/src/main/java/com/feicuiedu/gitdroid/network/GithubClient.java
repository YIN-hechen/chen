package com.feicuiedu.gitdroid.network;

import com.feicuiedu.gitdroid.github.login.model.AccessTokenResult;
import com.feicuiedu.gitdroid.github.login.model.User;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

/**
 * Created by Administrator on 2016/7/6.
 */
public class GithubClient implements GitHubApi{

    private static GithubClient sClient;
      private GitHubApi mGitHubApi;
    //得到本类对象
    public static GithubClient getInstance() {
        if (sClient == null) {
            sClient = new GithubClient();
        }
        return sClient;
    }
   //创建对象时,初始化GitHubApi对象
    public GithubClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor())//添加拦截器.添加了令牌
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .client(okHttpClient)
                .build();

        mGitHubApi=retrofit.create(GitHubApi.class);
    }
  //这是拿令牌的具体实现联网
    @Override
    public Call<AccessTokenResult> getOAuthToken (@Field("client_id") String client, @Field("client_secret") String clientSecret, @Field("code") String code) {
        return mGitHubApi.getOAuthToken(client,clientSecret,code);
    }

    //这是获得用户信息的
    @Override
    public Call<User> getUserInfo() {
        return mGitHubApi.getUserInfo();
    }


}
