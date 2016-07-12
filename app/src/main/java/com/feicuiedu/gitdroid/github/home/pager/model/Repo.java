package com.feicuiedu.gitdroid.github.home.pager.model;

import com.feicuiedu.gitdroid.github.login.model.User;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/7.
 */
public class Repo implements Serializable {
    //        "id": 29028775,
//            "name": "react-native",
//            "full_name": "facebook/react-native",
//            "owner": {},
//            "private": false,
//            "html_url": "https://github.com/facebook/react-native",
//            "description": "A framework for building native apps with React.",

    // 仓库名称
    @SerializedName("name")
    private String name;

    // 仓库全名
    @SerializedName("full_name")
    private String fullName;

    // 仓库描述
    @SerializedName("description")
    private String description;

    // 本仓库的star数量 (在GitHub上被关注的数量)
    @SerializedName("stargazers_count")
    private int stargazersCount;

    // 本仓库的fork数量 (在GitHub上被拷贝的数量)
    @SerializedName("forks_count")
    private int forksCount;

    // 本仓库的拥有者
    @SerializedName("owner")
    private User owner;

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public int getForksCount() {
        return forksCount;
    }

    public User getOwner() {
        return owner;
    }


}
