package com.example.final_project.soccer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.final_project.R;
import com.example.final_project.databinding.FragmentSoccerBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SoccerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoccerFragment extends Fragment implements SoccerInterface {
    private String url ="https://www.scorebat.com/video-api/v1/";
    FragmentSoccerBinding binding;
    private static final String TAG = "SoccerFragment";

    SoccerAdapter soccerAdapter;
    private ArrayList<SoccerModel> soccerList = new ArrayList<>();
    SoccerActivity movieActivity;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private RequestQueue queue;
    private String mParam2;

    public SoccerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SoccerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SoccerFragment newInstance(String param1, String param2) {
        SoccerFragment fragment = new SoccerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieActivity = (SoccerActivity) getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSoccerBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        soccerAdapter = new SoccerAdapter(this);
        binding.rvList.setLayoutManager(new LinearLayoutManager(movieActivity));
        binding.rvList.setAdapter(soccerAdapter);
        soccerMatch();
        binding.btnSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieActivity.getNavController().navigate(R.id.savedMatchFragment);
            }
        });

    }

    private void soccerMatch() {
     /*
   Fetch soccer match details from the API endpoint.
   Process the JSON response to extract match data such as titles, dates, URLs, embed codes, team names, and videos.
   Populate a list of soccer models and update the UI accordingly.
*/
        queue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Volley", "response" + response);
                        binding.progressBar.setVisibility(View.GONE);
                        binding.btnSaved.setVisibility(View.VISIBLE);
                        Snackbar.make(binding.rvList,"Search",Snackbar.LENGTH_SHORT).show();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject matchObject = response.getJSONObject(i);
                                String title = matchObject.getString("title");
                                String date = matchObject.getString("date");
                                String embed = matchObject.getString("embed");
                                String url = matchObject.getString("url");



                                // Extract side1 and side2 details
                                JSONObject side1Object = matchObject.getJSONObject("side1");
                                String side1Name = side1Object.getString("name");
                                String side1Url = side1Object.getString("url");

                                JSONObject side2Object = matchObject.getJSONObject("side2");
                                String side2Name = side2Object.getString("name");
                                String side2Url = side2Object.getString("url");
                                JSONArray videosArray = matchObject.getJSONArray("videos");
                                List<SoccerModel.Video> videoList = new ArrayList<>();

                                for (int j = 0; j < videosArray.length(); j++) {
                                    JSONObject videoObject = videosArray.getJSONObject(j);
                                    String videoTitle = videoObject.getString("title");
                                    String videoEmbed = videoObject.getString("embed");
                                    // Handle video details as needed
                                    SoccerModel.Video video = new SoccerModel.Video(videoTitle, videoEmbed);
                                    videoList.add(video);
                                    System.out.println("VideoList :"+videoList.size());
                                }
//                                int srcStartIndex = embed.indexOf("src='")+5;
//                                int srcEndIndex = embed.indexOf("'", srcStartIndex);
//                                String srcLink = embed.substring(srcStartIndex, srcEndIndex);

                                Log.d("Match Details", "Embed"+ embed) ;
                                SoccerModel soccerModel = new SoccerModel();
                                soccerModel.setTitle(title);
                                soccerModel.setDate(date);
                                soccerModel.setEmbed(embed);
                                soccerModel.setUrl(url);
                                soccerModel.setSide1(new SoccerModel.Side());
                                soccerModel.setSide2(new SoccerModel.Side());
                                soccerModel.getSide1().setName(side1Name);
                                soccerModel.getSide1().setUrl(side1Url);
                                soccerModel.getSide2().setName(side2Name);
                                soccerModel.getSide2().setUrl(side2Url);
                                soccerModel.setVideos(videoList);
                            soccerList.add(soccerModel);
                            soccerAdapter.addSoccer(soccerList);
                            }
                            System.out.println("MovieList: " + soccerList.size());
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Log.e("Volley", "Error: " + error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization","Bearer "+ url);
                return headers;
            }
        };
        jsonObjectRequest.setTag(this);
        queue.add(jsonObjectRequest);
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (queue != null) {
          queue.cancelAll(this); // Cancel the API call if it's in progress
        }

    }

    @Override
    public void onDetailSoccer(SoccerModel soccerModel) {
        Bundle bundle = new Bundle();
        bundle.putString("title",soccerModel.getTitle());
        bundle.putString("date",soccerModel.getDate());
        bundle.putString("teamName1",soccerModel.getSide1().getName());
        bundle.putString("teamName2",soccerModel.getSide2().getName());
        bundle.putString("url",soccerModel.getUrl());
        bundle.putString("embed",soccerModel.getEmbed());
        movieActivity.getNavController().navigate(R.id.matchDetalFragment,bundle);
    }
}