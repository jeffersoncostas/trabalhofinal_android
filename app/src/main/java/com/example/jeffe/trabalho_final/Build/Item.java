package com.example.jeffe.trabalho_final.Build;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("ItemId")
    private String ItemId;

    @SerializedName("DeviceName")
    private String DeviceName;

    @SerializedName("itemIcon_URL")
    private String itemIcon_url;

    @SerializedName("ShortDesc")
    private String ShortDesc;

    @SerializedName("Price")
    private Long Price;
    public boolean IsUsing;

    public Item(){}
    public Item(String itemId, String deviceName, String itemIconurl, String shortDesc, Long price, boolean isUsing) {
        ItemId = itemId;
        DeviceName = deviceName;
        itemIcon_url = itemIconurl;
        ShortDesc = shortDesc;
        Price = price;
        IsUsing = isUsing;
    }


    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public String getItemIcon_url() {
        return itemIcon_url;
    }

    public void setItemIcon_url(String itemIcon_url) {
        this.itemIcon_url = itemIcon_url;
    }

    public String getShortDesc() {
        return ShortDesc;
    }

    public void setShortDesc(String shortDesc) {
        ShortDesc = shortDesc;
    }

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
    }

    public boolean isUsing() {
        return IsUsing;
    }

    public void setUsing(boolean using) {
        IsUsing = using;
    }
}
