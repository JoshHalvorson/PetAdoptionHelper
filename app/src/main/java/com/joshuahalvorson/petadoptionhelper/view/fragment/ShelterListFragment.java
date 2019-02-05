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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.joshuahalvorson.petadoptionhelper.R;
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

    private ShelterListRecyclerViewAdapter adapter;
    private PetFinderApiViewModel viewModel;
    private LinearLayoutManager layoutManager;

    private List<Shelter> shelterList;

    int pageOffset = 0;

    private ProgressBar progressCircle;

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

        progressCircle = view.findViewById(R.id.loading_circle);

        RecyclerView recyclerView = view.findViewById(R.id.shelter_list_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(
                        recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new ShelterListRecyclerViewAdapter(shelterList, mListener);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(layoutManager.findLastCompletelyVisibleItemPosition() == shelterList.size() - 1){
                    pageOffset += 25;
                    progressCircle.setVisibility(View.VISIBLE);
                    getShelterList(AnimalListFragment.zipcode, Integer.toString(pageOffset));
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(PetFinderApiViewModel.class);
        getShelterList(AnimalListFragment.zipcode, Integer.toString(pageOffset));
    }

    private void getShelterList(int zipcode, String offset) {
        LiveData<SheltersOverview> sheltersData =
                viewModel.getSheltersInArea(zipcode, "json", offset);
        sheltersData.observe(this, new Observer<SheltersOverview>() {
            @Override
            public void onChanged(@Nullable SheltersOverview sheltersOverview) {
                if(sheltersOverview != null){
                    ShelterPetfinder sheltersOverviewPetfinder = sheltersOverview.getPetfinder();
                    if(sheltersOverviewPetfinder != null){
                        Shelters shelters = sheltersOverviewPetfinder.getShelters();
                        if(shelters != null){
                            shelterList.addAll(shelters.getShelter());
                            adapter.notifyDataSetChanged();
                            progressCircle.setVisibility(View.GONE);

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

                }
            }
        });
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
        void onShelterListFragmentInteraction(Shelter shelter);
    }
}
