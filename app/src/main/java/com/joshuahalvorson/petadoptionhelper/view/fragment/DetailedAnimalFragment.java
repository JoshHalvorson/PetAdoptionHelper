package com.joshuahalvorson.petadoptionhelper.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
import com.joshuahalvorson.petadoptionhelper.animal.Photo;
import com.joshuahalvorson.petadoptionhelper.animal.StringPet;
import com.joshuahalvorson.petadoptionhelper.database.AnimalsDbDao;
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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        reference = FirebaseDatabase.getInstance().getReference();

        AnimalsDbDao.createAnimalHistoryEntry(pet);

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
                AnimalsDbDao.createAnimalEntry(pet);
                if(FirebaseAuth.getInstance().getCurrentUser() != null){
                    StringPet stringPet = new StringPet(pet);

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
