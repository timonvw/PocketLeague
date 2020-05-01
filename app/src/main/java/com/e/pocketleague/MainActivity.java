package com.e.pocketleague;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //champion lijst vullen
        fillChampions();
    }

    //test hehehe
    public void testMe(View view)
    {
        System.out.println(view.getTag());
    }

    //profile openen
    public void OpenProfile(View view)
    {
        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
    }

    //settings openen
    public void OpenSettings(View view)
    {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }

    //champoins lijst vullen
    private void fillChampions()
    {
        //database singleton in database variable zetten met de custom volleyInterface
        Database database = Database.getInstance(this, new CustomVolleyListener.CustomVolleyListenerChampion() {
            @Override
            public void onVolleyResponse(List<Champion> champions)
            {
                //System.out.println(champions);

                //Hashmap lijst aanmaken zodat we erdoorheen kunnen mappen en kunnen toevoegen aan de Listview
                List<HashMap<String, String>> championList = new ArrayList<>();

                //door de champion lijst heen loopen die we terug gekregen hebben dan de database singleton
                for(Champion champ : champions)
                {
                    //alle data vullen in de hashmap
                    HashMap<String, String> hm = new HashMap<>();
                    hm.put("listview_title", champ.getName());
                    hm.put("listview_discription", champ.getTitle());

                    //photonaam pakken en de laatste 4 chars eraf halen (.png)
                    String photoString =  champ.getSprite().substring(0, champ.getSprite().toLowerCase().length() - 4);
                    hm.put("listview_image", Integer.toString(getResources().getIdentifier(photoString,"drawable",getPackageName())));

                    //door de tags loopen en toevoegen aan de hashmap
                    String tagString = "";
                    for(int i = 0; i < champ.getTags().length; i++)
                    {
                        if(i < (champ.getTags().length -1))
                        {
                            tagString += champ.getTags()[i] + ", ";
                        }
                        else
                        {
                            tagString += champ.getTags()[i];
                        }
                    }
                    hm.put("listview_tags", tagString);

                    championList.add(hm);
                }

                //van en naar lijst zodat de adapter weet welke keys hij ze aan elkaar moet binden
                String[] from = {"listview_image", "listview_title", "listview_discription", "listview_tags",};
                int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description, R.id.listview_item_tags};

                SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), championList, R.layout.listview_item, from, to);
                ListView androidListView = (ListView) findViewById(R.id.list_view);
                androidListView.setAdapter(simpleAdapter);

            }
        });

        //de champios ophalen uit de API en als lijst terug sturen
        database.getChampions();
    }
}
