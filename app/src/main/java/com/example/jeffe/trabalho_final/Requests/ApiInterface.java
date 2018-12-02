package com.example.jeffe.trabalho_final.Requests;
import com.example.jeffe.trabalho_final.Noticias.Noticia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface ApiInterface {

    @GET("get-posts/1")
    Call<List<Noticia>> getNews(@Query("per_page") int newsCount);

    @GET("getItems/1")
    Call<List<Noticia>> getItems();
}
