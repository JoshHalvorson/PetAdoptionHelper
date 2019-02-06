package com.joshuahalvorson.petadoptionhelper.view.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.animal.StringPet;
import java.util.ArrayList;
import java.util.List;

public class DetailedStringAnimalFragment extends Fragment {
    private StringPet pet;

    private TextView petName, petAge, petSex, petSize, petBreeds, petDesc, petOptions, petContact;
    private ImageView petImage;


    public DetailedStringAnimalFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
            pet = (StringPet)this.getArguments().getSerializable("stringPet");
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
        view.findViewById(R.id.favorite_button).setVisibility(View.GONE);
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

        petName.setText(pet.getsName());
        petAge.setText("Age: " + pet.getsAge());
        petSex.setText("Sex: " + pet.getsSex());
        petSize.setText("Size: " + pet.getsSize());
        petDesc.setText(pet.getsDescription());
        petContact.setText("Contact info: " + pet.getsContact());

        if(pet.getsBreeds() != null){
            petBreeds.setText(
                    "Breeds: " +
                            removeCharsFromString(pet.getsBreeds(), chars));
        }else{
            petBreeds.setText(getString(R.string.breeds_default_text));
        }

        if(pet.getsOptions() != null){
            petOptions.setText(
                    "Options: " +
                            removeCharsFromString(pet.getsOptions(), chars));
        }else{
            petOptions.setText(getString(R.string.options_default_text));
        }

        String imageUrl = pet.getsMedia();
        Glide.with(getContext())
                .load(imageUrl)
                .into(petImage);

    }

    private String removeCharsFromString(String string, List<String> charactersToRemove){
        for(int i = 0; i < charactersToRemove.size(); i++){
            string = string.replaceAll(charactersToRemove.get(i), "");
        }
        return string;
    }

}
