package com.example.jeffe.trabalho_final.Build;

import java.util.List;

public class BuildCompleta {



    public String buildName;
    public List<Item> listaItemsBuild;

    public BuildCompleta( List<Item> listaItemsBuild,String buildName) {
        this.buildName = buildName;
        this.listaItemsBuild = listaItemsBuild;
    }

    public BuildCompleta(List<Item> listaItemsBuild) {
        this.listaItemsBuild = listaItemsBuild;
    }

    public List<Item> getListaItemsBuild() {
        return listaItemsBuild;
    }

    public void setListaItemsBuild(List<Item> listaItemsBuild) {
        this.listaItemsBuild = listaItemsBuild;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

}
