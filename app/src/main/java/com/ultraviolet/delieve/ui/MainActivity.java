package com.ultraviolet.delieve.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.ultraviolet.delieve.R;


public class MainActivity extends AppCompatActivity {

    private Fragment sendFragment, deliverFragment, myPageFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, sendFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, deliverFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, myPageFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Log.d("credt", String.valueOf(navigation.getHeight()));

        sendFragment = new SendFragment();
        deliverFragment = new DeliverFragment();
        myPageFragment = new MyPageFragment();

        sendFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(
                R.id.fragment_container, sendFragment).commit();

    }

}
