package com.example.bobby97.newsreader50lollipop;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bobby97.newsreader50lollipop.Adapter.ListNewsAdapter;
import com.example.bobby97.newsreader50lollipop.Common.Common;
import com.example.bobby97.newsreader50lollipop.Interface.NewsService;
import com.example.bobby97.newsreader50lollipop.Model.Article;
import com.example.bobby97.newsreader50lollipop.Model.News;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.ConcurrentModificationException;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.widget.SwipeRefreshLayout.*;

public class ListNews extends AppCompatActivity {

    KenBurnsView kbv;
    DiagonalLayout diagonalLayout;
    AlertDialog dialog;
    NewsService mService;
    TextView topAuthor, topTitle;
    SwipeRefreshLayout swipeRefreshLayout;

    String source = "", sortBy="", webHotUrl = "";

    ListNewsAdapter adapter;
    RecyclerView lstNews;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        //service
        mService = Common.getNewsService();

        dialog = new SpotsDialog(this);

        //View
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(source, true);
            }
        });

        diagonalLayout = (DiagonalLayout) findViewById(R.id.diagonalLayout);
        diagonalLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(getBaseContext(), DetailArticle.class);
                detail.putExtra("webURL", webHotUrl);
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //additional
                startActivity(detail);

            }
        });
        kbv = (KenBurnsView) findViewById(R.id.top_image);
        topAuthor = (TextView) findViewById(R.id.top_author);
        topTitle = (TextView) findViewById(R.id.top_title);

        lstNews = (RecyclerView) findViewById(R.id.list_news);
        lstNews.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        lstNews.setLayoutManager(layoutManager);

        if (getIntent() != null)
        {
            source = getIntent().getStringExtra("source");
            if (!source.isEmpty())
            {
                loadNews(source, false);
            }
        }
    }

    private void loadNews(String source, boolean isRefreshed) {
        if (!isRefreshed){
            dialog.show();
            mService.getNewestArticles(Common.getAPIUrl(source, Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            dialog.dismiss();
                            //get first article
                            Picasso.with(getBaseContext())
                                    .load(response.body().getArticles().get(0).getUrlToImage()).into(kbv);

                            topTitle.setText(response.body().getArticles().get(0).getTitle());
                            topAuthor.setText(response.body().getArticles().get(0).getAuthor());

                            webHotUrl = response.body().getArticles().get(0).getUrl();

                            //load remain articles
                            List<Article> removeFirstItem = response.body().getArticles();
                            //Because we already load first item, we should delete it
                            removeFirstItem.remove(0);
                            adapter = new ListNewsAdapter(removeFirstItem, getBaseContext());
                            adapter.notifyDataSetChanged();
                            lstNews.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {

                        }
                    });
        }
        else
        {
            dialog.show();
            mService.getNewestArticles(Common.getAPIUrl(source, Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            dialog.dismiss();
                            //get first article
                            Picasso.with(getBaseContext())
                                    .load(response.body().getArticles().get(0).getUrlToImage()).into(kbv);

                            topTitle.setText(response.body().getArticles().get(0).getTitle());
                            topAuthor.setText(response.body().getArticles().get(0).getAuthor());

                            webHotUrl = response.body().getArticles().get(0).getUrl();

                            //load remain articles
                            List<Article> removeFirstItem = response.body().getArticles();
                            //Because we already load first item, we should delete it
                            removeFirstItem.remove(0);
                            adapter = new ListNewsAdapter(removeFirstItem, getBaseContext());
                            adapter.notifyDataSetChanged();
                            lstNews.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {

                        }
                    });
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
