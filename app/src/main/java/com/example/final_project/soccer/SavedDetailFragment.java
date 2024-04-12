package com.example.final_project.soccer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.final_project.databinding.FragmentSavedDetailBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedDetailFragment extends Fragment {

    FragmentSavedDetailBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    SoccerModel soccerModel;

    private  String date;
    private String teamName1;
    private String teamName2;
    private String url;
    private String embed;
    private String title;

    public SavedDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavedDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavedDetailFragment newInstance(String param1, String param2) {
        SavedDetailFragment fragment = new SavedDetailFragment();
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
      //    soccerModel = (SoccerModel) getArguments().getSerializable("model");
            title = getArguments().getString("title");
            date = getArguments().getString("date");
            teamName1 = getArguments().getString("teamName1");
            teamName2 = getArguments().getString("teamName2");

            System.out.println("date " + date);
            System.out.println("teamName1 " + teamName1);
            System.out.println("teamName2 " + teamName2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSavedDetailBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment
        binding.tvDate.setText(date);
        binding.tvTeam1Name.setText(teamName1);
        binding.tvTeam2Name.setText(teamName2);
        return binding.getRoot();
    }
}