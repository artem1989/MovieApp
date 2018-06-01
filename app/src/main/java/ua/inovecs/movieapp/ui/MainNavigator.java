package ua.inovecs.movieapp.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import ua.inovecs.movieapp.R;

public class MainNavigator {

    private FragmentManager fragmentManager;

    MainNavigator(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    void navigateTo(int containerId, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (addToBackStack) transaction.addToBackStack(null);
        transaction
                .setCustomAnimations(R.anim.fade_in_medium, R.anim.fade_out)
                .replace(containerId, fragment)
                .commit();
    }

}
