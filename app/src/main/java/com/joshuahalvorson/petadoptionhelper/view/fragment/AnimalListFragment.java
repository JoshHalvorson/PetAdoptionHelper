package com.joshuahalvorson.petadoptionhelper.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joshuahalvorson.petadoptionhelper.R;

public class AnimalListFragment extends Fragment {
    private static final String PET_LIST_ARG = "pet_list";

    private String petList;

    private OnFragmentInteractionListener mListener;

    public AnimalListFragment() {

    }

    public static AnimalListFragment newInstance(String petList) {
        AnimalListFragment fragment = new AnimalListFragment();
        Bundle args = new Bundle();
        args.putString(PET_LIST_ARG, petList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            petList = getArguments().getString(PET_LIST_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animal_list, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAnimalListFragmentInteraction(uri);
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
        void onAnimalListFragmentInteraction(Uri uri);
    }
}
