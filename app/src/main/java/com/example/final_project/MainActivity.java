package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.final_project.databinding.ActivityMainBinding;
import com.example.final_project.databinding.CustomDialogBinding;
import com.example.final_project.movie.activity.MovieInfoActivity;
import com.example.final_project.soccer.SoccerActivity;
import com.example.final_project.ticket.TicketActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public ActionBarDrawerToggle actionBarDrawerToggle;
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.chooselanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Show a dialog to allow the user to choose the language.
                 * When a language option is selected, change the application language accordingly.
                 */
                final Dialog customDialog = new Dialog(MainActivity.this);
                CustomDialogBinding dialogBinding = CustomDialogBinding.inflate(LayoutInflater.from(MainActivity.this));
                customDialog.setContentView(dialogBinding.getRoot());

                RadioGroup radioGroup = customDialog.findViewById(R.id.radioGroup);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = customDialog.findViewById(checkedId);
                        String selectedLanguage = radioButton.getText().toString();

                        /* Change the language of the app based on the selected language */
                        changeAppLanguage(selectedLanguage);
                    }
                });

                customDialog.show();

            }
        });
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,binding.myDrawerLayout, binding.toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.myDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.navigatioView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.ticket){
                    Intent intent = new Intent(getApplication(), TicketActivity.class);
                    startActivity(intent);
                }else if(id == R.id.pexel){

                }else if(id == R.id.movie_in){
                    Intent intent = new Intent(getApplication(), MovieInfoActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.soccer){
                    Intent intent = new Intent(getApplication(), SoccerActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    private void changeAppLanguage(String language) {
        /* Change the language of the application based on the selected language.
         * Supported languages: British English, American English.
         * @param language The selected language as a string.
         */
        switch (language) {
            case "British English":
                changeLanguage("en-GB");
                break;
            case "American Language":
                changeLanguage("en");
                break;
            default:
                break;
        }
    }
    private void changeLanguage(String languageCode) {
        /* Change the language of the application to the specified language code.
         * @param languageCode The language code to set (e.g., "en", "en-GB").
         */
        Locale newLocale = new Locale(languageCode);
        Locale.setDefault(newLocale);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(newLocale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        // You may need to recreate the activity to apply the language change
        recreate();
    }
}