package com.example.final_project.soccer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.final_project.R;
import com.example.final_project.databinding.ActivitySoccerBinding;
import com.example.final_project.databinding.CustomDialogBinding;
import com.example.final_project.movie.Singleton;

import java.util.Locale;

public class SoccerActivity extends AppCompatActivity {
    NavController navController;

    ActivitySoccerBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySoccerBinding.inflate(getLayoutInflater());
        Singleton.getInstance().sharedPrefs.initialisePrefs(this);
        // Getting language from SharedPreferences with default value "en"
        String language = Singleton.getInstance().sharedPrefs.getString("language", "en");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        createConfigurationContext(config);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.soccer_fragment);
        setSupportActionBar(binding.toolbar);

    }
    public NavController getNavController() {
        return navController;
    }

    @Override
    protected void onResume() {
        super.onResume();
  //      binding.toolbar.setTitle(getResources().getString(R.string.jashan_deep));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Go back to previous activity
            return true;
        }
        if (id == R.id.nav_language) {
            /* Show a dialog to allow the user to choose the language.
             * When a language option is selected, change the application language accordingly.
             */
                final Dialog customDialog = new Dialog(this);
                CustomDialogBinding dialogBinding = CustomDialogBinding.inflate(LayoutInflater.from(this));
                customDialog.setContentView(dialogBinding.getRoot());

                RadioGroup radioGroup = customDialog.findViewById(R.id.radioGroup);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = customDialog.findViewById(checkedId);
                        String selectedLanguage = radioButton.getText().toString();
                        changeAppLanguage(selectedLanguage);
                    }
                });


                customDialog.show();
            }
            if (id == R.id.nav_instructions) {
                /* Create an AlertDialog to display instructions to the user.
                 * Set the title and message of the dialog with appropriate strings.
                 * Add a positive button "Cancel" to dismiss the dialog when clicked.
                 */
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle(getResources().getString(R.string.instructions));
                dialog.setMessage(getResources().getString(R.string.match_instruction));
                dialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }

            return true;
        }
    private void changeAppLanguage(String language) {
        /* Change the language of the application based on the selected language.
         * Supported languages: British English, American English.
         * @param language The selected language as a string.
         */
        switch (language) {
            case "British English":
                changeLanguage("en");
                break;
            case "American English":
                changeLanguage("en-GB");
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

/*
        You may need to recreate the activity to apply the language change
*/
        recreate();
    }

    }

