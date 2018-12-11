package com.example.jeffe.trabalho_final.Build;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.example.jeffe.trabalho_final.R;
import com.example.jeffe.trabalho_final.Requests.HttpRequests;

import java.util.ArrayList;
import java.util.List;


public class EditBuildFragment extends Fragment {

    private static EditBuildFragment uniqueInstance = null;
    public BuildCompleta buildCompleta = null;
    public boolean onEditBuild = false;
    //

    public TextView titleCreateBuild;
    private RecyclerView buildRecycler;
    public List<Item> buildList;
    private ItensAdapter buildItensAdapter;

    private RecyclerView recyclerView;

    public List<Item> itemList;
    public ItensAdapter itensAdapter;

    public EditBuildFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EditBuildFragment newInstance(BuildFragment _buildFragment) {
        if(uniqueInstance == null){
            uniqueInstance = new EditBuildFragment();

        }
        return uniqueInstance;
    }

    public static EditBuildFragment newInstance() {

        if(uniqueInstance == null ){
            uniqueInstance = new EditBuildFragment();
        }
        return uniqueInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_build, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onEditBuild = true;
        Log.d("criou","edit build");
        titleCreateBuild = getView().findViewById(R.id.titleCreateBuild);
        titleCreateBuild.setText(buildCompleta.getBuildName());
        recyclerView  = (RecyclerView) getView().findViewById(R.id.recycler_view);

        itemList = new ArrayList<>();
        itensAdapter = new ItensAdapter(this, itemList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new EditBuildFragment.GridSpacingItemDecoration(3, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itensAdapter);

        getItems();

        initializeBuildList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onEditBuild = false;
    }

    public void initializeBuildList(){

        buildList = new ArrayList<>();

        if(buildCompleta != null){
            buildList = buildCompleta.getListaItemsBuild();
        }

        buildRecycler  = getView().findViewById(R.id.suaBuildRecycler);
        buildItensAdapter= new ItensAdapter(this, buildList);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);

        buildRecycler.setLayoutManager(mLayoutManager);
        buildRecycler.addItemDecoration(new EditBuildFragment.GridSpacingItemDecoration(3, dpToPx(1), true));
        buildRecycler.setItemAnimator(new DefaultItemAnimator());
        buildRecycler.setAdapter(buildItensAdapter);

        buildItensAdapter.notifyDataSetChanged();
    }


    public void getItems(){
        HttpRequests.GetInstance().getItems(this);
//        Item n = new Item("1","Qin","https://web2.hirez.com/smite/item-icons/qins-sais.jpg","Bonus damage based on target's maximum Health","1,450",false);
//        itemList.add(n);
//
//        n = new Item("2","Light Blade","https://web2.hirez.com/smite/item-icons/light-blade.jpg","Physical Power and Attack Speed.","1,450",false);
//        itemList.add(n);
//
//        n = new Item("3","The Executioner","https://web2.hirez.com/smite/item-icons/the-executioner.jpg","Basic Attacks reduce target's Physical Protection","1,450",false);
//        itemList.add(n);

        itensAdapter.notifyDataSetChanged();
    }

    public void sendItemToBuild(Item item){

        if(buildList.contains(item)){
            return;
        }

        buildList.add(item);
        buildItensAdapter.notifyDataSetChanged();
    }

    public void removeItemFromBuild(Item item){
        buildList.remove(item);
        buildItensAdapter.notifyDataSetChanged();
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
