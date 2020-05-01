package com.e.pocketleague;

public class Spell
{
    public String name;
    private String description;

    //TODO: cooldown etc in future

    //constructor
    public Spell (String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    //getters and setters
    public String getName() { return name; }
    public String getDescription() { return description; }

    //class to string
    public String toString()
    {
        return getClass().getName()+" "+name;
    }
}
