package com.feicuiedu.gitdroid.spalsh.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.feicuiedu.gitdroid.R;

/**
 * Created by Administrator on 2016/6/29.
 */
public class pager0 extends FrameLayout {
    public pager0(Context context) {
        super(context);
        init();
    }


    public pager0(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public pager0(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
      LayoutInflater.from(getContext()).inflate(R.layout.content_pager_0,this,true);



    }
}
