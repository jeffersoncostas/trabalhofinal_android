package com.example.jeffe.trabalho_final.Build;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        Log.d("createdMyBuilds","xxxx");
        listaDeBuilds = new ArrayList<>();
    }

    public void enviarNovaBuild(List<Item> nBuild,String buildname){
        Log.d("enviandoNovaBuild","xds");
        final BuildCompleta novaBuild = new BuildCompleta(nBuild, buildname);

        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("enviandoNovaBuild","aqui: " + novaBuild.toString());
                DatabaseReference buildListRef = databaseUsers.child("userBuild");
                buildListRef.push().setValue(novaBuild);
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


      /*  listaDeBuilds.add(novaBuild); */



    }

    public List<BuildCompleta> getBuilds(){
        Log.d("getbuild",":::::::" + listaDeBuilds);

        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isOkay;
                for (DataSnapshot snapshot : dataSnapshot.child("userBuild").getChildren()) {

                    Log.d("aaaz", "bbbbbbb:" + dataSnapshot.child("userBuild").getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return listaDeBuilds;
    }

    public void deleteBuild(BuildCompleta item) {
        listaDeBuilds.removeAll(Arrays.asList(item));

    }

    public int getBuildsSize(){
        return listaDeBuilds.size();
    }

}
