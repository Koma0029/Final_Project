package com.example.final_project.pixels.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.final_project.databinding.FragmentPexelsListBinding;
import com.example.final_project.pixels.activity.PexelsActivity;
import com.example.final_project.R;
import com.example.final_project.pixels.adapter.PhotosAdapter;
import com.example.final_project.pixels.model.PhotosResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PexelsListFragment extends Fragment {

    private FragmentPexelsListBinding binding;
    private PexelsActivity pexelsActivity;
    private RequestQueue queue;
    private PhotosAdapter photosAdapter;
    private ArrayList<PhotosResponse.Photo> photosList = new ArrayList<>();
    private final String API_KEY = "b9fp8U9SDZ1imVAuolYWnfbzD3VOnjEHzXRpUN02dFmnTGOoI1BwHFjZ";
    private final String API_URL = "https://api.pexels.com/v1/search";
    private final String TAG = "DataListFragment";

    public static PexelsListFragment newInstance(String param1, String param2) {
        PexelsListFragment fragment = new PexelsListFragment();
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
            pexelsActivity = (PexelsActivity) context;
        } else {
            throw new ClassCastException("Activity must be MainActivity");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPexelsListBinding.inflate(inflater, container, false);

        photosAdapter = new PhotosAdapter(requireContext(), new PhotosAdapter.ViewHandler() {
            @Override
            public void viewHandler(int position, PhotosAdapter.DialogClickType clickType) {
                switch (clickType) {
                    case Image:
                        PhotosResponse.Photo photo = photosList.get(position);
                        int id = photo.getId();
                        int width = photo.getWidth();
                        int height = photo.getHeight();
                        String url = photo.getUrl();
                        String original = photo.getSrc().getOriginal();

                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);
                        bundle.putInt("width", width);
                        bundle.putInt("height", height);
                        bundle.putString("url", url);
                        bundle.putString("original", original);
                        pexelsActivity.getNavController().navigate(R.id.imageFragment,bundle);

                        break;
                    case Delete:
                        // Handle delete click
                        break;
                    case Update:
                        // Handle update click
                        break;
                }
            }

            @Override
            public void imgView(int position, ImageView imageView) {

            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(pexelsActivity));
        binding.recyclerView.setAdapter(photosAdapter);
        loadSearchedText();

        binding.savedimages.setOnClickListener(v -> {
            pexelsActivity.getNavController().navigate(R.id.savedImageFragment);
        });
        binding.btn.setOnClickListener(v -> {
            photosList.clear();
            if(binding.edttext.getText().toString().isEmpty()){
                binding.edttext.setError("Enter the Search Item");
            }else {
                searchPexels(binding.edttext.getText().toString());
                saveUserAuthenticationState(binding.edttext.getText().toString());
            }
        });
        return binding.getRoot();
    }
    private void saveUserAuthenticationState(String item) {
        /* Save user authentication state and searched item to SharedPreferences.
         * - Get the SharedPreferences instance with the app name and private mode.
         * - Create an editor to make changes to the SharedPreferences.
         * - Put the boolean value indicating that an item has been searched.
         * - Put the searched item string.
         * - Apply changes to the SharedPreferences.
         */
        SharedPreferences sharedPreferences = pexelsActivity.getSharedPreferences(
                getString(R.string.app_name),
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("itemsearched", true);
        editor.putString("pexel", item);
        editor.apply();
    }
    private void loadSearchedText() {

        /* Load the searched text from SharedPreferences and set it to the EditText.
         * - Get the SharedPreferences instance with the app name and private mode.
         * - Retrieve the searched item string from SharedPreferences.
         * - Set the retrieved string to the EditText.
         * - If the searched item is not empty, perform a search operation.
         * - Otherwise, log a message indicating that the search view is empty.
         */
        SharedPreferences sharedPreferences = pexelsActivity.getSharedPreferences(
                getString(R.string.app_name),
                Context.MODE_PRIVATE
        );
        String searchedItem = sharedPreferences.getString("pexel", "");
        binding.edttext.setText(searchedItem);
        if(!searchedItem.isEmpty()) {
            searchPexels(searchedItem);
        }else{
            System.out.println("Search View ");
        }
    }

    private void searchPexels(String photoTitle) {
        /* Clear the photosList and log the photoTitle and URL.
         * - Construct the URL for the Pexels API request.
         * - Create a new request queue using Volley.
         * - Define a JsonObjectRequest to fetch data from the Pexels API.
         * - Handle the JSON response.
         * - Parse the response JSON and populate the photosList.
         * - Add the parsed photos to the photosAdapter.
         */
        photosList.clear();
        Log.e(TAG, " photoTitle " + photoTitle);
        String url = API_URL + "?query=" + photoTitle;
        Log.e(TAG, " url " + url);
         queue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        //binding.resulttext.setVisibility(View.GONE);
                        Log.e(TAG, "response " + response);
                        JSONArray photosArray = response.optJSONArray("photos");
                        photosList = new ArrayList<>();
                        if (photosArray != null) {
                            for (int i = 0; i < photosArray.length(); i++) {
                                JSONObject photoObj = photosArray.optJSONObject(i);
                                PhotosResponse.Photo photo = new PhotosResponse.Photo(
                                        photoObj.optInt("id", 0),
                                        photoObj.optString("photographer", ""),
                                        photoObj.optInt("photographer_id", 0),
                                        photoObj.optString("photographer_url", ""),
                                        photoObj.optInt("height", 0),
                                        photoObj.optInt("width", 0),
                                        photoObj.optString("url", ""),
                                        new PhotosResponse.Photo.Src(
                                                photoObj.optJSONObject("src") != null ? photoObj.optJSONObject("src").optString("original") : null
                                        )
                                );
                                photosList.add(photo);
                            }
                        }
                        photosAdapter.addPhotos(photosList);
                        Log.e("JSONResponse", "Success");

                    } catch (Exception e){
                        e.printStackTrace();
//                        binding.resulttext.setVisibility(View.VISIBLE);
//                        binding.resulttext.setText("Error parsing response.");
                        Log.e("JSONResponse", "Error parsing response: " + e.getMessage());
                    }
                },
                error -> {
                //    binding.resulttext.setVisibility(View.VISIBLE);
                    Log.e("Volley", "Error: " + error);
                   // binding.resulttext.setText("Error fetching data.");
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", API_KEY);
                return headers;
            }
        };
        jsonObjectRequest.setTag(this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(queue!=null){
            queue.cancelAll(this);
        }
    }


}