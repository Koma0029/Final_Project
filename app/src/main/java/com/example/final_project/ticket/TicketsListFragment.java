package com.example.final_project.ticket;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.final_project.R;
import com.example.final_project.databinding.FragmentTicketsListBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TicketsListFragment extends Fragment {

    private TicketAdapter ticketAdapter;
    private TicketActivity ticketActivity;
    private ArrayList<Model.Event> ticketList = new ArrayList<>();

    private   RequestQueue queue;

    private ArrayList<Model.Image> imglist = new ArrayList<>();
    private ArrayList<Model.PriceRange> priceRangeList = new ArrayList<>();
    private String Api_URL = "https://app.ticketmaster.com/discovery/v2/events.json?apikey=GbewhhuG0O1SbjNaS0QtuHPcGCqSRDju";
    FragmentTicketsListBinding binding;
    public TicketsListFragment() {
        // Required empty public constructor
    }

    public static TicketsListFragment newInstance(String param1, String param2) {
        TicketsListFragment fragment = new TicketsListFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ticketActivity = (TicketActivity) requireActivity();
        // Inflate the layout for this fragment
        binding = FragmentTicketsListBinding.inflate(inflater, container, false);
        DatabaseTicketMaster dbHelper = new DatabaseTicketMaster(getActivity());

        binding.btnSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ticketActivity.getNavController().navigate(R.id.savedTicketFrafment);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = ticketActivity.getSharedPreferences(
                getString(R.string.app_name),
                Context.MODE_PRIVATE
        );
        String searchedcity = sharedPreferences.getString("city", "");
        binding.edttext.setText(searchedcity);
        System.out.println("SearchedEvent: "+searchedcity);
        if(!searchedcity.isEmpty()) {
            searchEvents(searchedcity, 100);
        }else {
            Toast.makeText(requireContext(),getResources().getString(R.string.search_city),Toast.LENGTH_SHORT).show();
        }

        ticketAdapter = new TicketAdapter(requireContext(), new TicketAdapter.ViewHandler() {
            /*
   Creates a new instance of TicketAdapter with a ViewHandler implementation for handling item clicks,
   including navigation to TicketDetailsFragment or saving events to the database.
*/
            @Override
            public void viewHandler(int position, TicketAdapter.DialogClickType clickType) {
                switch (clickType) {
                    case OnClick:

                        String url = ticketList.get(position).getUrl();
//                        String date= ticketList.get(position).getDates().getStart().getLocalDate();
                        double min = ticketList.get(position).getPriceRanges().get(position).getMin();
                        double max = ticketList.get(position).getPriceRanges().get(position).getMax();
                        String currency = ticketList.get(position).getPriceRanges().get(position).getCurrency();
                        String type = ticketList.get(position).getPriceRanges().get(position).getType();
                        Bundle bundle = new Bundle();
                        bundle.putString("url", url);
                        bundle.putDouble("min", min);
                        bundle.putDouble("max", max);
                        bundle.putString("currency", currency);
                        bundle.putString("type", type);
                        ticketActivity.getNavController().navigate(R.id.ticketDetailsFragment, bundle);
                        break;
                    case Save:

//                        String dateS= ticketList.get(position).getDates().getStart().getLocalDate();
                        String urlS = ticketList.get(position).getUrl();
                        double minS = ticketList.get(position).getPriceRanges().get(position).getMin();
                        double maxS = ticketList.get(position).getPriceRanges().get(position).getMax();
                        String currencyS = ticketList.get(position).getPriceRanges().get(position).getCurrency();
                        String typeS = ticketList.get(position).getPriceRanges().get(position).getType();
                        String event = String.valueOf(ticketList.get(position));

                        DatabaseTicketMaster dbHelper = new DatabaseTicketMaster(getActivity());
                        boolean isSaved = dbHelper.inserEvent(currencyS,typeS,minS,maxS,urlS);

                        if (isSaved) {
                            Toast.makeText(getActivity(), "saved successfully", Toast.LENGTH_SHORT).show();
                          //  ticketActivity.getNavController().navigate(R.id.savedTicketFrafment);

                            Log.d("eventSaved", "event saved successfully: " + event);
//                            ticketActivity.getNavController().navigate(R.id.savedImageFragment);

                        } else {
                            Toast.makeText(getActivity(), "Failed to save event", Toast.LENGTH_SHORT).show();

                            Log.e("eventSaved", "Failed to save event: " + event);
                        }

                        break;
                }
            }

            @Override
            public void imgView(int position, ImageView imageView) {
                // Handle image view
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(ticketActivity));
        binding.recyclerView.setAdapter(ticketAdapter);

        binding.btn.setOnClickListener(v -> {
            String city = binding.edttext.getText().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("citysearched", true);
            editor.putString("city", city);
            editor.apply();
            searchEvents(city, 100);
        });
    }

    private void searchEvents(String city, int radius) {
        /*
   Clears the ticket list, constructs the API URL using the provided city and radius parameters,
   makes a Volley request to fetch event data, and handles the response and error cases.
*/
        ticketList.clear();
        System.out.println("Api Hit: "+city+ " " +radius);
        Log.e("responseparam", " city " + city + ", radius " + radius);
        String url = Api_URL + "&city=" + city + "&radius=" + radius;
        Log.e("responseparam", " url " + url);
         queue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject embeddedObj = response.optJSONObject("_embedded");
                            JSONArray eventsArray = embeddedObj.optJSONArray("events");
                            ticketList = new ArrayList<>();
                            if (eventsArray != null) {
                                for (int i = 0; i < eventsArray.length(); i++) {
                                    JSONObject eventObj = eventsArray.optJSONObject(i);
                                    JSONArray priceRange = eventObj.optJSONArray("priceRanges");
                                    if (priceRange != null) {
                                        for (int j = 0; j < priceRange.length(); j++) {
                                            JSONObject priceObj = priceRange.optJSONObject(j);
                                            Model.PriceRange price = new Model.PriceRange(
                                                    priceObj.optDouble("min", 0.0),
                                                    priceObj.optDouble("max", 0.0),
                                                    priceObj.optString("type", ""),
                                                    priceObj.optString("currency", "")
                                            );
                                            priceRangeList.add(price);

                                        }
                                    }
                                    JSONArray images = eventObj.optJSONArray("images");
                                    if (images != null) {
                                        for (int j = 0; j < images.length(); j++) {
                                            JSONObject imageObj = images.optJSONObject(j);
                                            Model.Image image = new Model.Image(
                                                    imageObj.optString("url", "")
                                            );
                                            imglist.add(image);
                                        }
                                    }

                                    Model.Start startdate = new Model.Start(
                                            eventObj.optJSONObject("dates") != null ?
                                                    eventObj.optJSONObject("dates").optJSONObject("start").optString("localDate", "") :
                                                    ""
                                    );
                                    Model.Event event = new Model.Event(
                                            eventObj.optString("name", ""),
                                            eventObj.optString("id", ""),
                                            eventObj.optString("url", ""),
                                            imglist,
                                            new Model.Dates(startdate),
                                            priceRangeList
                                    );
                                    ticketList.add(event);
                                    Log.e("ticketlist", "searchEvents: " + ticketList);
                                }
                            }
                            ticketAdapter.AddEvents(ticketList);
                            ticketAdapter.notifyDataSetChanged();
                            Log.e("JSONResponse", "searchEvents: Success " + response);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("JSONResponse", "Error parsing response: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error: " + error);
                    }
                }
        );
        jsonObjectRequest.setTag(this);
        queue.add(jsonObjectRequest);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (queue != null) {
            queue.cancelAll(this); // Cancel the API call if it's in progress
        }

    }
}