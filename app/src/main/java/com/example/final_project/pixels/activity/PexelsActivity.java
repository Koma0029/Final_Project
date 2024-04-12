package com.example.final_project.pixels.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.example.final_project.R;
import com.example.final_project.databinding.ActivityPexelsBinding;

public class PexelsActivity extends AppCompatActivity {
    NavController navController;

    ActivityPexelsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPexelsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        setSupportActionBar(binding.toolbar);
    }
    public NavController getNavController() {
        return navController;
    }
    }
