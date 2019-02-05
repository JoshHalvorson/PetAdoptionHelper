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
import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
import com.joshuahalvorson.petadoptionhelper.animal.Photo;
import com.joshuahalvorson.petadoptionhelper.database.TaggedAnimalsDbDao;

import java.util.ArrayList;
import java.util.List;

public class DetailedAnimalFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private Pet pet;

    private OnFragmentInteractionListener mListener;

    private TextView petName, petAge, petSex, petSize, petBreeds, petDesc, petOptions, petContact;
    private ImageView petImage;
    private FloatingActionButton favoriteButton;

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
        petContact = view.findViewById(R.id.pet_contact);
        favoriteButton = view.findViewById(R.id.favorite_button);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<String> chars = new ArrayList<>();
        chars.add("\\[");
        chars.add("\\]");
        chars.add("\\{");
        chars.add("\\}");
        chars.add("\\$t");
        chars.add("\\=");

        petName.setText(pet.getName().get$t());
        petAge.setText("Age: " + pet.getAge().get$t());
        petSex.setText("Sex: " + pet.getSex().get$t());
        petSize.setText("Size: " + pet.getSize().get$t());
        petDesc.setText(pet.getDescription().get$t());
        petContact.setText("Contact info: " + pet.getContact().getPhone().get$t());

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

        List<Photo> photoList = pet.getMedia().getPhotos().getPhoto();
        Glide.with(getContext())
                .load(photoList.get(2).get$t())
                .into(petImage);

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaggedAnimalsDbDao.createAnimalEntry(pet);
                Toast.makeText(getContext(), pet.getName().get$t() + " add to your favorites!",
                        Toast.LENGTH_SHORT).show();
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
