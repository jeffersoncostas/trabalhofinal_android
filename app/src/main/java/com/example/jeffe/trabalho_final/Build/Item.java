package com.example.jeffe.trabalho_final.Build;

public class Item {

    public String ItemId;
    public String DeviceName;
    public String itemIcon_url;
    public String ShortDesc;
    public int Price;
    public boolean IsUsing;

    public Item(String itemId, String deviceName, String itemIcon_url, String shortDesc, int price,boolean isUsing) {
        ItemId = itemId;
        DeviceName = deviceName;
        this.itemIcon_url = itemIcon_url;
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

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public boolean isUsing() {
        return IsUsing;
    }

    public void setUsing(boolean using) {
        IsUsing = using;
    }
}
