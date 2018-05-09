package com.example.bobby97.newsreader50lollipop.Interface;

import retrofit2.Call;

import com.example.bobby97.newsreader50lollipop.Model.IconFavicon;

import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Bobby97 on 04.05.2018.
 */

public interface IconFaviconService {
    @GET
    Call<IconFavicon> getIconUrl(@Url String url);
}
