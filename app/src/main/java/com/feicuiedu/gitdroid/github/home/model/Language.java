package com.feicuiedu.gitdroid.github.home.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/7.
 *
 *
 * 这是上面的标题实体类
 */
public class Language implements Serializable{
    private String path; //这个用于网上连接数据的类型
    private String name; //这个用于标题

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }
    private static List<Language> DEFAULT_LANGS;

    public static List<Language> getDefaultLanguage(Context context) {
        if (DEFAULT_LANGS != null)return DEFAULT_LANGS;
        try {
            //把文件转化为gson字符串
            InputStream inputStream = context.getAssets().open("langs.json");
            String content = IOUtils.toString(inputStream);
            //解析gson
            Gson gson = new Gson();
            DEFAULT_LANGS = gson.fromJson(content, new TypeToken<List<Language>>(){}.getType());
            return DEFAULT_LANGS;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
