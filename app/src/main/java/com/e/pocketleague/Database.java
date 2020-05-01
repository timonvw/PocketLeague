package com.e.pocketleague;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database extends AppCompatActivity
{
    private static Database databaseInstance;

    //const api key
    final static private String apiKey = "Your_Riot_API_key";

    //interface que en listeners
    private static Context context;
    private static RequestQueue requestQueue;
    private static CustomVolleyListener.CustomVolleyListenerChampion listener = null;
    private static CustomVolleyListener.CustomVolleyListenerProfile listener2 = null;

    //singelton gebruiken omdat je maar 1 static instnace van het database object wilt hebben
    //region singleton
    //private constructor.
    private Database()
    {
        //veiligheid check zodat je alleen de instance kan aanspreken
        if (databaseInstance != null)
        {
            throw new RuntimeException("Use getInstance() method ;)");
        }
    }

    //singleton static instance
    public static Database getInstance(Context context, CustomVolleyListener.CustomVolleyListenerChampion listener)
    {
        //checken of er al een instance van is
        if (databaseInstance == null)
        {
            synchronized (Database.class)
            {
                //nieuwe instance aanmaken als er geen is
                if (databaseInstance == null) databaseInstance = new Database();
            }
        }

        //listener en que voor volley
        Database.context = context;
        Database.listener = listener;
        Database.requestQueue = Volley.newRequestQueue(context);

        return databaseInstance;
    }

    //method overloading voor andere interface volley response
    public static Database getInstance(Context context, CustomVolleyListener.CustomVolleyListenerProfile listener)
    {
        //checks if there is already an instance
        if (databaseInstance == null)
        {
            synchronized (Database.class)
            {
                //create new instance when none available
                if (databaseInstance == null) databaseInstance = new Database();
            }
        }

        Database.context = context;
        Database.listener2 = listener;
        Database.requestQueue = Volley.newRequestQueue(context);

        return databaseInstance;
    }
    //endregion

    //Alle champions ophalen
    public void getChampions()
    {
        final String url = "http://ddragon.leagueoflegends.com/cdn/10.7.1/data/en_US/champion.json";
        final List<Champion> champions = new ArrayList<Champion>();

        //alle champions ophalen uit de leagoe of legends api als jsonObject
        try
        {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            try
                            {
                                //data opbject ophalen en in object stoppem
                                JSONObject data = response.getJSONObject("data");
//
                                //door alle objecten loopen in het data object
                                for(int i = 0; i < data.names().length(); i++)
                                {
                                    //alle gegevens invullen, champion van maken en aan de champions lijst toevoegen
                                    JSONObject champJSON = data.getJSONObject(data.names().getString(i));

                                    String sprite = champJSON.getJSONObject("image").getString("full").toLowerCase();
                                    String name = champJSON.getString("name");
                                    String title = champJSON.getString("title");
                                    int key = champJSON.getInt("key");
                                    String[] tags = new String[champJSON.getJSONArray("tags").length()];

                                    //door de tags loopen
                                    for (int j = 0; j < champJSON.getJSONArray("tags").length(); j++)
                                    {
                                        tags[j] = champJSON.getJSONArray("tags").getString(j);
                                    }

                                    champions.add(new Champion(name,key,title,sprite,tags));
                                }

                                //System.out.println("In de response");
                                //aan de interface meegeven zodat ie opgehaald kan worden vanuit een andere Activity
                                listener.onVolleyResponse(champions);
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }

                            //System.out.println(champions);
                        }

                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError e)
                        {
                            e.printStackTrace();
                        }
                    });

            requestQueue.add(jsonObjectRequest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //System.out.println("voor de return");
        //return champions;
    }

    //profiel ophalen
    public void getProfile(String name)
    {
        //profiel ophalen uit de leagoe of legends API met de naam uit de settings
        final String url = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + name;

        try
        {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            try
                            {
                                //nieuwe gebruiken maken, gegevens invullen terug sturen
                                User user = new User(
                                        response.getString("name"),
                                        response.getString("id"),
                                        response.getString("profileIconId"),
                                        response.getString("summonerLevel"));

                                listener2.onVolleyResponseUser(user);
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }

                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError e)
                        {
                            e.printStackTrace();
                        }
                    })
                    {
                        //API key meesturen
                        @Override
                        public Map getHeaders() throws AuthFailureError
                        {
                            HashMap headers = new HashMap();
                            headers.put("Content-Type", "application/json");
                            headers.put("X-Riot-Token", apiKey);
                            return headers;
                        }
                    };

            requestQueue.add(jsonObjectRequest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String testMe()
    {
        return "TEST SINGLETON";
    }
}
