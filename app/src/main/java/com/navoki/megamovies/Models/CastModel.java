package com.navoki.megamovies.Models;

/**
 * Created by Shivam Srivastava on 6/6/2018.
 */
public class CastModel {
    private String character,name,profile_path;

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
