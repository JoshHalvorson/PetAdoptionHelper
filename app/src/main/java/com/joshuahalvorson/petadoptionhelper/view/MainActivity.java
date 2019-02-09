package com.joshuahalvorson.petadoptionhelper.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AnimalListFragment.OnFragmentInteractionListener,
        ShelterListFragment.OnFragmentInteractionListener,
        DetailedAnimalFragment.OnFragmentInteractionListener,
        TaggedAnimalsFragment.OnFragmentInteractionListener,
        AnimalViewedHistoryFragment.OnFragmentInteractionListener{

    public static final int SIGN_IN_REQUEST_CODE = 1;
    public static final int PERMISSIONS_REQUEST_CODE = 2;
    double currentLat, currentLon;
    int zipcode;
    Bundle bundle;
    NavigationView navigationView;
    AnimalListFragment animalListFragment;
    ShelterListFragment shelterListFragment;

    GoogleSignInClient googleSignIn;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;

    String userEmail, userName;
    View headerLayout;
    TextView navEmail, navUserName;

    private final android.location.LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            currentLat = location.getLatitude();
            currentLon = location.getLongitude();

            passBundle();

            Log.i("locationListener", "(onLocationChanged) Location lat: " +
                    Double.toString(currentLat) + " Location lon: " + Double.toString(currentLon));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i("locationListener", "(onStatusChanged) Provider: " +
                    provider + " Status: " + Integer.toString(status));
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.i("locationListener", "(onProviderEnabled) Provider: " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.i("locationListener", "(onProviderDisabled) Provider: " + provider);
        }
    };



    @Override
    public void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(getApplicationContext());
        Stetho.initializeWithDefaults(this);
        auth.addAuthStateListener(authStateListener);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE);

        } else {
            getLocation();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    userEmail = firebaseAuth.getCurrentUser().getEmail();
                    userName = firebaseAuth.getCurrentUser().getDisplayName();
                    navEmail.setText(userEmail);
                    navUserName.setText(userName);
                    headerLayout.findViewById(R.id.log_in).setVisibility(View.GONE);
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        googleSignIn = GoogleSignIn.getClient(this, gso);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        AnimalsDbDao.initializeInstance(getApplicationContext());

        auth = FirebaseAuth.getInstance();

        headerLayout = navigationView.getHeaderView(0);
        navEmail = headerLayout.findViewById(R.id.user_email);
        navUserName = headerLayout.findViewById(R.id.user_name);

        headerLayout.findViewById(R.id.log_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            headerLayout.findViewById(R.id.log_in).setVisibility(View.GONE);
        }else{
            Snackbar.make(findViewById(R.id.fragment_container), "You are logged out, " +
                    "favorited animals will no longer be saved online!", Snackbar.LENGTH_LONG)
                    .show();
            headerLayout.findViewById(R.id.log_in).setVisibility(View.VISIBLE);
        }

        animalListFragment = new AnimalListFragment();
        shelterListFragment = new ShelterListFragment();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getLocation();
            }
        }
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

                bundle = new Bundle();
                bundle.putInt("zipcode", zipcode);
                bundle.putString("lat", Double.toString(currentLat));
                bundle.putString("lon", Double.toString(currentLon));

                animalListFragment.setArguments(bundle);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, animalListFragment)
                        .addToBackStack(null)
                        .commit();

                break;
            case R.id.nav_shelters_list:
                bundle = new Bundle();
                bundle.putInt("zipcode", zipcode);
                bundle.putString("lat", Double.toString(currentLat));
                bundle.putString("lon", Double.toString(currentLon));

                shelterListFragment.setArguments(bundle);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, shelterListFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.nav_favorite_pets:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new TaggedAnimalsFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.nav_pets_history:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AnimalViewedHistoryFragment(),
                                "viewed_history")
                        .addToBackStack(null)
                        .commit();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signIn() {
        Intent signInIntent = googleSignIn.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_IN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                        } else {
                        }
                    }
                });
    }

    private void getLocation() {
        if (ActivityCompat
                .checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat
                .checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            return;

        }
        LocationManager locationManger = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location location = locationManger.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        currentLat = location.getLatitude();
        currentLon = location.getLongitude();
        if(location == null){
            locationManger.requestSingleUpdate(
                    LocationManager.GPS_PROVIDER, locationListener, null);
        }else{
            passBundle();
        }

    }

    private void passBundle() {
        zipcode = Integer.parseInt(getZipcode(currentLat, currentLon));

        bundle = new Bundle();
        bundle.putInt("zipcode", zipcode);
        bundle.putString("lat", Double.toString(currentLat));
        bundle.putString("lon", Double.toString(currentLon));

        animalListFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, animalListFragment)
                .commit();
        getSupportActionBar().setTitle("Pets");

        /*getSupportActionBar().setTitle("Pets");*/
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private String getZipcode(double lat, double lon){
        List<Address> addresses = new ArrayList<>();
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses.get(0).getPostalCode();
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
