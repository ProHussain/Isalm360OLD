package com.hashmac.islam360.model;

import java.io.Serializable;



public class Azkar implements Serializable {

    // private variables
    int id;
    String name;
    String azkar;
    String category;
    String fileName;
    String fav;
    String count;
    String arabic;

    // Empty constructor
    public Azkar() {

    }

    // Azkar constructor
    public Azkar(int id, String name, String azkar, String category,
                 String fileName, String fav) {
        this.id = id;
        this.name = name;
        this.azkar = azkar;
        this.category = category;
        this.fileName = fileName;
        this.fav = fav;

    }

    public Azkar(int id, String name, String azkar, String category, String fileName, String fav, String arabic) {
        this.id = id;
        this.name = name;
        this.azkar = azkar;
        this.category = category;
        this.fileName = fileName;
        this.fav = fav;
        this.arabic = arabic;
    }

    // Author constructor
    public Azkar(String name, String fileName, String count) {

        this.name = name;

        this.fileName = fileName;

        this.count = count;
    }



    // getting ID
    public int getID() {
        return this.id;
    }

    // setting id
    public void setID(int keyId) {
        this.id = keyId;
    }

    // getting name
    public String getName() {
        return this.name;
    }

    // setting name
    public void setName(String name) {
        this.name = name;
    }

    // getting Azkar
    public String getAzkar() {
        return this.azkar;
    }

    // setting Azkar
    public void setAzkar(String azkar) {
        this.azkar = azkar;
    }

    // getting categoty
    public String getCategory() {
        return this.category;
    }

    // setting categoty
    public void setCategory(String category) {
        this.category = category;
    }

    // getting fileName
    public String getFileName() {
        return this.fileName;
    }

    // setting fileName
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // getting favorite
    public String getFav() {
        return this.fav;
    }

    // setting favorite
    public void setFav(String fav) {
        this.fav = fav;
    }

    // getting counter
    public String getCount() {
        return this.count;
    }

    // setting counter
    public void setCount(String count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArabic() {
        return arabic;
    }

    public void setArabic(String arabic) {
        this.arabic = arabic;
    }
}
