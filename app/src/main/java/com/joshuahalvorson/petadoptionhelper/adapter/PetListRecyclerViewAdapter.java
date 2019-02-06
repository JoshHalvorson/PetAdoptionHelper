package com.joshuahalvorson.petadoptionhelper.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
import com.joshuahalvorson.petadoptionhelper.animal.Photo;
import com.joshuahalvorson.petadoptionhelper.animal.Photos;
import com.joshuahalvorson.petadoptionhelper.view.fragment.AnimalListFragment;
import java.util.List;


public class PetListRecyclerViewAdapter extends RecyclerView.Adapter<PetListRecyclerViewAdapter.ViewHolder> {

    private final List<Pet> petList;

    private AnimalListFragment.OnFragmentInteractionListener listener;

    public PetListRecyclerViewAdapter(List<Pet> petList,
                                      AnimalListFragment.OnFragmentInteractionListener listener) {
        this.petList = petList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pet_list_element_layout, viewGroup, false);
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
                    .into(viewHolder.petImage);
        }


        String desc = pet.getDescription().getAnimalDescription();
        if(desc != null){
            if(desc.length() > 150){
                desc = desc.substring(0, 150);
                desc += "...";
                viewHolder.petDesc.setText(desc);
            }
        }

        String lastUpdated = pet.getLastUpdate().getLastUpdate().substring(0, 10);
        if(lastUpdated != null){
            viewHolder.lastUpdated.setText("Last updated: " + lastUpdated);
        }

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAnimalListFragmentInteraction(pet);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        TextView petName, petDesc, lastUpdated;
        ImageView petImage;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            petName = view.findViewById(R.id.pet_name);
            petDesc = view.findViewById(R.id.pet_desc);
            petImage = view.findViewById(R.id.pet_image);
            lastUpdated = view.findViewById(R.id.pet_last_updated);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
