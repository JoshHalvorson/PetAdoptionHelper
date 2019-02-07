package com.joshuahalvorson.petadoptionhelper.view.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.adapter.TaggedPetListRecyclerViewAdapter;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
import com.joshuahalvorson.petadoptionhelper.animal.StringPet;
import com.joshuahalvorson.petadoptionhelper.database.TaggedAnimalsDbDao;
import com.joshuahalvorson.petadoptionhelper.network.PetFinderApiViewModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaggedAnimalsFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private static TaggedPetListRecyclerViewAdapter adapter;

    private static List<StringPet> taggedPetsList;

    private PetFinderApiViewModel viewModel;

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

        taggedPetsList.addAll(TaggedAnimalsDbDao.readAllTaggedAnimals());
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);

    }

    public static void refreshList(){
        taggedPetsList.clear();
        taggedPetsList.addAll(TaggedAnimalsDbDao.readAllTaggedAnimals());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateFirebaseDb();
        updateLocalDb();
    }

    private void updateLocalDb(){

        // Read from the database
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> ids = new ArrayList<>();

                for(DataSnapshot snapshot : dataSnapshot.child("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("animals").getChildren()){

                    ids.add(snapshot.getKey());
                }

                for(String id : ids){
                    LiveData<Pet> petLiveData = viewModel.getAnimalData(id, "json");
                    petLiveData.observe(getViewLifecycleOwner(), new Observer<Pet>() {
                        @Override
                        public void onChanged(@Nullable Pet pet) {
                            TaggedAnimalsDbDao.createAnimalEntry(pet);
                            taggedPetsList.add(new StringPet(pet));
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            });

    }

    private void updateFirebaseDb() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            for(StringPet stringPet : taggedPetsList){
                DatabaseReference reference = FirebaseDatabase.getInstance()
                        .getReference("users/" +
                                FirebaseAuth.getInstance().getCurrentUser().getUid() +
                                "/animals");
                reference.child(stringPet.getsId()).child("name").setValue(stringPet.getsName());
            }
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
        void onTaggedAnimalListFragmentInteraction(StringPet item);
    }

}
