package com.example.final_project.pixels.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.final_project.databinding.FragmentSavedImageBinding;
import com.example.final_project.pixels.SQLiteDatabase.PexelDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.example.final_project.R;
import com.example.final_project.pixels.adapter.ImageUrlAdapter;

import java.util.ArrayList;
import java.util.List;


public class SavedImageFragment extends Fragment {

    FragmentSavedImageBinding binding;
    private ImageUrlAdapter adapter;

    private PexelDatabaseHelper databaseHelper;
    List<String> urlsList = new ArrayList<>();

    public SavedImageFragment() {
        // Required empty public constructor
    }


    public static SavedImageFragment newInstance(String param1, String param2) {
        SavedImageFragment fragment = new SavedImageFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new PexelDatabaseHelper(requireContext());

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
/*
         Inflate the layout for this fragment
*/
        binding = FragmentSavedImageBinding.inflate(inflater, container, false);

        urlsList = getAllUrlsFromDatabase();
        adapter = new ImageUrlAdapter(getContext(), new ImageUrlAdapter.ViewHandler() {
            @Override
            public void onDeleteButtonClicked(int position) {

                new AlertDialog.Builder(requireContext())
                        .setTitle(getString(R.string.delete_alert))
                        .setMessage(getString(R.string.delete_message))
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String urlToDelete = urlsList.get(position);
                                boolean isDeleted = databaseHelper.deleteUrl(urlToDelete);
                                if (isDeleted) {
                                    urlsList.clear();
                                    urlsList =
                                            getAllUrlsFromDatabase();
                                    adapter.updateList(urlsList);

                                    Snackbar.make(binding.main,getString(R.string.item_deleted_message), Snackbar.LENGTH_SHORT).show();


                                } else {
                                    Snackbar.make(binding.main,getString(R.string.failed_delete), Snackbar.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();

            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);
        adapter.updateList(urlsList);
        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_saved_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Print the list of URLs in a toast
        // Print the list of URLs in logcat
        for (String url : urlsList) {
            Log.d("URLs", url);
        }
    }


    private List<String> getAllUrlsFromDatabase() {
        /* Retrieve all saved image URLs from the local database using the PexelDatabaseHelper.
         * - Create a new instance of PexelDatabaseHelper.
         * - Call the getAllUrls method to fetch all URLs from the database.
         * - Return the list of URLs.
         */
        PexelDatabaseHelper dbHelper = new PexelDatabaseHelper(requireContext());
        return dbHelper.getAllUrls();
    }
}