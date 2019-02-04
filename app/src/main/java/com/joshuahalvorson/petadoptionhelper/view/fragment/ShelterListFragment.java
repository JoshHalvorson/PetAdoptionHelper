package com.joshuahalvorson.petadoptionhelper.view.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.adapter.PetListRecyclerViewAdapter;
import com.joshuahalvorson.petadoptionhelper.adapter.ShelterListRecyclerViewAdapter;
import com.joshuahalvorson.petadoptionhelper.network.PetFinderApiViewModel;
import com.joshuahalvorson.petadoptionhelper.shelter.Shelter;
import com.joshuahalvorson.petadoptionhelper.shelter.ShelterPetfinder;
import com.joshuahalvorson.petadoptionhelper.shelter.Shelters;
import com.joshuahalvorson.petadoptionhelper.shelter.SheltersOverview;

import java.util.ArrayList;
import java.util.List;

public class ShelterListFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private PetFinderApiViewModel viewModel;

    private RecyclerView recyclerView;
    private ShelterListRecyclerViewAdapter adapter;

    private List<Shelter> shelterList;

    public ShelterListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shelter_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shelterList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.shelter_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new ShelterListRecyclerViewAdapter(shelterList);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(PetFinderApiViewModel.class);

        LiveData<SheltersOverview> sheltersData = viewModel.getSheltersInArea(98092, "json");
        sheltersData.observe(this, new Observer<SheltersOverview>() {
            @Override
            public void onChanged(@Nullable SheltersOverview sheltersOverview) {
                if(sheltersOverview != null){
                    ShelterPetfinder sheltersOverviewPetfinder = sheltersOverview.getPetfinder();
                    if(sheltersOverviewPetfinder != null){
                        Shelters shelters = sheltersOverviewPetfinder.getShelters();
                        if(shelters != null){
                            //have list of pets here
                            shelterList.clear();
                            shelterList.addAll(shelters.getShelter());
                            adapter.notifyDataSetChanged();
                            Log.i("shelterList", shelterList.get(0).getName().get$t());
                        }
                    }

                }
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onShelterListFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onShelterListFragmentInteraction(Uri uri);
    }
}
