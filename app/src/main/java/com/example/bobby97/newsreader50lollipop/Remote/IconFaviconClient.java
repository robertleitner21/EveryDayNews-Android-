package com.example.bobby97.newsreader50lollipop.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bobby97 on 04.05.2018.
 */

public class IconFaviconClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl("https://besticon-demo.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
