package com.joshuahalvorson.petadoptionhelper.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.adapter.PetListRecyclerViewAdapter;
import com.joshuahalvorson.petadoptionhelper.animal.AnimalPetfinder;
import com.joshuahalvorson.petadoptionhelper.animal.AnimalsOverview;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
import com.joshuahalvorson.petadoptionhelper.animal.Pets;
import com.joshuahalvorson.petadoptionhelper.breed.Breed;
import com.joshuahalvorson.petadoptionhelper.breed.BreedsOverview;
import com.joshuahalvorson.petadoptionhelper.network.PetFinderApiViewModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AnimalListFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private PetListRecyclerViewAdapter adapter;
    private PetFinderApiViewModel viewModel;
    private LinearLayoutManager layoutManager;

    private List<Pet> petList;

    public static int pageOffset = 0;

    private ProgressBar progressCircle;

    private FloatingActionButton filterList;

    double currentLat, currentLon;

    public static int zipcode;

    public static String filterAnimal, filterBreed, filterSize, filterSex, filterAge;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private CardView filterView;
    private CheckBox animalMale, animalFemale;
    private Button applyFilter;
    private Spinner animalTypeSpinner, animalSizeSpinner, animalAgeSpinner, animalBreedsSpinner;

    private List<String> animalTypeSpinnerArray, animalSizeSpinnerArray, animalAgeSpinnerArray;


    public AnimalListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animal_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            getLocation();
        }

        petList = new ArrayList<>();

        progressCircle = view.findViewById(R.id.loading_circle);

        RecyclerView recyclerView = view.findViewById(R.id.pet_list_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(
                        recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new PetListRecyclerViewAdapter(petList, mListener);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(layoutManager.findLastCompletelyVisibleItemPosition() == petList.size() - 1){
                    pageOffset += 25;
                    progressCircle.setVisibility(View.VISIBLE);
                    getPetList(zipcode, Integer.toString(pageOffset),
                            filterAnimal, filterBreed, filterSize, filterSex, filterAge);
                }
            }
        });

        filterList = view.findViewById(R.id.filter_button);

        filterAnimal = "";
        filterBreed = "";
        filterSize = "";
        filterSex = "";
        filterAge = "";

        filterView = view.findViewById(R.id.filter_options_view);
        filterView.setVisibility(View.GONE);
        animalFemale = view.findViewById(R.id.animal_female);
        animalMale = view.findViewById(R.id.animal_male);
        applyFilter = view.findViewById(R.id.apply_filter_button);
        animalTypeSpinner = view.findViewById(R.id.animal_type);
        animalSizeSpinner = view.findViewById(R.id.animal_size);
        animalAgeSpinner = view.findViewById(R.id.animal_age);
        animalBreedsSpinner = view.findViewById(R.id.animal_breeds);

        animalTypeSpinnerArray = new ArrayList<>();
        animalTypeSpinnerArray.add("All");
        animalTypeSpinnerArray.add("bird");
        animalTypeSpinnerArray.add("cat");
        animalTypeSpinnerArray.add("dog");
        animalTypeSpinnerArray.add("horse");
        animalTypeSpinnerArray.add("reptile");
        animalTypeSpinnerArray.add("smallfurry");

        animalSizeSpinnerArray = new ArrayList<>();
        animalSizeSpinnerArray.add("All");
        animalSizeSpinnerArray.add("small");
        animalSizeSpinnerArray.add("medium");
        animalSizeSpinnerArray.add("large");

        animalAgeSpinnerArray = new ArrayList<>();
        animalAgeSpinnerArray.add("All");
        animalAgeSpinnerArray.add("Baby");
        animalAgeSpinnerArray.add("Young");
        animalAgeSpinnerArray.add("Adult");
        animalAgeSpinnerArray.add("Senior");

        setSpinnerAdapter(animalTypeSpinner, animalTypeSpinnerArray);
        setSpinnerAdapter(animalSizeSpinner, animalSizeSpinnerArray);
        setSpinnerAdapter(animalAgeSpinner, animalAgeSpinnerArray);


    }

    private void setSpinnerAdapter(Spinner spinner, List<String> spinnerList){
        List<String> spinnerArray = new ArrayList<>(spinnerList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(PetFinderApiViewModel.class);

        filterList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterView.getVisibility() == View.VISIBLE){
                    filterView.setVisibility(View.GONE);
                }else{
                    filterView.setVisibility(View.VISIBLE);
                }
            }
        });

        animalTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!animalTypeSpinnerArray.get(position).equals("All")){
                    LiveData<BreedsOverview> data = viewModel.getBreedsForAnimal(
                            "json", animalTypeSpinnerArray.get(position));
                    data.observe(getActivity(), new Observer<BreedsOverview>() {
                        @Override
                        public void onChanged(@Nullable BreedsOverview breedsOverview) {
                            List<Breed> breeds =
                                    breedsOverview.getPetfinder().getBreeds().getBreed();
                            List<String> stringBreeds = new ArrayList<>();
                            for(Breed breed : breeds){
                                stringBreeds.add(breed.get$t());
                            }
                            setSpinnerAdapter(animalBreedsSpinner, stringBreeds);
                        }
                    });
                    animalBreedsSpinner.setVisibility(View.VISIBLE);
                }else{
                    animalBreedsSpinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String animal, breed = "", size = "", sex = "", age = "";

                if(animalMale.isChecked()){
                    sex = "M";
                }else if(animalFemale.isChecked()){
                    sex = "F";
                }else if(animalMale.isChecked() && animalFemale.isChecked()){
                    sex = "";
                }

                animal = animalTypeSpinner.getSelectedItem().toString();
                if(animal.equals("All")){
                    animal = "";
                }

                if(animalSizeSpinner.getSelectedItem().toString().equals("All")){
                    size = "";
                }else if(animalSizeSpinner.getSelectedItem().toString().equals("small")){
                    size = "S";
                }else if(animalSizeSpinner.getSelectedItem().toString().equals("medium")){
                    size = "M";
                }else if(animalSizeSpinner.getSelectedItem().toString().equals("large")){
                    size = "L";
                }

                age = animalAgeSpinner.getSelectedItem().toString();
                if(age.equals("All")){
                    age = "";
                }

                if(animalBreedsSpinner.getSelectedItem() != null){
                    breed = animalBreedsSpinner.getSelectedItem().toString();
                }

                if (animalBreedsSpinner.getVisibility() == View.GONE){
                    breed = "";
                }

                filterPetList(zipcode, "", animal, breed, size, sex, age);
                filterView.setVisibility(View.GONE);
            }
        });

    }

    public void filterPetList(int zipcode, String offset,
                              String animal, String breed, String size, String sex, String age){
        petList.clear();
        filterAnimal = animal;
        filterBreed = breed;
        filterSize = size;
        filterSex = sex;
        filterAge = age;
        getPetList(zipcode, offset, animal, breed, size, sex, age);

    }

    public void getPetList(int zipcode, String offset,
                            String animal, String breed, String size, String sex, String age) {
        LiveData<AnimalsOverview> data = viewModel.getPetsInArea(zipcode, "json", offset,
                animal, breed, size, sex, age);
        data.observe(this, new Observer<AnimalsOverview>() {
            @Override
            public void onChanged(@Nullable AnimalsOverview animalsOverview) {
                if(animalsOverview != null){
                    AnimalPetfinder petfinder = animalsOverview.getPetfinder();
                    if(petfinder != null){
                        Pets pets = petfinder.getPets();
                        if(pets != null){
                            petList.addAll(pets.getPet());
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


    private void getLocation() {
        if (ActivityCompat
                .checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat
                .checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            return;

        }
        fusedLocationProviderClient
                .getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        currentLat = location.getLatitude();
                        currentLon = location.getLongitude();
                        zipcode = Integer.parseInt(getZipcode(currentLat, currentLon));
                        petList.clear();
                        getPetList(zipcode, Integer.toString(pageOffset),
                                filterAnimal, filterBreed, filterSize, filterSex, filterAge);
                    }
                });
    }

    private String getZipcode(double lat, double lon){
        List<Address> addresses = new ArrayList<>();
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses.get(0).getPostalCode();
    }

    public interface OnFragmentInteractionListener {
        void onAnimalListFragmentInteraction(Pet item);
    }
}
