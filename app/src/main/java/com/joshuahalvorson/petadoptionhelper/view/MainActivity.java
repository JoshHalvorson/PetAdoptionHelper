package com.joshuahalvorson.petadoptionhelper.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.joshuahalvorson.petadoptionhelper.network.PetFinderApiViewModel;
import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.animal.AnimalsOverview;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
import com.joshuahalvorson.petadoptionhelper.animal.AnimalPetfinder;
import com.joshuahalvorson.petadoptionhelper.animal.Pets;
import com.joshuahalvorson.petadoptionhelper.shelter.Shelter;
import com.joshuahalvorson.petadoptionhelper.shelter.ShelterPetfinder;
import com.joshuahalvorson.petadoptionhelper.shelter.Shelters;
import com.joshuahalvorson.petadoptionhelper.shelter.SheltersOverview;
import com.joshuahalvorson.petadoptionhelper.view.fragment.AnimalListFragment;
import com.joshuahalvorson.petadoptionhelper.view.fragment.ShelterListFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AnimalListFragment.OnFragmentInteractionListener,
        ShelterListFragment.OnFragmentInteractionListener{

    private PetFinderApiViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ShelterListFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onAnimalListFragmentInteraction(Uri uri) {
        Log.i("animalListInteraction", "clicked");
    }

    @Override
    public void onShelterListFragmentInteraction(Uri uri) {
        Log.i("shelterListInteraction", "clicked");
    }
}
