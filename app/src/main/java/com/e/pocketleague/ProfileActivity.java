package com.e.pocketleague;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //profiel met data vullen
        fillProfile();
    }

    //champions lijst poenen
    public void OpenChampions(View view) {
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }

    //settings openen
    public void OpenSettings(View view) {
        startActivity(new Intent(ProfileActivity.this, SettingsActivity.class));
    }

    //hard refresh
    public void RefreshProfile(View view)
    {
        fillProfile();
        try
        {
            setLocation();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //data ophalen en profiel vullen
    private void fillProfile()
    {
        //database singleton in variable stoppen met de costumvolley interface
        Database database = Database.getInstance(this, new CustomVolleyListener.CustomVolleyListenerProfile() {
            @Override
            public void onVolleyResponseUser(User user) {
                //de user die ik terug krijg in de front end zetten
                ImageView profileImage = findViewById(R.id.imageProfile);
                TextView profileText = findViewById(R.id.textName);
                TextView profileLevel = findViewById(R.id.textLevel);

                profileText.setText(user.getName());
                profileLevel.setText("Level: " + user.getLevel());

                //met picasso de url afbeelding downloaden en in de front end zetten
                Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/10.8.1/img/profileicon/" + user.getProfileIconId() + ".png").into(profileImage);

                //locatie aan profiel toevoegen doormiddel van je gps cooordinaten
                try
                {
                    setLocation();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

        //De gebruikersnaam settings ophalen en meesturen met het request om de juiste gebruiker op te kunne halen als er niks opgeslagen is GodlyPhyro gebruiken
        SharedPreferences settings = getSharedPreferences(SettingsActivity.SUMMONER_NAME, 0);
        String name = settings.getString("name", "GodlyPhyro");

        database.getProfile(name);
    }

    //Vult de locatie in waar je je nu bevindt.
    private void setLocation() throws IOException
    {
        //locatie manager aanmaken
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);

        //stomme check permissie
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //JAJAJAJAJAJAJAJAJ check voor locatie permission
            System.out.println("You need permission mate ;)");
            return;
        }

        //laatste locatie pakken
        Location location = locationManager.getLastKnownLocation(bestProvider);

        //met de locatie en goecoder bepalen
        Geocoder gcd = new Geocoder(ProfileActivity.this, Locale.getDefault());
        //kijken welke adressen er allemaal in de buurt zijn
        List<Address> addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

        //kijken of er adress zijn terug gegeven
        if (addresses.size() > 0)
        {
            //de dichtsbijzijnde adres pakken
            String countryName = addresses.get(0).getCountryName();
            TextView country = findViewById(R.id.textViewLocation);
            country.setText(countryName);
        }
    }
}
