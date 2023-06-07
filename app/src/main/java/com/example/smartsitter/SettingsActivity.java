package com.example.smartsitter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartsitter.ui.settings.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Replace the container with the settings fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment settingsFragment = new Fragment(); // Assuming your settings fragment is named SettingsFragment
        fragmentTransaction.replace(R.id.settings_container, settingsFragment);
        fragmentTransaction.commit();
        ImageView backgroundImage = findViewById(R.id.background);
        backgroundImage.setImageResource(R.drawable.background);
    }

}
