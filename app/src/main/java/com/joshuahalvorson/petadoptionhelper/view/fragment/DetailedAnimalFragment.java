package com.joshuahalvorson.petadoptionhelper.view.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
import com.joshuahalvorson.petadoptionhelper.animal.Photo;
import com.joshuahalvorson.petadoptionhelper.animal.StringPet;
import com.joshuahalvorson.petadoptionhelper.database.AnimalsDbDao;
import com.joshuahalvorson.petadoptionhelper.network.PetFinderApiViewModel;
import com.joshuahalvorson.petadoptionhelper.shelter.Shelter;
import com.joshuahalvorson.petadoptionhelper.shelter.ShelterPetfinder;
import com.joshuahalvorson.petadoptionhelper.shelter.SheltersOverview;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class DetailedAnimalFragment extends Fragment {
    private static final String TAG = "DetailedAnimalFragment";
    private static final String ARG_PARAM1 = "param1";
    private Pet pet;

    private OnFragmentInteractionListener mListener;

    private TextView petName, petAge, petSex, petSize, petBreeds, petDesc, petOptions,
            petContactPhone, petContactEmail, petContactAddress;
    private ImageView petImage;
    private FloatingActionButton favoriteButton;

    List<String> chars;

    DatabaseReference reference;

    private PetFinderApiViewModel viewModel;

    private String dist, shelterName;

    private ProgressBar loadingCircle;

    public DetailedAnimalFragment() {

    }


    public static DetailedAnimalFragment newInstance(Pet pet) {
        DetailedAnimalFragment fragment = new DetailedAnimalFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, pet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pet = (Pet) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detailed_animal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        petImage = view.findViewById(R.id.pet_image);
        petName = view.findViewById(R.id.pet_name);
        petAge = view.findViewById(R.id.pet_age);
        petSex = view.findViewById(R.id.pet_sex);
        petSize = view.findViewById(R.id.pet_size);
        petBreeds = view.findViewById(R.id.pet_breeds);
        petDesc = view.findViewById(R.id.pet_desc);
        petOptions = view.findViewById(R.id.pet_options);
        petContactPhone = view.findViewById(R.id.pet_contact_phone);
        petContactEmail = view.findViewById(R.id.pet_contact_email);
        petContactAddress = view.findViewById(R.id.pet_contact_address);
        favoriteButton = view.findViewById(R.id.favorite_button);

        viewModel = ViewModelProviders.of(getActivity()).get(PetFinderApiViewModel.class);

        loadingCircle = view.findViewById(R.id.pet_image_loading_circle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        reference = FirebaseDatabase.getInstance().getReference();

        favoriteButton.setImageDrawable(getResources().getDrawable(R.drawable.avd_anim_favorite_animation));

        chars = new ArrayList<>();
        chars.add("\\[");
        chars.add("\\]");
        chars.add("\\{");
        chars.add("\\}");
        chars.add("\\$t");
        chars.add("\\=");

        petName.setText(pet.getName().getAnimalName());
        petAge.setText("Age: " + pet.getAge().getAge());
        petSex.setText("Sex: " + pet.getSex().getAnimalSex());
        petSize.setText("Size: " + pet.getSize().getAnimalSize());
        petDesc.setText(pet.getDescription().getAnimalDescription());

        getShelterData();

        String phone = "Phone unknown";
        if (pet.getContact().getPhone().getPhone() != null){
            phone = pet.getContact().getPhone().getPhone();
        }

        String email = "Email unkown";
        if (pet.getContact().getEmail().getEmail() != null){
            email = pet.getContact().getEmail().getEmail();
        }

        String address = "Address unknown";
        if(pet.getContact().getAddress1().getAddress() != null){
            address = pet.getContact().getAddress1().getAddress();
        }else if(pet.getContact().getAddress2().getAddress() != null){
            address = pet.getContact().getAddress2().getAddress();
        }

        String city = "City unknown";
        if(pet.getContact().getCity().getCity() != null){
            city = pet.getContact().getCity().getCity();
        }

        String state = "State unknown";
        if(pet.getContact().getState().getState() != null){
            state = pet.getContact().getState().getState();
        }

        String zip = "Zip unknown";
        if(pet.getContact().getZip().getZip() != null){
            zip = pet.getContact().getZip().getZip();
        }

        petContactPhone.setText("Phone: " + phone);

        petContactEmail.setText("Email: " + email);

        petContactAddress.setText("Location: " + address + ", " + city + ", " + state + " " + zip);

        if(pet.getBreeds().getBreed() != null){
            petBreeds.setText(
                    "Breeds: " +
                            removeCharsFromString(pet.getBreeds().getBreed().toString(), chars));
        }else{
            petBreeds.setText(getString(R.string.breeds_default_text));
        }

        if(pet.getOptions().getOption() != null){
            petOptions.setText(
                    "Options: " +
                            removeCharsFromString(pet.getOptions().getOption().toString(), chars));
        }else{
            petOptions.setText(getString(R.string.options_default_text));
        }

        //can throw error
        List<Photo> photoList = pet.getMedia().getPhotos().getPhoto();
        Glide.with(getContext())
                .load(photoList.get(2).getImageUrl())
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target, DataSource dataSource,
                                                   boolean isFirstResource) {
                        loadingCircle.setVisibility(View.GONE);
                        return false;
                    }
                })
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_detailed_pet_image_placeholder))
                .into(petImage);

        final String finalPhone = phone;
        final String finalEmail = email;
        final String finalAddress = address;
        final String finalCity = city;
        final String finalState = state;
        final String finalZip = zip;

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteButton.setImageDrawable(getResources().getDrawable(R.drawable.avd_anim_favorite_animation));
                final Drawable drawable = favoriteButton.getDrawable();
                if(drawable instanceof Animatable){
                    ((Animatable) drawable).start();
                }
                AnimalsDbDao.createAnimalEntry(pet, dist, shelterName);
                if(FirebaseAuth.getInstance().getCurrentUser() != null){
                    StringPet stringPet = new StringPet(pet, dist, shelterName);

                    stringPet.setsContact("Phone: " + finalPhone + "/n" +

                                    "Email: " + finalEmail + "/n" +

                                    "Location: " + finalAddress + ", " + finalCity + ", " +
                                    finalState + " " + finalZip
                            );

                    stringPet.setsBreeds(removeCharsFromString(stringPet.getsBreeds(), chars));
                    stringPet.setsOptions(removeCharsFromString(stringPet.getsOptions(), chars));

                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    reference.child("users").child(userId).child("animals")
                            .child(stringPet.getsId())
                            .child("options").setValue(stringPet.getsOptions());
                    reference.child("users").child(userId).child("animals")
                            .child(stringPet.getsId())
                            .child("contact").setValue(stringPet.getsContact());
                    reference.child("users").child(userId).child("animals")
                            .child(stringPet.getsId())
                            .child("age").setValue(stringPet.getsAge());
                    reference.child("users").child(userId).child("animals")
                            .child(stringPet.getsId())
                            .child("size").setValue(stringPet.getsSize());
                    reference.child("users").child(userId).child("animals")
                            .child(stringPet.getsId())
                            .child("media").setValue(stringPet.getsMedia());
                    reference.child("users").child(userId).child("animals")
                            .child(stringPet.getsId())
                            .child("id").setValue(stringPet.getsId());
                    reference.child("users").child(userId).child("animals")
                            .child(stringPet.getsId())
                            .child("breeds").setValue(stringPet.getsBreeds());
                    reference.child("users").child(userId).child("animals")
                            .child(stringPet.getsId())
                            .child("name").setValue(stringPet.getsName());
                    reference.child("users").child(userId).child("animals")
                            .child(stringPet.getsId())
                            .child("sex").setValue(stringPet.getsSex());
                    reference.child("users").child(userId).child("animals")
                            .child(stringPet.getsId())
                            .child("description").setValue(stringPet.getsDescription());
                    reference.child("users").child(userId).child("animals")
                            .child(stringPet.getsId())
                            .child("last_update").setValue(stringPet.getsLastUpdate());
                    reference.child("users").child(userId).child("animals")
                            .child(stringPet.getsId())
                            .child("shelter_name").setValue(shelterName);
                    reference.child("users").child(userId).child("animals")
                            .child(stringPet.getsId())
                            .child("distance").setValue(dist);

                    Toast.makeText(getContext(), pet.getName().getAnimalName() +
                                    " added to your favorites!",
                            Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getContext(), pet.getName().getAnimalName() +
                                    " added to your favorites! (not synced online)",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getShelterData(){
        LiveData<SheltersOverview> shelterData =
                viewModel.getShelterData(pet.getShelterId().getShelterId(), "json");

        shelterData.observe(getViewLifecycleOwner(), new Observer<SheltersOverview>() {
            @Override
            public void onChanged(@Nullable SheltersOverview sheltersOverview) {
                double lat = 0;
                double lon = 0;
                if (sheltersOverview != null) {
                    ShelterPetfinder petfinder = sheltersOverview.getPetfinder();
                    if(petfinder !=  null){
                        Shelter shelter = petfinder.getShelter();
                        if(shelter != null){
                            lat = Double.parseDouble(
                                    sheltersOverview.getPetfinder().getShelter().getLatitude().getLatitude());
                            lon = Double.parseDouble(
                                    sheltersOverview.getPetfinder().getShelter().getLongitude().getLongitude());
                            double doubleDist = getDistance(AnimalListFragment.currentLat, AnimalListFragment.currentLon, lat, lon, "");
                            dist = Double.toString(doubleDist);
                            shelterName = sheltersOverview.getPetfinder().getShelter().getName().getName();
                        }else{
                            dist = "Contact shelter for information";
                            shelterName = "";
                        }
                    }
                }
            }
        });

        AnimalsDbDao.createAnimalHistoryEntry(pet, dist, shelterName);
    }

    private double getDistance(double lat1, double lon1, double lat2, double lon2, String unit){
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
                    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                            Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit == "K") {
                dist = dist * 1.609344;
            } else if (unit == "N") {
                dist = dist * 0.8684;
            }
            return new BigDecimal(dist).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
    }

    private String removeCharsFromString(String string, List<String> charactersToRemove){
        for(int i = 0; i < charactersToRemove.size(); i++){
            string = string.replaceAll(charactersToRemove.get(i), "");
        }
        return string;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDetailedAnimalFragmentInteraction(uri);
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
        void onDetailedAnimalFragmentInteraction(Uri uri);
    }
}
