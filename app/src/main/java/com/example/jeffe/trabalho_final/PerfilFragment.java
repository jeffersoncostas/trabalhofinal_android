package com.example.jeffe.trabalho_final;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jeffe.trabalho_final.Build.BuildCompleta;
import com.example.jeffe.trabalho_final.Build.BuildFragment;
import com.example.jeffe.trabalho_final.Build.MyBuilds;


public class PerfilFragment extends Fragment {

    public TextView numeroBuilds;
    public TextView userName;
    public TextView userAdress;
    public TextView userDesc;

    public boolean isInitializedFriend = false;
    public Usuario user;

    private static PerfilFragment uniqueInstance = null;

    public static MainActivity mainActivity;

    public PerfilFragment() {
        isInitializedFriend = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(Context context) {

        if(uniqueInstance == null){
            uniqueInstance = new PerfilFragment();
        }

        return uniqueInstance;
    }

    public void initializeWithFriend(Usuario usuario){
        usuario = user;
        isInitializedFriend = true;
        Log.d("funfo", "sim!!!");
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

        userName = getView().findViewById(R.id.tv_name);
        userAdress = getView().findViewById(R.id.tv_address);
        userDesc = getView().findViewById(R.id.tv_desc);
        numeroBuilds = getView().findViewById(R.id.numberBuild);
        numeroBuilds.setText(String.valueOf( MyBuilds.getInstance().getBuildsSize()));

    }
}
