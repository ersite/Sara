package com.example.marti.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class FragmentActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Intent getStates, getWeather, postState;
    private SharedPreferences preferences;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_manager);

        checkPermissions();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        checkPermissions();

        getWeather = new Intent(FragmentActivity.this, GETweather.class);
        postState = new Intent(FragmentActivity.this, POSTtoMainServer.class);
        getStates = new Intent(FragmentActivity.this, GETfromMainServer.class);
        startService(getStates);
        stopService(getStates);
        startService(getWeather);
        stopService(getWeather);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        int[] id = {R.drawable.led,
                R.drawable.weather,
                R.drawable.alarm};
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(id[i]);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.speechMenuItem)
        {
            SpeechRecognition speechRecognition = new SpeechRecognition();
            speechRecognition.context = getApplicationContext();
            speechRecognition.startListen();
        }

        return true;
    }

    private void checkPermissions()
    {
        ActivityCompat.requestPermissions(FragmentActivity.this, new String[]{Manifest.permission.RECORD_AUDIO},0);
    }

    public void changeSwitch(View view)
    {
        TextView ledTV = (TextView)view.findViewById(R.id.ledTV);
        Integer index = Integer.parseInt(ledTV.getText().toString().replace("Light ",""));

        if(LedFragment.adapter.getItem(index).state) LedFragment.adapter.getItem(index).state = false;
        else LedFragment.adapter.getItem(index).state = false;


    }

    private boolean isProtected = false;
    public void protect(View view)
    {
        if (isProtected)
        {
            view.setBackgroundResource(0);
            isProtected = false;
        }
        else
        {
            view.setBackgroundResource(R.color.colorAccent);
            isProtected = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fragment_manager, menu);
        return true;
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    LedFragment ledFragment = new LedFragment();
                    return ledFragment;
                case 1:
                    FragmentClass weatherFragment = new FragmentClass();
                    return weatherFragment;
                case 2:
                    AlarmFragment alarmFragment = new AlarmFragment();
                    return alarmFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return  "";
                case 1:
                    return "";
                case 2:
                    return "";
            }
            return null;
        }
    }
}
