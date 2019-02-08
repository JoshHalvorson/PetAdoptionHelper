package com.joshuahalvorson.petadoptionhelper.adapter;


import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
import com.joshuahalvorson.petadoptionhelper.animal.Photo;
import com.joshuahalvorson.petadoptionhelper.animal.Photos;
import com.joshuahalvorson.petadoptionhelper.network.PetFinderApiViewModel;
import com.joshuahalvorson.petadoptionhelper.shelter.Shelter;
import com.joshuahalvorson.petadoptionhelper.shelter.ShelterPetfinder;
import com.joshuahalvorson.petadoptionhelper.shelter.SheltersOverview;
import com.joshuahalvorson.petadoptionhelper.view.MainActivity;
import com.joshuahalvorson.petadoptionhelper.view.fragment.AnimalListFragment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


public class PetListRecyclerViewAdapter extends RecyclerView.Adapter<PetListRecyclerViewAdapter.ViewHolder> {

    private final List<Pet> petList;

    private AnimalListFragment.OnFragmentInteractionListener listener;

    private PetFinderApiViewModel viewModel;

    FragmentActivity activity;
    AppCompatActivity appCompatActivity;

    public PetListRecyclerViewAdapter(List<Pet> petList,
                                      AnimalListFragment.OnFragmentInteractionListener listener,
                                      FragmentActivity activity,
                                      AppCompatActivity appCompatActivity) {
        this.petList = petList;
        this.listener = listener;
        this.activity = activity;
        this.appCompatActivity = appCompatActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pet_list_element_layout, viewGroup, false);
        viewModel = ViewModelProviders.of(activity).get(PetFinderApiViewModel.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final Pet pet = petList.get(i);
        viewHolder.petName.setText(pet.getName().getAnimalName());

        List<Photo> photoList;
        Photos photos= pet.getMedia().getPhotos();
        if(photos != null){
            photoList = photos.getPhoto();

            Glide.with(viewHolder.petImage.getContext())
                    .load(photoList.get(1).getImageUrl())
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
                            viewHolder.loadingCircle.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.petImage);
        }else{
            Glide.with(viewHolder.petImage.getContext())
                    .load(R.drawable.ic_broken_image_black_24dp)
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
                            viewHolder.loadingCircle.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(viewHolder.petImage);
        }


        String desc = pet.getDescription().getAnimalDescription();
        if(desc != null){
            if(desc.length() > 150){
                desc = desc.substring(0, 150);
                desc += "...";
                viewHolder.petDesc.setText(desc);
            }
        }else{
            desc = "No description provided.";
            viewHolder.petDesc.setText(desc);
        }

        String lastUpdated = pet.getLastUpdate().getLastUpdate().substring(0, 10);
        if(lastUpdated != null){
            viewHolder.lastUpdated.setText("Last updated: " + lastUpdated);
        }

        LiveData<SheltersOverview> shelterData =
                viewModel.getShelterData(pet.getShelterId().getShelterId(), "json");

        shelterData.observe(appCompatActivity, new Observer<SheltersOverview>() {
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
                            double dist = getDistance(AnimalListFragment.currentLat, AnimalListFragment.currentLon, lat, lon, "");

                            viewHolder.distance.setText(Double.toString(dist) + " Miles");
                            viewHolder.shelterName.setText(
                                    "Shelter: " +
                                            sheltersOverview.getPetfinder().getShelter().getName().getName());
                        }else{
                            viewHolder.distance.setText("Contact shelter for information");
                            viewHolder.shelterName.setText("");
                        }
                    }
                }
            }
        });

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAnimalListFragmentInteraction(pet);
                }
            }
        });
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

    @Override
    public int getItemCount() {
        return petList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        TextView petName, petDesc, lastUpdated, distance, shelterName;
        ImageView petImage;
        ProgressBar loadingCircle;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            petName = view.findViewById(R.id.pet_name);
            petDesc = view.findViewById(R.id.pet_desc);
            petImage = view.findViewById(R.id.pet_image);
            lastUpdated = view.findViewById(R.id.pet_last_updated);
            distance = view.findViewById(R.id.pet_distance);
            shelterName = view.findViewById(R.id.pet_shelter_name);
            loadingCircle = view.findViewById(R.id.list_element_loading_circle);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
