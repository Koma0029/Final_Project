package com.example.final_project.pixels.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.final_project.databinding.FragmentImageBinding;
import com.example.final_project.pixels.SQLiteDatabase.PexelDatabaseHelper;
import com.example.final_project.R;
import com.example.final_project.pixels.activity.PexelsActivity;


public class ImageFragment extends Fragment {
    private FragmentImageBinding binding;
    private PexelsActivity mainActivity;

    int id ;
    int width ;
    int height;
    String url ;
    String original;

    public ImageFragment() {
        // Required empty public constructor
    }

    public static ImageFragment newInstance(String param1, String param2) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PexelsActivity) {
            mainActivity = (PexelsActivity) context;
        } else {
            throw new ClassCastException("Activity must be MainActivity");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentImageBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
             id = bundle.getInt("id");
             width = bundle.getInt("width");
             height = bundle.getInt("height");
            url = bundle.getString("url");
            original = bundle.getString("original");
        }


        Log.d("values", "onCreateView:  "+id +width  +height +url +original);
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(requireContext())
                .load(original)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.img);
        binding.url.setText(url);
        binding.height.setText(String.valueOf(height));
        binding.width.setText(String.valueOf(width));
        binding.saveimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click event here

                saveImageUrlToDatabase(original);
            }
        });

        binding.url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    private void saveImageUrlToDatabase(String imageUrl) {
        /* Save the image URL to the local database using the PexelDatabaseHelper.
         * - Create a new instance of PexelDatabaseHelper.
         * - Insert the image URL into the database.
         * - Display a toast message indicating success or failure.
         * - Log the result.
         * - Navigate to the savedImageFragment using the mainActivity's NavController.
         */
        PexelDatabaseHelper dbHelper = new PexelDatabaseHelper(getActivity());
        boolean isSaved = dbHelper.insertUrl(imageUrl);

        if (isSaved) {


            Toast.makeText(getActivity(), "saved successfully", Toast.LENGTH_SHORT).show();

            Log.d("SaveImage", "Image URL saved successfully: " + imageUrl);

        } else {
            Toast.makeText(getActivity(), "Failed to save image", Toast.LENGTH_SHORT).show();

            Log.e("SaveImage", "Failed to save image URL: " + imageUrl);
        }
    }

}