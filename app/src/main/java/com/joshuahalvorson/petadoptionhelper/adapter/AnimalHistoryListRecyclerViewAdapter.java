package com.joshuahalvorson.petadoptionhelper.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
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
import com.joshuahalvorson.petadoptionhelper.animal.StringPet;
import com.joshuahalvorson.petadoptionhelper.database.AnimalsDbDao;
import com.joshuahalvorson.petadoptionhelper.view.fragment.AnimalViewedHistoryFragment;
import com.joshuahalvorson.petadoptionhelper.view.fragment.TaggedAnimalsFragment;

import java.util.List;

public class AnimalHistoryListRecyclerViewAdapter extends RecyclerView.Adapter<AnimalHistoryListRecyclerViewAdapter.ViewHolder> {

    private final List<StringPet> petList;

    private AnimalViewedHistoryFragment.OnFragmentInteractionListener listener;

    Context context;

    public AnimalHistoryListRecyclerViewAdapter(
            List<StringPet> petList, AnimalViewedHistoryFragment.OnFragmentInteractionListener listener) {
        this.petList = petList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pet_list_element_layout, viewGroup, false);
        context = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final StringPet pet = petList.get(i);
        viewHolder.petName.setText(pet.getsName());

        String imageUrl = pet.getsMedia();
        if(imageUrl != null){

            Glide.with(viewHolder.petImage.getContext())
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
                            viewHolder.loadingCircle.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.petImage);
        }


        String desc = pet.getsDescription();
        if(desc != null){
            if(desc.length() > 150){
                desc = desc.substring(0, 150);
                desc += "...";
                viewHolder.petDesc.setText(desc);
            }
        }

        String lastUpdated = pet.getsLastUpdate().substring(0, 10);
        if(lastUpdated != null){
            viewHolder.lastUpdated.setText("Last updated: " + lastUpdated);
        }

        String distance = pet.getsDistance();
        if(distance != null){
            viewHolder.distance.setText(distance + " Miles");
        }

        String shelterName = pet.getsShelterName();
        if(shelterName != null){
            viewHolder.shelterName.setText("Shelter: " + shelterName);
        }

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onHistoryAnimalListFragmentInteraction(pet);
                }
            }
        });

        viewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AnimalsDbDao.deleteAnimalEntry(pet);
                TaggedAnimalsFragment.refreshList();
                return true;
            }
        });
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
