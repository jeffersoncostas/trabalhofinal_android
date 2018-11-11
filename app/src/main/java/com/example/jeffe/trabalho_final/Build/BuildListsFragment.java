package com.example.jeffe.trabalho_final.Build;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeffe.trabalho_final.MainActivity;
import com.example.jeffe.trabalho_final.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuildListsFragment extends Fragment {

    public List<BuildCompleta> listaDeBuilds;
    private RecyclerView recyclerView;
    private BuildListsAdapter buildListsAdapter;

    private BuildCompleta novaBuildSent;

    public static BuildListsFragment uniqueInstance = null;

    public static MainActivity mainActivity;


    public BuildListsFragment() {
        // Required empty public constructor
        Log.d("constructor","a");

    }
    public static  BuildListsFragment newInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new BuildListsFragment();
        }
        Log.d("new instance","a");

        return uniqueInstance;
    }

    public static  BuildListsFragment newInstance(MainActivity mActivity){
        mainActivity = mActivity;
        if(uniqueInstance == null){
            uniqueInstance = new BuildListsFragment();
        }
        Log.d("new instance","a");

        return uniqueInstance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_build_lists, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaDeBuilds = MyBuilds.getInstance().getBuilds();
        recyclerView = getView().findViewById(R.id.recycler_view);
        buildListsAdapter = new BuildListsAdapter(this,listaDeBuilds);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(buildListsAdapter);

        Log.d("created view","a");

    }


    public void goToSpecificBuild(BuildCompleta builCompleta){

        Fragment buildFragment = BuildFragment.newInstance();
        ((BuildFragment) buildFragment).initializeWithBuild(builCompleta);

        mainActivity.openFragment(buildFragment);
    }



}
