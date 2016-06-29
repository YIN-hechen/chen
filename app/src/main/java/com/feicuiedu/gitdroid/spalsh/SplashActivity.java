package com.feicuiedu.gitdroid.spalsh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.feicuiedu.gitdroid.MainActivity;
import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.Utils.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/28.
 */


public class SplashActivity extends AppCompatActivity{
    @Bind(R.id.btnLogin) Button btnlogin;
    @Bind(R.id.btnEnter) Button btnenter;
    private ActivityUtils mUtils;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_splash);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        mUtils=new ActivityUtils(this);
    }

    @OnClick(R.id.btnLogin)
    public void login(){
    }

    @OnClick(R.id.btnEnter)
    public void enter(){
        mUtils.startActivity(MainActivity.class);
    }


}