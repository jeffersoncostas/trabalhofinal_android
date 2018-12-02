package com.example.jeffe.trabalho_final.Requests;

import android.util.Log;

import com.example.jeffe.trabalho_final.Build.BuildFragment;
import com.example.jeffe.trabalho_final.Build.Item;
import com.example.jeffe.trabalho_final.Noticias.Noticia;
import com.example.jeffe.trabalho_final.Noticias.NoticiasFragment;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequests {

    int newsCount = 5;
    static final String newsUrl = "https://cms.smitegame.com/wp-json/smite-api/";
    static HttpRequests uniqueInstance = null;
    private static Retrofit retrofit = null;

    private HttpRequests(){}

    public static HttpRequests GetInstance(){
        if (uniqueInstance == null){

            uniqueInstance = new HttpRequests();
        }

        return uniqueInstance;
    }

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(newsUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public void getNews(NoticiasFragment noticiasFragment){

        ApiInterface apiService =
                HttpRequests.getClient().create(ApiInterface.class);
        Call<List<Noticia>> call = apiService.getNews(30);
        call.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                List<Noticia> noticias = response.body();
                noticiasFragment.noticiaList.clear();

                noticias.forEach((noticia)->{
                    noticiasFragment.noticiaList.add(noticia);
                    noticiasFragment.noticiaAdapter.notifyDataSetChanged();
                });

                Log.d("deu certo", "pqp");
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                Log.e("ax", t.toString());
            }
        });


    }

    public void getItems(BuildFragment buildFragment){
        ApiInterface apiService = HttpRequests.getClient().create(ApiInterface.class);

        Call<List<Item>> call = apiService.getItems();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                List<Item> itens = response.body();

                buildFragment.itemList.clear();

                itens.forEach((item -> {

                    buildFragment.itemList.add(item);
                    buildFragment.itensAdapter.notifyDataSetChanged();
                }));
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
    }
}
