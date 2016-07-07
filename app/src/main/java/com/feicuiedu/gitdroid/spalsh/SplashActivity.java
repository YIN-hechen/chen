package com.feicuiedu.gitdroid.spalsh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.github.main.MainActivity;
import com.feicuiedu.gitdroid.github.login.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/28.
 */


public class SplashActivity extends AppCompatActivity{
    @Bind(R.id.btnLogin) Button btnlogin;
    @Bind(R.id.btnEnter) Button btnenter;

    private ActivityUtils activityUtils;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_splash);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
    }

    @OnClick(R.id.btnLogin)
    public void login(){
        activityUtils.startActivity(LoginActivity.class);
    }

    @OnClick(R.id.btnEnter)
    public void enter(){
    activityUtils.startActivity(MainActivity.class);
    }


}