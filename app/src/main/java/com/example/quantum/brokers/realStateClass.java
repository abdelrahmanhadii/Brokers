package com.example.quantum.brokers;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.io.Serializable;

/**
 * Created by Abdel-Rahman on 11/07/2017.
 */

public class realStateClass implements Serializable {
    private String nameHolder;
    private String mobileHolder;
    private String addressHolder;
    private String priceHolder;
    private String latitudeHolder;
    private String longtudeHolder;
    private String imgHolder;

    public realStateClass(){

    }

    public realStateClass(String nameHolder, String mobileHolder, String addressHolder, String priceHolder, String latitudeHolder, String longtudeHolder, String imgHolder) {
        this.nameHolder = nameHolder;
        this.mobileHolder = mobileHolder;
        this.addressHolder = addressHolder;
        this.priceHolder = priceHolder;
        this.latitudeHolder = latitudeHolder;
        this.longtudeHolder = longtudeHolder;
        this.imgHolder = imgHolder;
    }

    public String getNameHolder() {
        return nameHolder;
    }

    public void setNameHolder(String nameHolder) {
        this.nameHolder = nameHolder;
    }

    public String getMobileHolder() {
        return mobileHolder;
    }

    public void setMobileHolder(String mobileHolder) {
        this.mobileHolder = mobileHolder;
    }

    public String getAddressHolder() {
        return addressHolder;
    }

    public void setAddressHolder(String addressHolder) {
        this.addressHolder = addressHolder;
    }

    public String getPriceHolder() {
        return priceHolder;
    }

    public void setPriceHolder(String priceHolder) {
        this.priceHolder = priceHolder;
    }

    public String getLatitudeHolder() {
        return latitudeHolder;
    }

    public void setLatitudeHolder(String latitudeHolder) {
        this.latitudeHolder = latitudeHolder;
    }

    public String getLongtudeHolder() {
        return longtudeHolder;
    }

    public void setLongtudeHolder(String longtudeHolder) {
        this.longtudeHolder = longtudeHolder;
    }

    public String getImgHolder() {
        return imgHolder;
    }

    public void setImgHolder(String imgHolder) {
        this.imgHolder = imgHolder;
    }
}
