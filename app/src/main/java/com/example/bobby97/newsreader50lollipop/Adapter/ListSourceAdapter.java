package com.example.bobby97.newsreader50lollipop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bobby97.newsreader50lollipop.Common.Common;
import com.example.bobby97.newsreader50lollipop.Interface.IconFaviconService;
import com.example.bobby97.newsreader50lollipop.Interface.ItemClickListener;
import com.example.bobby97.newsreader50lollipop.ListNews;
import com.example.bobby97.newsreader50lollipop.Model.IconFavicon;
import com.example.bobby97.newsreader50lollipop.Model.WebSite;
import com.example.bobby97.newsreader50lollipop.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bobby97 on 03.05.2018.
 */

class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ItemClickListener itemClickListener;

    TextView sourceTitle;
    CircleImageView sourceImage;

    public ListSourceViewHolder(View itemView) {
        super(itemView);

        sourceImage = (CircleImageView) itemView.findViewById(R.id.source_image);
        sourceTitle = (TextView) itemView.findViewById(R.id.source_name);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}

public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder>{

    private Context context;
    private WebSite webSite;

    private IconFaviconService mService;

    public ListSourceAdapter(Context context, WebSite webSite) {
        this.context = context;
        this.webSite = webSite;

        mService = Common.getIconService();
    }

    @Override
    public ListSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.source_layout, parent, false);
        return new ListSourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListSourceViewHolder holder, int position) {
//        StringBuilder iconFavAPI = new StringBuilder("https://besticon-demo.herokuapp.com/allicons.json?url=");
//        iconFavAPI.append(webSite.getSources().get(position).getUrl());
//
//        mService.getIconUrl(iconFavAPI.toString())
//                .enqueue(new Callback<IconFavicon>() {
//                    @Override
//                    public void onResponse(Call<IconFavicon> call, Response<IconFavicon> response) {
//                        if (response.body().getIcons().size() > 0)
//                         {
//                            Picasso.with(context)
//                                    .load(response.body().getIcons().get(0).getUrl())
//                                    .into(holder.sourceImage);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<IconFavicon> call, Throwable t) {
//
//                    }
//                });

        holder.sourceTitle.setText(webSite.getSources().get(position).getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, ListNews.class);
                intent.putExtra("source", webSite.getSources().get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return webSite.getSources().size();
    }
}