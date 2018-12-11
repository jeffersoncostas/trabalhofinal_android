package com.example.jeffe.trabalho_final.Build;

import android.os.Build;
import android.util.Log;

import com.example.jeffe.trabalho_final.Requests.FirebaseRequests;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyBuilds {

    FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("Users").child(fUser.getUid());

    public List<BuildCompleta> listaDeBuilds;
    private static MyBuilds ourInstance = null;


    public static MyBuilds getInstance() {
        if(ourInstance == null){
            ourInstance = new MyBuilds();
        }
        return ourInstance;
    }

    private MyBuilds() {
        listaDeBuilds = new ArrayList<>();
    }

    public void enviarNovaBuild(List<Item> nBuild,String buildname){
        final BuildCompleta novaBuild = new BuildCompleta(nBuild, buildname);
        FirebaseRequests.GetInstance().CreateBuild(novaBuild);
    }

    public void getBuilds(BuildListsFragment buildListsFragment){
            FirebaseRequests.GetInstance().GetUserBuilds(buildListsFragment);
    }

    public void deleteBuild(BuildCompleta item, BuildListsFragment buildListsFragment) {
        FirebaseRequests.GetInstance().DeleteBuild(item, buildListsFragment);
    }

    public int getBuildsSize(){
        return listaDeBuilds.size();
    }



}
