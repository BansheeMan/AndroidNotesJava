package com.example.androidnotesjava;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.androidnotesjava.observe.Publisher;
import com.example.androidnotesjava.ui.DialogFragmentExit;
import com.example.androidnotesjava.ui.FavoriteFragment;
import com.example.androidnotesjava.ui.ListNotesFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private Publisher publisher = new Publisher();
    private Navigation navigation;

    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = new Navigation(getSupportFragmentManager());

        initToolbar();
        initDrawer(initToolbar());
        navigation.addFragment(ListNotesFragment.newInstance(),false);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    private void showDialogFragmentExit(){
        new DialogFragmentExit().show(getSupportFragmentManager(), "DialogFragmentExit");
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private  void initDrawer(Toolbar toolbar) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_drawer_notes:
                    navigation.addFragment(ListNotesFragment.newInstance(),false);
                    break;
                case R.id.action_drawer_favorite:
                    navigation.addFragment(FavoriteFragment.newInstance(),false);
                    break;
                case R.id.action_drawer_exit:
                    showDialogFragmentExit();
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackStackChanged() {
        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }else{
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public Navigation getNavigation() {
        return navigation;
    }
}