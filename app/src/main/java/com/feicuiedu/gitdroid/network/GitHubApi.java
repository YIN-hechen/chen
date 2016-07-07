package com.feicuiedu.gitdroid.network;

import com.feicuiedu.gitdroid.github.login.model.AccessTokenResult;
import com.feicuiedu.gitdroid.github.login.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/7/6.
 */
public interface GitHubApi {

    // GitHub开发者，申请时填写的(重定向返回时的一个标记)
    String CALL_BACK = "feicui";
    // GitHub开发者，申请就行
    String CLIENT_ID = "aa7a3fb1b42f8c07a232";
    String CLIENT_SECRET = "841a9cfd15ded1abb9d75ba51d2d8dd0189ebb77";



//    我们应用中使用的scope权限包括：
//    user : 读写用户信息；
//    public_repo : 该用户公有库的访问权限，关注(starring)其他公有库的权限;
//    repo : 该用户公有和私有库的访问权限。
// 授权时申请的可访问域
String INITIAL_SCOPE = "user,public_repo,repo";

//WebView 用来加载URL,显示GitHub的登录界面
String AUTH_URL="https://github.com/login/oauth/authorize?client_id="+
        CLIENT_ID+"&"+"scope="+INITIAL_SCOPE;


//获取访问令牌
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    Call<AccessTokenResult> getOAuthToken(
            @Field("client_id") String client,
            @Field("client_secret") String clientSecret,
            @Field("code") String code);


    //这是获得用户信息的
    @GET("user")
    Call<User> getUserInfo();

    /**
     * @param query  查询参数
     * @param pageId 查询页数，从1开始
     * @return 查询结果
     */
    @GET("/search/repositories")
    void searchRepo(
            @Query("q") String query,
            @Query("page") int pageId);



}
