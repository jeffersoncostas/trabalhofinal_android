package com.example.jeffe.trabalho_final.Noticias;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jeffe.trabalho_final.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.MyViewHolder> {
    private Context mContext;
    private List<Noticia> noticiaList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public ConstraintLayout noticiaCard;

        public MyViewHolder(View view) {
            super(view);
            noticiaCard = view.findViewById(R.id.noticiaCard);
            title =  view.findViewById(R.id.title);
            count = view.findViewById(R.id.count);
            thumbnail = view.findViewById(R.id.thumbnail);
        }
    }


    public NoticiasAdapter(Context mContext, List<Noticia> noticiaList) {
        this.mContext = mContext;
        this.noticiaList = noticiaList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.noticia_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Noticia noticia = noticiaList.get(position);
        holder.title.setText(noticia.getTitle());
        holder.count.setText(noticia.getAuthor());

        // loading album cover using Glide library

        Picasso.get().load(noticia.getFeatured_image()).into(holder.thumbnail);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebView(view, noticia.getSlug());
            }
        };

        holder.noticiaCard.setOnClickListener(listener);
        holder.thumbnail.setOnClickListener(listener);




    }

    private void showWebView(View view, String slug) {
        // open webview page when clicks on the card
        Intent intent = new Intent(mContext, NoticiaWebActivity.class);
        intent.putExtra("noticiaSlug", slug);
        mContext.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return noticiaList.size();
    }


}
