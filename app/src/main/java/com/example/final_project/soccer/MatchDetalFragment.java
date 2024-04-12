package com.example.final_project.soccer;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.final_project.R;
import com.example.final_project.databinding.FragmentMatchDetalBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchDetalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchDetalFragment extends Fragment {

    FragmentMatchDetalBinding binding;

    SoccerActivity movieActivity;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    SoccerModel soccerModel = new SoccerModel();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String srcLink;

    private  String date;
    private String teamName1;
    private String teamName2;
    private String url;
    private String embed;
    private String title;

    public MatchDetalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MatchDetalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchDetalFragment newInstance(String param1, String param2) {
        MatchDetalFragment fragment = new MatchDetalFragment();
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
//            soccerModel = (SoccerModel) getArguments().getSerializable("model");
//            System.out.println("SoccerModel " + soccerModel.getEmbed());
            title = getArguments().getString("title");
            date = getArguments().getString("date");
            teamName1 = getArguments().getString("teamName1");
            teamName2 = getArguments().getString("teamName2");
            url = getArguments().getString("url");
            embed = getArguments().getString("embed");
            System.out.println("date " + date);
            System.out.println("teamName1 " + teamName1);
            System.out.println("teamName2 " + teamName2);
            System.out.println("url " + url);
            System.out.println("embed " + embed);


        //     Find the index of src attribute
            int srcStartIndex = embed.indexOf("src='") + 5;
            int srcEndIndex = embed.indexOf("'", srcStartIndex);

// Extract the link
            srcLink = embed.substring(srcStartIndex, srcEndIndex);
            System.out.println("Embed Link: " + srcLink);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMatchDetalBinding.inflate(getLayoutInflater());


        binding.tvDate.setText(date);
        binding.tvTeam1Name.setText(teamName1);
      //  binding.tvTeam1URL.setText(soccerModel.getSide1().getUrl());
        binding.tvTeam2Name.setText(teamName2);
    //    binding.tvTeam2URL.setText(soccerModel.getSide2().getUrl());

        soccerModel.setTitle(title);
        soccerModel.setDate(date);
        soccerModel.setSide1(new SoccerModel.Side());
        soccerModel.setSide2(new SoccerModel.Side());
        soccerModel.getSide1().setName(teamName1);
        soccerModel.getSide2().setName(teamName2);

        SharedPreferences prefs = requireContext().getSharedPreferences("url", MODE_PRIVATE);
        binding.btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(soccerModel);
            }
        });
        // Saving video URL to SharedPreferences when the user clicks to watch a video
        binding.btnVideoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("videoUrl", srcLink);
                editor.apply();
                System.out.println("Video Url: " + srcLink);

            }
        });



        binding.btnHighlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Checking if there is a saved URL in SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("url", MODE_PRIVATE);
        String savedUrl = prefs.getString("videoUrl", "");
        System.out.println("Saved Url: " + savedUrl);

        // If a URL is saved, launch the web browser immediately
        if (!savedUrl.isEmpty()) {
            // Launch web browser
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(savedUrl));
            startActivityForResult(browserIntent, 1); // Start activity for result
        }

    }

    // Override onActivityResult to clear saved URL
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences prefs = requireContext().getSharedPreferences("url", MODE_PRIVATE);
        if (requestCode == 1) {
            // Clear saved URL
            System.out.println("Requiest Code: "+requestCode);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();
        }
    }


    private void saveData(SoccerModel soccerModel) {
        /*
   Saves soccer match data to the database.
   Displays a toast message indicating whether the data was saved successfully or not.
   Navigates to the saved matches fragment if the data was saved successfully.
*/
        DatabaseHelper db = new DatabaseHelper(requireContext());
        boolean dataSaved = db.insertMatch(soccerModel);
        if(dataSaved){
            Toast.makeText(requireContext(),"Data Saved",Toast.LENGTH_SHORT).show();
            movieActivity.getNavController().navigate(R.id.savedMatchFragment);
        }else{
            Toast.makeText(requireContext(),"Data Not Saved",Toast.LENGTH_SHORT).show();
        }
    }
}