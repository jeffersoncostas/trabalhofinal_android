package com.example.jeffe.trabalho_final.Build;

import android.util.Log;

import com.example.jeffe.trabalho_final.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyFriends {

    public List<Usuario> listaDeAmigos;
    private static MyFriends ourInstance = null;

    public static MyFriends getInstance() {
        if(ourInstance == null){
            ourInstance = new MyFriends();
        }
        return ourInstance;
    }

    private MyFriends() {
        Log.d("createdMyFriends","xxxx");
        listaDeAmigos = new ArrayList<>();
    }

   /* public void setAmigo(String userId, List<Item> userBuild, List<String> friendsList, String userName, String userDescription, String userLocation){
        Log.d("enviandoNovoAmigo","xds");
        Usuario newUser = new Usuario(userId, userBuild, friendsList, userName, userDescription, userLocation);
        listaDeAmigos.add(newUser);

    } */

    public List<Usuario> getUsers(){
        Log.d("getfriends","Mybuilds");
        return listaDeAmigos;
    }

    public void deleteUser(BuildCompleta item) {
        listaDeAmigos.removeAll(Arrays.asList(item));

    }

    public int getBuildsSize(){
        return listaDeAmigos.size();
    }

}