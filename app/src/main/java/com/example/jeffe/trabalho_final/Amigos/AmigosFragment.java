package com.example.jeffe.trabalho_final.Amigos;


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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeffe.trabalho_final.Build.BuildCompleta;
import com.example.jeffe.trabalho_final.Build.BuildFragment;
import com.example.jeffe.trabalho_final.Build.Item;
import com.example.jeffe.trabalho_final.PerfilFragment;
import com.example.jeffe.trabalho_final.R;
import com.example.jeffe.trabalho_final.Usuario;

import java.util.ArrayList;
import java.util.List;

import static com.example.jeffe.trabalho_final.PerfilFragment.mainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmigosFragment extends Fragment {

    private RecyclerView recyclerView;

    private List<Usuario> usuarioList;
    private AmigosAdapter amigosAdapter;
    private Usuario usuario;

    public  AmigosFragment() {
        // Required empty public constructor
    }

    public static AmigosFragment newInstance() {
        AmigosFragment fragment = new AmigosFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_amigos, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView  = (RecyclerView) getView().findViewById(R.id.recycler_view);
        usuarioList = new ArrayList<>();
        amigosAdapter = new AmigosAdapter(this,usuarioList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(amigosAdapter);

        getUsuarios();
    }


    public void getUsuarios(){
        Usuario u = new Usuario("123","Jamba","Sou uma boa pessoa");
        usuarioList.add(u);

        u = new Usuario("2","Cranga","Eu gostaria de fazer umas coisas");
        usuarioList.add(u);


        amigosAdapter.notifyDataSetChanged();

    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
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
//        Fragment perfilFragment = PerfilFragment.newInstance();
//        ((PerfilFragment) perfilFragment).initializeWithFriend(usuario);
//        mainActivity.openFragment(perfilFragment);
    }
}
