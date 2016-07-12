package com.feicuiedu.gitdroid.github.home.pager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/7/7.
 * <p>
 * 热门仓库的实体类
 */


public class RepoResult {
    //    "total_count": 2103761,
//            "incomplete_results": false,
//            "items":[]

    // 总量
    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;
    // 仓库列表
    @SerializedName("items")
    private List<Repo> repoList;

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public List<Repo> getRepoList() {
        return repoList;
    }
}
