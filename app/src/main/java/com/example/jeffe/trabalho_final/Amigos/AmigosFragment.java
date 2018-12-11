package com.example.jeffe.trabalho_final.Amigos;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeffe.trabalho_final.AmigoPerfilFragment;
import com.example.jeffe.trabalho_final.Build.BuildCompleta;
import com.example.jeffe.trabalho_final.Build.BuildFragment;
import com.example.jeffe.trabalho_final.Build.EditBuildFragment;
import com.example.jeffe.trabalho_final.Build.Item;
import com.example.jeffe.trabalho_final.MainActivity;
import com.example.jeffe.trabalho_final.PerfilFragment;
import com.example.jeffe.trabalho_final.R;
import com.example.jeffe.trabalho_final.Requests.FirebaseRequests;
import com.example.jeffe.trabalho_final.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AmigosFragment extends Fragment {
    public static  MainActivity mainActivity;
    private RecyclerView recyclerView;
    public static PerfilFragment perfilFragment;

    private List<Usuario> usuarioList;
    public AmigosAdapter amigosAdapter;

    private TextView userName;
    private Usuario usuario;
    public AmigosFragment mContext;

    public static AmigosFragment uniqueInstance = null;


    FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("Users").child(fUser.getUid());
    DatabaseReference databaseFriends = FirebaseDatabase.getInstance().getReference("Users");

    public  AmigosFragment() {
        // Required empty public constructor

    }

    public static AmigosFragment newInstance(MainActivity mActivity) {
        mainActivity = mActivity;
        if(uniqueInstance == null ){
            uniqueInstance = new AmigosFragment();
        }

        return uniqueInstance;
    }
    public static AmigosFragment newInstance() {

        if(uniqueInstance == null ){
            uniqueInstance = new AmigosFragment();
        }

        return uniqueInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_amigos, container, false);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    mContext = this;
        userName = getView().findViewById(R.id.userNameAmigos);

        recyclerView  = (RecyclerView) getView().findViewById(R.id.recycler_view);
        usuarioList = new ArrayList<>();
        amigosAdapter = new AmigosAdapter(this,usuarioList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(amigosAdapter);

        getUsuarios();

        userName.setText("Amigos de "+FirebaseRequests.GetInstance().userName );

    }


    public void getUsuarios(){
        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    usuarioList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.child("userFriendList").getChildren()) {

                        DatabaseReference friend = databaseFriends.child(snapshot.getValue().toString());

                        friend.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                 Usuario u = new Usuario(
                                         dataSnapshot.child("userId").getValue().toString(),
                                         dataSnapshot.child("userName").getValue().toString(),
                                         dataSnapshot.child("userEmail").getValue().toString(),
                                         dataSnapshot.child("userLocalization").getValue().toString()
                                 );

                                usuarioList.add(u);
                                amigosAdapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                    }
                }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    public void goToFriend(Usuario usuario){
        Fragment amigoPerfilFragment = AmigoPerfilFragment.newInstance(perfilFragment, mainActivity);

        ((AmigoPerfilFragment) amigoPerfilFragment).usuario = usuario;

        mainActivity.openFragment(amigoPerfilFragment);
    }

    public void deleteFriend(Usuario usuario){

        FirebaseRequests.GetInstance().DeleteAmigo(usuario,mContext);
    }
}
