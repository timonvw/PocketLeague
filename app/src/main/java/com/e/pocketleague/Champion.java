package com.e.pocketleague;

public class Champion
{
    //basic info
    private String name;
    private int key;
    private String title;
    private String sprite;
    private String[] tags;
    private AdvancedInfo moreInfo;

    //constructor
    public Champion (String name, int key, String title, String sprite, String[] tags)
    {
        this.name = name;
        this.key = key;
        this.title = title;
        this.sprite = sprite;
        this.tags = tags;
    }

    //contructor method overload if I want to create one with more info
    public Champion (String name, int key, String title, String sprite, String[] tags, AdvancedInfo moreInfo)
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
    public String getSprite() { return sprite; }
    public String[] getTags() { return tags; }
    public AdvancedInfo getMoreInfo() { return moreInfo; }

    public void setMoreInfo(AdvancedInfo moreInfo)
    {
        this.moreInfo = moreInfo;
    }

    //class to string
    public String toString()
    {
        return getClass().getName()+" "+name;
    }
}

