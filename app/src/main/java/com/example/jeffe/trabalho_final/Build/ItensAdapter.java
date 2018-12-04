package com.example.jeffe.trabalho_final.Build;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

public class ItensAdapter extends RecyclerView.Adapter<ItensAdapter.MyViewHolder> {

    private BuildFragment mContext;
    private List<Item> itemList;

    private Context mContextGeneric;

    public ItensAdapter(Context mContext, List<Item> itemList) {
        this.mContextGeneric = mContext;
        this.itemList = itemList;
    }

    public ItensAdapter(BuildFragment mContext, List<Item> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView price;
        public ImageView thumbnail, overflow;
        public FrameLayout itemCard;

        public MyViewHolder(View view) {
            super(view);
            itemCard = (FrameLayout) view.findViewById(R.id.item_card_view);
            title = (TextView) view.findViewById(R.id.title);
            price = (TextView) view.findViewById(R.id.price);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Item item = itemList.get(position);
        holder.title.setText(item.getDeviceName());
        holder.price.setText(item.getPrice().toString());
        Picasso.get().load(item.getItemIcon_url()).into(holder.thumbnail);

        View.OnClickListener listenerCard = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.IsUsing){
                    removeItemFromBuild(item);
                    return;
                }
                sendItemToBuild(view, item);
            }
        };
        holder.itemCard.setOnClickListener(listenerCard);
        holder.thumbnail.setOnClickListener(listenerCard);

    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void removeItemFromBuild(Item item){
        Item nItem = item;
        item.setUsing(false);
        nItem.setUsing(false);
        mContext.removeItemFromBuild(item);

    }
    public void sendItemToBuild(View view, Item item){
        Item nItem = item;
        item.setUsing(true);
        nItem.setUsing(true);
        mContext.sendItemToBuild(item);
    }
}
