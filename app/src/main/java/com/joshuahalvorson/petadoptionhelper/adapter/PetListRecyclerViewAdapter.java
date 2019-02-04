package com.joshuahalvorson.petadoptionhelper.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
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
        viewHolder.petName.setText(petList.get(i).getName().get$t());

        String desc = petList.get(i).getDescription().get$t();
        if(desc != null){
            if(desc.length() > 200){
                desc = desc.substring(0, 200);
                desc += "...";
                viewHolder.petDesc.setText(desc);
            }
        }

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAnimalListFragmentInteraction(petList.get(i));
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
        public TextView petName, petDesc;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            petName = view.findViewById(R.id.pet_name);
            petDesc = view.findViewById(R.id.pet_desc);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
