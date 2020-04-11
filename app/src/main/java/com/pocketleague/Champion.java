package com.pocketleague;

import android.media.Image;
import java.util.List;

public class Champion
{
    //basic info
    private String name;
    private int key;
    private String title;
    private Image sprite;
    private String[] tags;
    private AdvancedInfo moreInfo;

    //constructor
    public Champion (String name, int key, String title, Image sprite, String[] tags)
    {
        this.name = name;
        this.key = key;
        this.title = title;
        this.sprite = sprite;
        this.tags = tags;
    }

    //contructor method overload if I want to create one with more info
    public Champion (String name, int key, String title, Image sprite, String[] tags, AdvancedInfo moreInfo)
    {
        this.name = name;
        this.key = key;
        this.title = title;
        this.sprite = sprite;
        this.tags = tags;
        this.moreInfo = moreInfo;
    }

    //getters en setters
    public String getName() { return name; }
    public String getTitle() { return title; }
    public Image getSprite() { return sprite; }
    public String[] getTags() { return tags; }
    public AdvancedInfo getMoreInfo() { return moreInfo; }

    public void setMoreInfo()
    {
        //TODO: advanced info setter 
    }

    //class to string
    public String toString()
    {
        return getClass().getName()+" "+name;
    }
}
