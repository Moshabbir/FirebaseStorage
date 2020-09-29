package com.example.firebasestorage;

public class Upload {
    private String imageName;
    private String imageUrl;

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Upload(){

    }

    public Upload (String imageName, String imageUrl)
    {
        this. imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public String getImageName(){
        return imageName;
    }

    public void setImageName (String imageName){
        this.imageName = imageName;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public void setImageUrl(String imageUrl){
        this. imageUrl = imageUrl;
    }


}
