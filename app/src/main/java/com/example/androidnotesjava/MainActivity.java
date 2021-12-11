package com.example.androidnotesjava;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            replaceFragment(R.id.list_notes_container, ListNotesFragment.newInstance());
        }

        initToolbar();
        initDrawer(initToolbar());
    }

    private void replaceFragment(int container, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .replace(container, fragment).commit();
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
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_drawer_about:
                    replaceFragment(R.id.list_notes_container, FavoriteFragment.newInstance());
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                case R.id.action_drawer_exit:
                    showDialogFragmentExit();
                    return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment backStackFragment = (Fragment) getSupportFragmentManager()
                .findFragmentById(R.id.list_notes_container);
        if (backStackFragment != null && backStackFragment instanceof NoteFragment) {
            onBackPressed();
        }
    }
}