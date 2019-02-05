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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
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
import com.joshuahalvorson.petadoptionhelper.database.TaggedAnimalsDbDao;
import com.joshuahalvorson.petadoptionhelper.network.PetFinderApiViewModel;

import java.util.ArrayList;
import java.util.List;

public class TaggedAnimalsFragment extends Fragment {
    private AnimalListFragment.OnFragmentInteractionListener mListener;

    private PetListRecyclerViewAdapter adapter;

    private List<String> taggedPetsIdsList;

    private PetFinderApiViewModel viewModel;

    public TaggedAnimalsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tagged_animals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taggedPetsIdsList = new ArrayList<>();
        taggedPetsIdsList = TaggedAnimalsDbDao.readAllTaggedAnimals();

        final List<Pet> petList = new ArrayList<>();

        viewModel = ViewModelProviders.of(this).get(PetFinderApiViewModel.class);

        for(String id : taggedPetsIdsList){
            LiveData<Pet> data = viewModel.getAnimalData(id, "json");
            data.observe(this, new Observer<Pet>() {
                @Override
                public void onChanged(@Nullable Pet pet) {
                    petList.add(pet);
                    adapter.notifyDataSetChanged();
                }
            });
        }


        RecyclerView recyclerView = view.findViewById(R.id.tagged_animals_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(
                        recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new PetListRecyclerViewAdapter(petList, mListener);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}