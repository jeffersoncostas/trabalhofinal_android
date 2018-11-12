package com.example.jeffe.trabalho_final.Build;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeffe.trabalho_final.R;

import java.util.List;

public class BuildListsAdapter extends RecyclerView.Adapter<BuildListsAdapter.MyViewHolder> {

    private List<BuildCompleta> buildList;
    private BuildListsFragment mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView date;
        public ImageView delIcon;
        public ConstraintLayout itemCard;

        public MyViewHolder(View view) {
            super(view);
            itemCard = view.findViewById(R.id.buildItemCard);
            title = view.findViewById(R.id.build_name_card);
            date = view.findViewById(R.id.build_date);
            delIcon = view.findViewById(R.id.deleteIcon);

        }
    }

    public void passContext(BuildListsFragment context){
        mContext = context;
    }

    public BuildListsAdapter(BuildListsFragment context, List<BuildCompleta> buildC){
        mContext = context;
        buildList = buildC;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.build_item_card, parent, false);

        return new MyViewHolder(itemView);
    }

    public void update() {
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final BuildCompleta item = buildList.get(position);
        holder.title.setText(item.getBuildName());
        holder.date.setText(item.returnDate());

        View.OnClickListener listenerCard = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            mContext.goToSpecificBuild(item);
                // ao clicar no card
            }
        };

        View.OnClickListener deleteItem = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MyBuilds.getInstance().deleteBuild(item);
               update();
            }
        };

     //   holder.itemCard.setOnClickListener(listenerCard);
        holder.title.setOnClickListener(listenerCard);
        holder.delIcon.setOnClickListener(deleteItem);


    }

    @Override
    public int getItemCount() {
        return buildList.size();
    }
}
