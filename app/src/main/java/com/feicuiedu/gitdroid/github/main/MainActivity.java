package com.feicuiedu.gitdroid.github.main;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.github.home.HotRepoFragment;
import com.feicuiedu.gitdroid.github.login.LoginActivity;
import com.feicuiedu.gitdroid.github.login.model.CurrentUser;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
   @Bind(R.id.drawerLayout)DrawerLayout drawerLayout; //抽屉(包含内容和侧滑菜单)
   @Bind(R.id.navigationView)NavigationView navigationView;//侧滑菜单视图(分头部和菜单)
    @Bind(R.id.toolbar)Toolbar toolbar;
    private MenuItem mMenuItem;
    private HotRepoFragment mHotRepoFragment;//要装在FrameLayout的Fragment
    private Button btnlogin;  //登录按钮
    private ActivityUtils activityUtils;  //辅助类

    private ImageView ivIcon;//头像
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //没有登陆过
   if (CurrentUser.isEmpty()){
    btnlogin.setText(R.string.login_github);
    return;
   }
     //已授权登录
        btnlogin.setText(R.string.switch_account);//改成切换账号
     getSupportActionBar().setTitle(CurrentUser.getUser().getName());//设置用户名
        // 设置用户头像
        String photoUrl = CurrentUser.getUser().getAvatar();
        ImageLoader.getInstance().displayImage(photoUrl,ivIcon);


    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);//关联id

        activityUtils = new ActivityUtils(this);

        navigationView.setNavigationItemSelectedListener(this); //设置navigationView侧滑菜单的监听

        mMenuItem=navigationView.getMenu().findItem(R.id.github_hot_repo);//找到侧滑菜单中的菜单中的"最热门"按键
        mMenuItem.setChecked(true);//使"最热门"按键默认成为点击状态


        //添加Toolbar左边的小图标
        //添加Toolbar左边的小图标并进行动画效果
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Frangment的动态替换
        mHotRepoFragment=new HotRepoFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container,mHotRepoFragment);
        fragmentTransaction.commit();
         //头像
        ivIcon=(ImageView)ButterKnife.findById(navigationView.getHeaderView(0),R.id.ivIcon);
        //登录
    btnlogin=(Button)ButterKnife.findById(navigationView.getHeaderView(0),R.id.btnLogin);
    btnlogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
       activityUtils.startActivity(LoginActivity.class);
        }
    });
    }

    @Override//侧滑菜单监听的回调
    public boolean onNavigationItemSelected(MenuItem item) {

        if (mMenuItem.isChecked()){
            mMenuItem.setChecked(false);  //当点击别的按键时,使"最热门"按键成为未点击状态
        }
    switch (item.getItemId()){
        case R.id.tips_share:
            drawerLayout.closeDrawer(GravityCompat.START);//按下分享,关闭抽屉
            break;
    }

        return true;//返回true,代表该菜单单项变为checked状态
    }

    @Override
    public void onBackPressed() {
        //当点击后退键时
        //如果navigationView开着的-----关闭
       //如果navigationView关着的-----退出Activity
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}