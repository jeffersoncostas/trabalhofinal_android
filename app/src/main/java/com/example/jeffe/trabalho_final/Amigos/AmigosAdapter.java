package com.example.jeffe.trabalho_final.Amigos;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.jeffe.trabalho_final.Build.BuildFragment;
import com.example.jeffe.trabalho_final.Build.BuildListsFragment;
import com.example.jeffe.trabalho_final.Build.Item;
import com.example.jeffe.trabalho_final.Build.MyBuilds;
import com.example.jeffe.trabalho_final.R;
import com.example.jeffe.trabalho_final.Usuario;

import java.util.List;

public class AmigosAdapter extends RecyclerView.Adapter<AmigosAdapter.MyViewHolder> {
    private List<Usuario> usuarioList;
    private AmigosFragment mContext;
    private Context mContextGeneric;

    public AmigosAdapter(AmigosFragment context, List<Usuario> list){
        usuarioList = list;
        mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,description;
        public ImageView thumbnail, overflow;
        public ConstraintLayout itemCard;
        public ImageView delIcon;


        public MyViewHolder(View view) {
            super(view);
            itemCard = (ConstraintLayout) view.findViewById(R.id.friend_card_view);
            name = (TextView) view.findViewById(R.id.friends_name);
            description = (TextView) view.findViewById(R.id.friends_description);
            thumbnail = (ImageView) view.findViewById(R.id.friend_image);
            delIcon = (ImageView) view.findViewById(R.id.deleteIcon);
        }
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.usuario_item_list, parent, false);

        return new MyViewHolder(itemView);
    }



    public void update() {
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Usuario usuario = usuarioList.get(position);
        holder.description.setText(usuario.getUserDescription());
        holder.name.setText(usuario.getUserName());

        View.OnClickListener listenerCard = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.goToFriend(usuario);
            }
        };

        View.OnClickListener deleteItem = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //      MyBuilds.getInstance().deleteBuild(item);
          //      update();

                Log.d("deletou", "deletada");
                update();
            }
        };

      //  holder.itemCard.setOnClickListener(listenerCard);
        holder.name.setOnClickListener(listenerCard);
        holder.thumbnail.setOnClickListener(listenerCard);
        holder.delIcon.setOnClickListener(deleteItem);
    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }


}
