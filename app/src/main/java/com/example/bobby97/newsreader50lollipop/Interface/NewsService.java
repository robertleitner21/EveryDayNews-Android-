package com.example.bobby97.newsreader50lollipop.Interface;

import com.example.bobby97.newsreader50lollipop.Common.Common;
import com.example.bobby97.newsreader50lollipop.Model.News;
import com.example.bobby97.newsreader50lollipop.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Bobby97 on 03.05.2018.
 */

public interface NewsService {
    @GET("v2/sources?language=en&apiKey="+Common.API_KEY)
    Call<WebSite> getSources();

    @GET
    Call<News> getNewestArticles(@Url String url);
}
