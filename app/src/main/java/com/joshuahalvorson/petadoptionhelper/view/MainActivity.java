package com.joshuahalvorson.petadoptionhelper.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.joshuahalvorson.petadoptionhelper.animal.StringPet;
import com.joshuahalvorson.petadoptionhelper.database.TaggedAnimalsDbDao;
import com.joshuahalvorson.petadoptionhelper.network.PetFinderApiViewModel;
import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
import com.joshuahalvorson.petadoptionhelper.shelter.Shelter;
import com.joshuahalvorson.petadoptionhelper.view.fragment.AnimalListFragment;
import com.joshuahalvorson.petadoptionhelper.view.fragment.DetailedAnimalFragment;
import com.joshuahalvorson.petadoptionhelper.view.fragment.DetailedShelterFragment;
import com.joshuahalvorson.petadoptionhelper.view.fragment.DetailedStringAnimalFragment;
import com.joshuahalvorson.petadoptionhelper.view.fragment.ShelterListFragment;
import com.joshuahalvorson.petadoptionhelper.view.fragment.TaggedAnimalsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AnimalListFragment.OnFragmentInteractionListener,
        ShelterListFragment.OnFragmentInteractionListener,
        DetailedAnimalFragment.OnFragmentInteractionListener,
        TaggedAnimalsFragment.OnFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TaggedAnimalsDbDao.initializeInstance(getApplicationContext());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new AnimalListFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        switch (item.getItemId()){
            case R.id.nav_pets_list:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AnimalListFragment())
                        .commit();
                break;
            case R.id.nav_shelters_list:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ShelterListFragment())
                        .commit();
                break;
            case R.id.nav_favorite_pets:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new TaggedAnimalsFragment())
                        .commit();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onAnimalListFragmentInteraction(Pet item) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, DetailedAnimalFragment.newInstance(item))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDetailedAnimalFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTaggedAnimalListFragmentInteraction(StringPet item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("stringPet", item);
        DetailedStringAnimalFragment detailedStringAnimalFragment =
                new DetailedStringAnimalFragment();
        detailedStringAnimalFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, detailedStringAnimalFragment)
                .addToBackStack("taggedAnimals")
                .commit();


    }

    @Override
    public void onShelterListFragmentInteraction(Shelter shelter) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, DetailedShelterFragment.newInstance(shelter))
                .addToBackStack(null)
                .commit();
    }
}
