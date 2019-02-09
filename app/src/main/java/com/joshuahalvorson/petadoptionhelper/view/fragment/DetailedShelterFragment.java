package com.joshuahalvorson.petadoptionhelper.view.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.adapter.PetListRecyclerViewAdapter;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
import com.joshuahalvorson.petadoptionhelper.network.PetFinderApiViewModel;
import com.joshuahalvorson.petadoptionhelper.shelter.Shelter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DetailedShelterFragment extends Fragment {
    private AnimalListFragment.OnFragmentInteractionListener mListener;

    private static final String ARG_PARAM1 = "shelter";
    private Shelter shelter;

    private PetFinderApiViewModel viewModel;

    private List<Pet> petsList;

    private PetListRecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;

    private int pageOffset = 0;

    public DetailedShelterFragment() {

    }

    public static DetailedShelterFragment newInstance(Shelter shelter) {
        DetailedShelterFragment fragment = new DetailedShelterFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, shelter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            shelter = (Shelter) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detailed_shelter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        petsList = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.shelter_pets_list);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(
                        recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new PetListRecyclerViewAdapter(petsList, mListener, getActivity(), (AppCompatActivity)getActivity());

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(layoutManager.findLastCompletelyVisibleItemPosition() == petsList.size() - 1){
                    pageOffset += 25;
                    getPetsInShelter(shelter.getId().getId(), Integer.toString(pageOffset));
                }
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(PetFinderApiViewModel.class);

        getPetsInShelter(shelter.getId().getId(), Integer.toString(pageOffset));

        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(shelter.getName().getName());

    }

    private void getPetsInShelter(String id, String offset) {
        LiveData<List<Pet>> data = viewModel.getPetsInShelter(id, "json", offset);
        data.observe(this, new Observer<List<Pet>>() {
            @Override
            public void onChanged(@Nullable List<Pet> data) {
                if (data != null) {
                    //petsList.clear();
                    petsList.addAll(data);

                    Collections.sort(petsList, new Comparator<Pet>() {
                        public int compare(Pet o1, Pet o2) {
                            return o2.getLastUpdate().getLastUpdate().substring(0, 10)
                                    .compareTo
                                            (o1.getLastUpdate().getLastUpdate().substring(0, 10));
                        }
                    });

                    adapter.notifyDataSetChanged();

                    RecyclerView.SmoothScroller smoothScroller =
                            new LinearSmoothScroller(getContext()) {
                                @Override protected int getVerticalSnapPreference() {
                                    return LinearSmoothScroller.SNAP_TO_START;
                                }
                            };
                    smoothScroller.setTargetPosition(pageOffset);
                    layoutManager.startSmoothScroll(smoothScroller);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AnimalListFragment.OnFragmentInteractionListener) {
            mListener = (AnimalListFragment.OnFragmentInteractionListener) context;
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

}
