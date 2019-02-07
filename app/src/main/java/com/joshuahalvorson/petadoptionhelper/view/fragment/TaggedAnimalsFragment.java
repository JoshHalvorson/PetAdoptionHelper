package com.joshuahalvorson.petadoptionhelper.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.adapter.TaggedPetListRecyclerViewAdapter;
import com.joshuahalvorson.petadoptionhelper.animal.AnimalId;
import com.joshuahalvorson.petadoptionhelper.animal.StringPet;
import com.joshuahalvorson.petadoptionhelper.database.AnimalsDbDao;
import com.joshuahalvorson.petadoptionhelper.network.PetFinderApiViewModel;
import java.util.ArrayList;
import java.util.List;

public class TaggedAnimalsFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private static TaggedPetListRecyclerViewAdapter adapter;

    private static List<StringPet> taggedPetsList;

    private PetFinderApiViewModel viewModel;

    private ProgressBar loadingCircle;

    public TaggedAnimalsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tagged_animals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(PetFinderApiViewModel.class);

        loadingCircle = view.findViewById(R.id.favorite_animals_loading_circle);

        taggedPetsList = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.tagged_animals_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(
                        recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new TaggedPetListRecyclerViewAdapter(taggedPetsList, mListener);

        recyclerView.setAdapter(adapter);

    }

    public static void refreshList(){
        taggedPetsList.clear();
        taggedPetsList.addAll(AnimalsDbDao.readAllTaggedAnimals());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateDatabases();

    }

    private void updateLocalDb(){
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot : dataSnapshot.child("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("animals").getChildren()){

                        AnimalId petFromFb = snapshot.getValue(AnimalId.class);

                        StringPet stringPet = new StringPet(petFromFb);

                        taggedPetsList.add(stringPet);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
        }else{
            taggedPetsList.clear();
            taggedPetsList.addAll(AnimalsDbDao.readAllTaggedAnimals());
            adapter.notifyDataSetChanged();
        }
        loadingCircle.setVisibility(View.GONE);

    }

    private void updateDatabases() {
        List<StringPet> localDb = AnimalsDbDao.readAllTaggedAnimals();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            for(StringPet stringPet : localDb){
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
                        .child("shelter_name").setValue(stringPet.getsShelterName());
                reference.child("users").child(userId).child("animals")
                        .child(stringPet.getsId())
                        .child("distance").setValue(stringPet.getsDistance());
            }
        }
        updateLocalDb();
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
        void onTaggedAnimalListFragmentInteraction(StringPet item);
    }

}
