package com.example.habitapp.dto;

public class CharacterData {
    private String image;
    private String message;

    public CharacterData(String image,String message){
        this.image = image;
        this.message = message;
    }

    public String getImage(){
        return image;
    }

    public String getMessage(){
        return message;
    }
    
}
