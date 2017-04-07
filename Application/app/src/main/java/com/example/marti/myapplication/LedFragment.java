package com.example.marti.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class LedFragment extends Fragment {

    private ListView ledsLV;
    private SharedPreferences preferences;
    private LedListItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_led, container, false);
        ledsLV = (ListView) rootView.findViewById(R.id.ledsLV);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
        adapter = new LedListItemAdapter(getContext().getApplicationContext(), R.layout.leds_list_item);

        final HashMap<String, ArrayList<String>> lights = GETfromMainServer.getAllLights();


        Toast.makeText(getContext().getApplicationContext(),"HEY",Toast.LENGTH_LONG).show();

        CountDownTimer timer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish()
            {
                Toast.makeText(getContext().getApplicationContext(), lights.toString(), Toast.LENGTH_SHORT).show();
                saveLightsState(lights);
                try
                {
                    ledsLV.setAdapter(adapter);
                }
                catch (Exception ex)
                {
                    Log.e("FUCK",ex.toString());
                }
            }
        };
        timer.start();

        return rootView;
    }

    private void saveLightsState(HashMap<String,ArrayList<String>> lights)
    {
        System.out.print("a");

        ArrayList<String> ids = lights.get("id");
        ArrayList<String> states = lights.get("state");

        for (int i = 0; i < ids.size(); i++)
        {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("light" + ids.get(i), states.get(i));
            try
            {
                adapter.add(new LedListItem(getContext().getApplicationContext(), ids.get(i), Boolean.parseBoolean(states.get(i))));
            }
            catch (Exception ex)
            {
                Log.e("asd",ex.toString());
            }
        }

    }


}
