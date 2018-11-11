package com.example.jeffe.trabalho_final;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jeffe.trabalho_final.Build.MyBuilds;


public class PerfilFragment extends Fragment {

    public TextView numeroBuilds;

    public static MainActivity mainActivity;
    public PerfilFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance() {
        PerfilFragment fragment = new PerfilFragment();
        return fragment;
    }

    public static PerfilFragment newInstance(MainActivity mainContext) {
        mainActivity = mainContext;
        PerfilFragment fragment = new PerfilFragment();
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
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        numeroBuilds = getView().findViewById(R.id.numberBuild);

        numeroBuilds.setText(String.valueOf( MyBuilds.getInstance().getBuildsSize()) );

    }
}
