package com.example.bobby97.newsreader50lollipop.Model;

import android.telecom.Call;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Bobby97 on 04.05.2018.
 */

public class IconFavicon {
    private String url;
    private List<Icon> icons;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Icon> getIcons() {
        return icons;
    }

    public void setIcons(List<Icon> icons) {
        this.icons = icons;
    }
}
