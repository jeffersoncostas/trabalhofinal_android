package com.example.jeffe.trabalho_final.Build;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyBuilds {

    public List<BuildCompleta> listaDeBuilds;
    private static  MyBuilds ourInstance = null;



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

    public void enviarNovaBuild(List<Item> nBuild,String buildName){
        Log.d("enviandoNovaBuild","xds");
        BuildCompleta novaBuild = new BuildCompleta(nBuild,buildName);

        listaDeBuilds.add(novaBuild);

    }

    public List<BuildCompleta> getBuilds(){
        Log.d("getbuild","Mybuilds");
        return listaDeBuilds;
    }

    public int getBuildsSize(){

        return listaDeBuilds.size();
    }

}
