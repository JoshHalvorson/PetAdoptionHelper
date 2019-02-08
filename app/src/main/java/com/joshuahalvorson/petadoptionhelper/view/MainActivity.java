package com.joshuahalvorson.petadoptionhelper.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.joshuahalvorson.petadoptionhelper.animal.StringPet;
import com.joshuahalvorson.petadoptionhelper.database.AnimalsDbDao;
import com.joshuahalvorson.petadoptionhelper.R;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
import com.joshuahalvorson.petadoptionhelper.shelter.Shelter;
import com.joshuahalvorson.petadoptionhelper.view.fragment.AnimalListFragment;
import com.joshuahalvorson.petadoptionhelper.view.fragment.AnimalViewedHistoryFragment;
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
        TaggedAnimalsFragment.OnFragmentInteractionListener,
        AnimalViewedHistoryFragment.OnFragmentInteractionListener{



    @Override
    public void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(getApplicationContext());
        Stetho.initializeWithDefaults(this);
    }


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

        AnimalsDbDao.initializeInstance(getApplicationContext());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new AnimalListFragment())
                .addToBackStack(null)
                .commit();

        String userEmail = getIntent().getStringExtra("user_email");
        String userName = getIntent().getStringExtra("user_name");

        View headerLayout = navigationView.getHeaderView(0);
        TextView navEmail = headerLayout.findViewById(R.id.user_email);
        TextView navUserName = headerLayout.findViewById(R.id.user_name);

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            navEmail.setText(userEmail);
            navUserName.setText(userName);
        }else{
            Snackbar.make(findViewById(R.id.fragment_container), "You are now logged out, " +
                    "favorited animals will no longer be saved online!", Snackbar.LENGTH_LONG)
                    .show();
            navEmail.setText("Not signed in");
            navUserName.setText("");
        }

        getSupportActionBar().setTitle("Pets");
        navigationView.getMenu().getItem(0).setChecked(true);

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
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
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
                getSupportActionBar().setTitle("Pets");
                break;
            case R.id.nav_shelters_list:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ShelterListFragment())
                        .commit();
                getSupportActionBar().setTitle("Shelters");
                break;
            case R.id.nav_favorite_pets:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new TaggedAnimalsFragment())
                        .commit();
                getSupportActionBar().setTitle("Favorite Pets");
                break;
            case R.id.nav_pets_history:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AnimalViewedHistoryFragment(),
                                "viewed_history")
                        .commit();
                getSupportActionBar().setTitle("Viewed Pets");
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onAnimalListFragmentInteraction(Pet item) {
        FloatingActionButton fab;
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

    @Override
    public void onHistoryAnimalListFragmentInteraction(StringPet item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("stringPet", item);
        DetailedStringAnimalFragment detailedStringAnimalFragment =
                new DetailedStringAnimalFragment();
        detailedStringAnimalFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, detailedStringAnimalFragment)
                .addToBackStack("animalHistory")
                .commit();
    }
}
