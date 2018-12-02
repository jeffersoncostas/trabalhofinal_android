package com.example.jeffe.trabalho_final.Noticias;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeffe.trabalho_final.R;
import com.example.jeffe.trabalho_final.Requests.HttpRequests;

import java.util.ArrayList;
import java.util.List;


public class NoticiasFragment extends Fragment {


    private RecyclerView recyclerView;

    public List<Noticia> noticiaList;
    public NoticiasAdapter noticiaAdapter;


    public NoticiasFragment() {
        // Required empty public constructor
    }


    public static NoticiasFragment newInstance() {
        NoticiasFragment fragment = new NoticiasFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_noticias, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView  = (RecyclerView) getView().findViewById(R.id.recycler_view);
        noticiaList = new ArrayList<>();
        noticiaAdapter = new NoticiasAdapter(getContext(), noticiaList);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new NoticiasFragment.GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(noticiaAdapter);

        getNoticias();
    }


    public void getNoticias(){

        HttpRequests.GetInstance().getNews(this);

//        Noticia n = new Noticia('1',"https://web2.hirez.com/smite-media//wp-content/uploads/2018/11/PatchNotes-PatchBadge-UpdateNotes-FeatImg-283x213-3.jpg","Celestial Domination | 5.21 Update Notes","celestial-domination-5-21-update-notes","TitanIsiah");
//        noticiaList.add(n);
//
//        n = new Noticia('1',"https://web2.hirez.com/smite-media//wp-content/uploads/2018/11/PatchNotes-PatchBadge-UpdateNotes-FeatImg-283x213-3.jpg","Teste","celestial-domination-5-21-update-notes","TitanIsiah");
//        noticiaList.add(n);
//
//        n = new Noticia('1',"https://web2.hirez.com/smite-media//wp-content/uploads/2018/11/PatchNotes-PatchBadge-UpdateNotes-FeatImg-283x213-3.jpg","Teste","celestial-domination-5-21-update-notes","TitanIsiah");
//        noticiaList.add(n);
//
//        n = new Noticia('1',"https://web2.hirez.com/smite-media//wp-content/uploads/2018/11/PatchNotes-PatchBadge-UpdateNotes-FeatImg-283x213-3.jpg","Teste","celestial-domination-5-21-update-notes","TitanIsiah");
//        noticiaList.add(n);

        noticiaAdapter.notifyDataSetChanged();

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

}
