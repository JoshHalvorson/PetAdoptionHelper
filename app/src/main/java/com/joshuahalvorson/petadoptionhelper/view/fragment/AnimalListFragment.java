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
import com.joshuahalvorson.petadoptionhelper.animal.AnimalPetfinder;
import com.joshuahalvorson.petadoptionhelper.animal.AnimalsOverview;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
import com.joshuahalvorson.petadoptionhelper.animal.Pets;
import com.joshuahalvorson.petadoptionhelper.network.PetFinderApiViewModel;

import java.util.ArrayList;
import java.util.List;

public class AnimalListFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private PetFinderApiViewModel viewModel;

    private RecyclerView recyclerView;
    private PetListRecyclerViewAdapter adapter;

    private List<Pet> petList;

    public AnimalListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animal_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        petList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.pet_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new PetListRecyclerViewAdapter(petList);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(PetFinderApiViewModel.class);

        LiveData<AnimalsOverview> data = viewModel.getPetsInArea(98092, "json");
        data.observe(this, new Observer<AnimalsOverview>() {
            @Override
            public void onChanged(@Nullable AnimalsOverview animalsOverview) {
                if(animalsOverview != null){
                    AnimalPetfinder petfinder = animalsOverview.getPetfinder();
                    if(petfinder != null){
                        Pets pets = petfinder.getPets();
                        if(pets != null){
                            //have list of pets here
                            petList.clear();
                            petList.addAll(pets.getPet());
                            adapter.notifyDataSetChanged();
                            Log.i("petsList", petList.get(0).getName().get$t());
                        }
                    }

                }
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAnimalListFragmentInteraction(uri);
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
        void onAnimalListFragmentInteraction(Uri uri);
    }
}
