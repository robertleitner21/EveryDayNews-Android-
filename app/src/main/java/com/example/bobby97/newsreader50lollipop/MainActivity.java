package com.example.bobby97.newsreader50lollipop;

//import android.support.v7.app.AlertDialog;
import android.app.AlertDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;

import com.example.bobby97.newsreader50lollipop.Adapter.ListSourceAdapter;
import com.example.bobby97.newsreader50lollipop.Common.Common;
import com.example.bobby97.newsreader50lollipop.Interface.NewsService;
import com.example.bobby97.newsreader50lollipop.Model.WebSite;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView listWebsite;
    RecyclerView.LayoutManager layoutManager;
    NewsService mService;
    ListSourceAdapter adapter;
    AlertDialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init cache
        Paper.init(this);

        //init service
        mService = Common.getNewsService();

        //init view

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshSwipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebServiceSource(true);
            }
        });

        listWebsite = (RecyclerView)findViewById(R.id.list_source);
        listWebsite.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listWebsite.setLayoutManager(layoutManager);

        dialog = new SpotsDialog(this);

        loadWebServiceSource(false);
    }

    private void loadWebServiceSource(boolean isRefreshed) {
        if (!isRefreshed)
        {
            String cache = Paper.book().read("cache");
            if (cache != null && !cache.isEmpty() && !cache.equals("null")) // If cache exists
            {
                WebSite webSite = new Gson().fromJson(cache, WebSite.class); // Converts cache from Json to Object
                adapter = new ListSourceAdapter(getBaseContext(), webSite);
                adapter.notifyDataSetChanged();
                listWebsite.setAdapter(adapter);
            }
            else //if no have cahce
            {
                dialog.show();
                mService.getSources().enqueue(new Callback<WebSite>() {
                    @Override
                    public void onResponse(retrofit2.Call<WebSite> call, Response<WebSite> response) {
                        adapter = new ListSourceAdapter(getBaseContext(), response.body());
                        adapter.notifyDataSetChanged();
                        listWebsite.setAdapter(adapter);

                        //Save to cache
                        Paper.book().write("cache", new Gson().toJson(response.body()));
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(retrofit2.Call<WebSite> call, Throwable t) {

                    }
                });
            }
        }
        else // If from swipe to refresh
        {
            swipeRefreshLayout.setRefreshing(true);

            mService.getSources().enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(retrofit2.Call<WebSite> call, Response<WebSite> response) {
                    adapter = new ListSourceAdapter(getBaseContext(), response.body());
                    adapter.notifyDataSetChanged();
                    listWebsite.setAdapter(adapter);

                    //Save to cache
                    Paper.book().write("cache", new Gson().toJson(response.body()));

                    //Dismiss refresh progressing
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(retrofit2.Call<WebSite> call, Throwable t) {

                }
            });
        }
    }


}
