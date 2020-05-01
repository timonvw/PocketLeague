package com.e.pocketleague;

import java.util.List;

public class CustomVolleyListener
{
    public interface CustomVolleyListenerChampion
    {
        void onVolleyResponse(List<Champion> champions);
    }

    public interface  CustomVolleyListenerProfile
    {
        void onVolleyResponseUser(User user);
    }
}
