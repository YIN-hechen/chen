package com.feicuiedu.gitdroid.github.home.pager.view;

import com.feicuiedu.gitdroid.github.home.pager.model.Repo;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2016/7/3.
 */
public interface PtrPageView extends MvpView, LoadMoreView<List<Repo>>,PtrView<List<Repo>> {
}
