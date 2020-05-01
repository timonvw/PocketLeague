package com.e.pocketleague;

import android.media.Image;

import java.util.List;

public class AdvancedInfo
{
    //advanced info for champion
    private int attack;
    private int defense;
    private int magic;
    private int difficulty;
    private String lore;
    private List<Spell> spells;

    //constructor
    public AdvancedInfo (int attack, int defense, int magic, int difficulty, String lore, List<Spell> spells)
    {
        this.attack = attack;
        this.defense = defense;
        this.magic = magic;
        this.difficulty = difficulty;
        this.lore = lore;
        this.spells = spells;
    }

    //getters en setters
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getMagic() { return magic; }
    public int getDifficulty() { return difficulty; }
    public String getLore(){ return lore; }
    public List<Spell> getSpells() { return spells; }

    //class to string
    public String toString()
    {
        return getClass().getName();
    }

}

