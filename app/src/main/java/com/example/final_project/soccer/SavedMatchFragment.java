package com.example.final_project.soccer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.final_project.R;
import com.example.final_project.databinding.FragmentSavedMatchBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedMatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedMatchFragment extends Fragment implements SavedMatchAdapter.DeleteInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    FragmentSavedMatchBinding binding;
    SavedMatchAdapter adapter;
    SoccerActivity movieActivity;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<SoccerModel> soccerList = new ArrayList<>();

    public SavedMatchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavedMatchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavedMatchFragment newInstance(String param1, String param2) {
        SavedMatchFragment fragment = new SavedMatchFragment();
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
        binding = FragmentSavedMatchBinding.inflate(getLayoutInflater());
        soccerList = getDataFromDatabase();

        adapter = new SavedMatchAdapter(requireContext(),this);
        binding.rvList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvList.setAdapter(adapter);
        adapter.updateList(soccerList);
        System.out.println("GetAllMAtches: "+soccerList);
        return binding.getRoot();
    }

    private List<SoccerModel> getDataFromDatabase() {
/*
   Retrieves all soccer matches data from the database.
*/        DatabaseHelper db = new DatabaseHelper(requireContext());
        return db.getAllMatches();
    }

    @Override
    public void onDeleteButtonClicked(int position) {
        DatabaseHelper  db = new DatabaseHelper(requireContext());
        new AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.delete_alert))
                .setMessage(getString(R.string.dlete_data))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String title = soccerList.get(position).getTitle();
                        boolean isDeleted = db.deleteMatch(title);
                        if (isDeleted) {
                            soccerList.clear();
                            soccerList =
                                    getDataFromDatabase();
                            adapter.updateList(soccerList);

                            Toast.makeText(requireContext(),getString(R.string.Data_Deleted), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(requireContext(),getString(R.string.data_not_delete), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDetailButton(SoccerModel soccerModel) {
        /*
   Navigates to the saved detail fragment with the soccer match details.
   @param soccerModel The SoccerModel object containing the match details.
*/
        Bundle bundle = new Bundle();
        System.out.println("dateMatch" + soccerModel.getDate());
        bundle.putString("title",soccerModel.getTitle());
        bundle.putString("date",soccerModel.getDate());
        bundle.putString("teamName1",soccerModel.getSide1().getName());
        bundle.putString("teamName2",soccerModel.getSide2().getName());
        movieActivity.getNavController().navigate(R.id.savedDetailFragment,bundle);

    }
}