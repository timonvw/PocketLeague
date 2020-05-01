package com.e.pocketleague;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity
{
    //const om je summoner name in op te slaan doormiddel van SharedPref
    public static final String SUMMONER_NAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //De opgeslagen summoner name ophalen uit de settings als er niks in zit dan GodlyPhyro gebruiken
        SharedPreferences settings = getSharedPreferences(SettingsActivity.SUMMONER_NAME, 0);
        String summonerName = settings.getString("name", "GodlyPhyro");

        //opgeslagen name in de editText zetten
        TextView name = findViewById(R.id.editText);
        name.setText(summonerName);

    }

    //Profiel pagina openen
    public void OpenProfile(View view)
    {
        startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
    }

    //champions pagina openen
    public void OpenChampions(View view)
    {
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
    }

    //Naam opslaan
    public void SaveName(View view)
    {
        TextView name = findViewById(R.id.editText);

        //Naam opslaan in de sharedpreferences
        SharedPreferences settings = getSharedPreferences(SUMMONER_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("name", name.getText().toString());
        editor.apply();
    }

}
