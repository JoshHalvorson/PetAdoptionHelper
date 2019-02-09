package com.joshuahalvorson.petadoptionhelper.view.fragment;


import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.animal.StringPet;
import com.joshuahalvorson.petadoptionhelper.database.AnimalsDbDao;

import java.util.ArrayList;
import java.util.List;

public class DetailedStringAnimalFragment extends Fragment {
    private StringPet pet;

    private TextView petName, petAge, petSex, petSize, petBreeds, petDesc, petOptions,
            petContactPhone, petContactEmail, petContactAddress;
    private ImageView petImage;

    private ProgressBar loadingCircle;

    private ImageButton mailButton, phoneButton, mapsButton, favoriteButton;

    private String email, address, phone, city, state, zip;

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
        petContactPhone = view.findViewById(R.id.pet_contact_phone);
        petContactEmail = view.findViewById(R.id.pet_contact_email);
        petContactAddress = view.findViewById(R.id.pet_contact_address);

        mailButton = view.findViewById(R.id.mail_button);
        phoneButton = view.findViewById(R.id.phone_button);
        mapsButton = view.findViewById(R.id.maps_button);

        if (getActivity().getSupportFragmentManager().findFragmentByTag("viewed_history")
                != null) {
            favoriteButton = view.findViewById(R.id.favorite_button);
            favoriteButton.setVisibility(View.VISIBLE);
        }else{
            view.findViewById(R.id.favorite_button).setVisibility(View.GONE);
        }

        loadingCircle = view.findViewById(R.id.pet_image_loading_circle);
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

        String[] contactString = pet.getsContact().split("\n");

        if (contactString.length >= 3){
            phone = contactString[0];
            email = contactString[1];
            address = contactString[2];

            String[] location = address.split(": ");
            String[] addressString = location[1].split(", ");
            String[] stateZip = addressString[2].split(" ");

            city = addressString[1];
            state = stateZip[0];
            zip = stateZip[1];

            petContactPhone.setText(phone);
            petContactEmail.setText(email);
            petContactAddress.setText(address);
        }else{
            contactString = pet.getsContact().split("/n");
            if (contactString.length >= 3){
                phone = contactString[0];
                email = contactString[1];
                address = contactString[2];

                String[] location = address.split(": ");
                String[] addressString = location[1].split(", ");
                String[] stateZip = addressString[2].split(" ");

                city = addressString[1];
                state = stateZip[0];
                zip = stateZip[1];

                petContactPhone.setText(phone);
                petContactEmail.setText(email);
                petContactAddress.setText(address);
            }
        }


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

        mailButton.setImageDrawable(getResources().getDrawable(R.drawable.avd_anim_mail_animation));
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Drawable drawable = mailButton.getDrawable();
                if(drawable instanceof Animatable){
                    ((Animatable) drawable).start();
                }
                sendEmailToShelter();
            }
        });

        phoneButton.setImageDrawable(getResources().getDrawable(R.drawable.avd_anim_phone_animation));
        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Drawable drawable = phoneButton.getDrawable();
                if(drawable instanceof Animatable){
                    ((Animatable) drawable).start();
                }
                callShelter();
            }
        });

        mapsButton.setImageDrawable(getResources().getDrawable(R.drawable.avd_anim_maps_animation));
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Drawable drawable = mapsButton.getDrawable();
                if(drawable instanceof Animatable){
                    ((Animatable) drawable).start();
                }
                openMapsToShelter();
            }
        });

        String imageUrl = pet.getsMedia();
        Glide.with(getContext())
                .load(imageUrl)
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
                        .placeholder(R.drawable.ic_detailed_pet_image_placeholder)
                        .centerInside())
                .into(petImage);

        if(favoriteButton != null){
            if(favoriteButton.getVisibility() == View.VISIBLE){
                favoriteButton.setImageDrawable(getResources().getDrawable(R.drawable.avd_anim_favorite_animation));
                favoriteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        favoriteButton.setImageDrawable(getResources().getDrawable(R.drawable.avd_anim_favorite_animation));
                        final Drawable drawable = favoriteButton.getDrawable();
                        if(drawable instanceof Animatable){
                            ((Animatable) drawable).start();
                        }

                        if(AnimalsDbDao.checkAnimalExists("animals", "animal_id", pet.getsId())) {
                            AnimalsDbDao.createAnimalEntryFromStringPet(pet);
                            Toast.makeText(getContext(), pet.getsName() +
                                    " added to your favorites!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), pet.getsName() +
                                    " is already added to your favorites!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    private void sendEmailToShelter() {
        String[] to = {email};
        String[] cc = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "About " + pet.getsName());
        startActivity(emailIntent);
    }

    private void callShelter() {
        if(!petContactPhone.getText().toString().contains("Phone unknown")){
            String[] phoneList = phone.split(":");
            Uri uri = Uri.parse("tel:" + phoneList[1]);
            Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
            try {
                startActivity(callIntent);
            } catch (SecurityException e) {
                Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getContext(), "No phone found", Toast.LENGTH_SHORT).show();
        }

    }

    private void openMapsToShelter(){
        if(!petContactAddress.getText().toString().contains("Address unknown")){
            Uri locUri = Uri.parse("geo:0,0?q=1600" + address);
            Intent setDestinationIntent = new Intent(Intent.ACTION_VIEW, locUri);
            setDestinationIntent.setPackage("com.google.android.apps.maps");
            startActivity(setDestinationIntent);
        }else{
            Toast.makeText(getContext(), "No address found", Toast.LENGTH_SHORT).show();
        }

    }

    private String removeCharsFromString(String string, List<String> charactersToRemove){
        for(int i = 0; i < charactersToRemove.size(); i++){
            string = string.replaceAll(charactersToRemove.get(i), "");
        }
        return string;
    }

}
