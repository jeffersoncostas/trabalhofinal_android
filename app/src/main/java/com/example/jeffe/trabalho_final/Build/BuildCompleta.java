package com.example.jeffe.trabalho_final.Build;

import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BuildCompleta {


    private String buildId;
    private String buildName;
    private List<Item> listaItemsBuild;
    private Date dataDeCriacao;

    public BuildCompleta( List<Item> listaItemsBuild,String BuildName) {
        this.buildName = BuildName;
        this.listaItemsBuild = listaItemsBuild;
        this.dataDeCriacao = new Date();
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


    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String returnDate() {
        String data = "dd/MM/yyyy";
        String hora = "HH:mm";

        DateFormat formatterData = new SimpleDateFormat(data);
        DateFormat formatterHora = new SimpleDateFormat(hora);

        return "Criada em " + formatterData.format(this.dataDeCriacao) + " às " + formatterHora.format(this.dataDeCriacao);
    }
}
