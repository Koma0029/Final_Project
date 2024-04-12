package com.example.final_project.ticket;

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
import com.example.final_project.databinding.FragmentSavedTicketFrafmentBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedTicketFrafment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedTicketFrafment extends Fragment implements SaveTicketAdapter.DeleteInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    List<Model.EventDetails> detailsList = new ArrayList<>();

    FragmentSavedTicketFrafmentBinding binding;

    SaveTicketAdapter adapter;
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SavedTicketFrafment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavedTicketFrafment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavedTicketFrafment newInstance(String param1, String param2) {
        SavedTicketFrafment fragment = new SavedTicketFrafment();
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
        // Inflate the layout for this fragment
        binding = FragmentSavedTicketFrafmentBinding.inflate(getLayoutInflater());
        adapter = new SaveTicketAdapter(requireContext(),this);
        binding.rvList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvList.setAdapter(adapter);
        detailsList = getList();
        adapter.updateList(detailsList);
        return binding.getRoot();
    }

    private List<Model.EventDetails> getList() {
        /* Retrieves a list of all event details from the database.
         * - Create a new instance of DatabaseTicketMaster.
         * - Call the getAllEvents method to fetch all events.
         * - Return the list of event details.
         */

        DatabaseTicketMaster db = new DatabaseTicketMaster(requireContext());
        return db.getAllEvents();

    }

    @Override
    public void onDeleteButtonClicked(int position) {
        /* Handles delete button click:
         * Confirm deletion with an AlertDialog.
         * If confirmed, delete event from database.
         * Update list and notify adapter.
         */
        DatabaseTicketMaster  db = new DatabaseTicketMaster(requireContext());
        new AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.delete_alert))
                .setMessage(getString(R.string.dlete_data))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String title = detailsList.get(position).getUrl();
                        boolean isDeleted = db.deletEvent(title);
                        if (isDeleted) {
                            detailsList.clear();
                            detailsList =
                                    getList();
                            adapter.updateList(detailsList);

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
}