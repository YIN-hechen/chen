package com.feicuiedu.gitdroid.network;

import com.feicuiedu.gitdroid.context.RepoContentResult;
import com.feicuiedu.gitdroid.github.home.pager.model.RepoResult;
import com.feicuiedu.gitdroid.github.login.model.AccessTokenResult;
import com.feicuiedu.gitdroid.github.login.model.User;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    //这是拿到各种类型的仓库资源
    @Override
    public Call<RepoResult> searchRepo(@Query("q") String query, @Query("page") int pageId) {
        return mGitHubApi.searchRepo(query,pageId);
    }
   //得到指定仓库的信息
   @Override public Call<RepoContentResult> getReadme(@Path("owner") String owner, @Path("repo") String repo) {
       return mGitHubApi.getReadme(owner,repo);
   }

//得到指定仓库的HTML格式的内容
    @Override public Call<ResponseBody> markdown(@Body RequestBody body) {
        return mGitHubApi.markdown(body);
    }
}
