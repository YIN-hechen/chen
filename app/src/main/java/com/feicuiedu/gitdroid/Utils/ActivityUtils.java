package com.feicuiedu.gitdroid.Utils;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Administrator on 2016/6/29.
 */
public class ActivityUtils {
   private Activity mActivity;
    public ActivityUtils(Activity m) {
        mActivity=m;
    }

    public void startActivity(Class clazz) {
        if (mActivity == null) {
            return;
        }
        Intent intent=new Intent(mActivity,clazz);
        mActivity.startActivity(intent);

    }
}
