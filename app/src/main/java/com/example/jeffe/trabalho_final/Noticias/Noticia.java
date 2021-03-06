package com.example.jeffe.trabalho_final.Noticias;


import com.squareup.picasso.Picasso;
import com.google.gson.annotations.SerializedName;

public class Noticia {
    @SerializedName("id")
    private int id;

    @SerializedName("featured_image")
    private String featured_image;

    @SerializedName("title")
    private String title;

    @SerializedName("slug")
    private String slug;

    @SerializedName("author")
    private String author;



    public Noticia(int id, String featured_image, String title, String slug, String author) {
        this.id = id;
        this.featured_image = featured_image;
        this.title = title;
        this.slug = slug;
        this.author = author;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeatured_image() {
        return featured_image;
    }

    public void setFeatured_image(String featured_image) {

        this.featured_image = featured_image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
