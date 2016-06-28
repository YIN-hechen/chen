package com.feicuiedu.gitdroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/28.
 */


public class SplashActivity extends AppCompatActivity{
    @Bind(R.id.btnLogin) Button btnlogin;
    @Bind(R.id.btnEnter) Button btnenter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btnLogin)
    public void login(){
    }

    @OnClick(R.id.btnEnter)
    public void enter(){
    }


}