package com.joshuahalvorson.petadoptionhelper.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.shelter.Shelter;
import java.util.List;

public class ShelterListRecyclerViewAdapter extends RecyclerView.Adapter<ShelterListRecyclerViewAdapter.ViewHolder> {

    private final List<Shelter> shelterList;

    public ShelterListRecyclerViewAdapter(List<Shelter> shelterList) {
        this.shelterList = shelterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shelter_list_element_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.shelterName.setText(shelterList.get(i).getName().get$t());
        viewHolder.shelterCity.setText(shelterList.get(i).getCity().get$t());
    }

    @Override
    public int getItemCount() {
        return shelterList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public TextView shelterName, shelterCity;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            shelterName = view.findViewById(R.id.shelter_name);
            shelterCity = view.findViewById(R.id.shelter_city);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
