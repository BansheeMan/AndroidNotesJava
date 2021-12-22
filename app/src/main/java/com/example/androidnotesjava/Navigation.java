package com.example.androidnotesjava;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Navigation {

    private final FragmentManager fragmentManager;

    public Navigation(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void addFragment(Fragment fragment,boolean useBackStack){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.list_notes_container,fragment);
        if(useBackStack){
            fragmentTransaction.addToBackStack("");
        }
        fragmentTransaction.commit();
    }
}
