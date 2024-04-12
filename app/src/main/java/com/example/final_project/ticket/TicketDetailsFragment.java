package com.example.final_project.ticket;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.final_project.databinding.FragmentTicketDetailsBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TicketDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TicketDetailsFragment extends Fragment {
    FragmentTicketDetailsBinding binding;

    int id ;
    double min ;
    double max;
    String currency ;
    String type;

    String url;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TicketDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TicketDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TicketDetailsFragment newInstance(String param1, String param2) {
        TicketDetailsFragment fragment = new TicketDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTicketDetailsBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            /* Extracts data from bundle: 1. id: Integer 2. min: Integer 3. max: Integer 4. currency: String 5. type: String 6. url: String */
            id = bundle.getInt("id");
            min = bundle.getDouble("min");
            max = bundle.getDouble("max");
            currency = bundle.getString("currency");
            type = bundle.getString("type");
            url= bundle.getString("url");
        }
        Log.d("values", "onCreateViewTicket:  "+id +min  +max +currency +type);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.currency.setText(currency);
        binding.min.setText(String.valueOf(min));
        binding.max.setText(String.valueOf(max));
        binding.type.setText(type);
        binding.url.setText(url);
    }
}