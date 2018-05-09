package com.example.bobby97.newsreader50lollipop.Common;

import com.example.bobby97.newsreader50lollipop.Interface.IconFaviconService;
import com.example.bobby97.newsreader50lollipop.Interface.NewsService;
import com.example.bobby97.newsreader50lollipop.Remote.IconFaviconClient;
import com.example.bobby97.newsreader50lollipop.Remote.RetrofitClient;

/**
 * Created by Bobby97 on 03.05.2018.
 */

public class Common {
    private static final String BASE_URL = "https://newsapi.org/";

    public static final String API_KEY = "dd0f48e1d51d482a95e5b240df32a9a8";

    public static NewsService getNewsService()
    {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    public static IconFaviconService getIconService()
    {
        return IconFaviconClient.getClient().create(IconFaviconService.class);
    }

    public static String getAPIUrl(String source, String apiKey)
    {
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v2/top-headlines?sources=");
        return apiUrl.append(source)
                .append("&apiKey=")
                .append(apiKey).toString();
    }
}
