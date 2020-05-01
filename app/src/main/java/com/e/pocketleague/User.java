package com.e.pocketleague;

public class User
{
    //basic info
    private String name;
    private String id;
    private String profileIconId;
    private String level;

    //constructor
    public User (String name, String id, String profileIconId, String level)
    {
        this.name = name;
        this.id = id;
        this.profileIconId = profileIconId;
        this.level = level;
    }

    //getters en setters
    public String getName() { return name; }
    public String getProfileIconId() { return profileIconId; }
    public String getLevel() { return level; }

    //class to string
    public String toString()
    {
        return getClass().getName()+" "+name;
    }
}
