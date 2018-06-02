package ua.inovecs.movieapp.ui;

import android.os.Bundle;
import android.view.MenuItem;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ua.inovecs.movieapp.R;

public class MainActivity extends DaggerAppCompatActivity implements DetailsFragment.OnToolbarDecoratorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        boolean isMasterDetailsPage = findViewById(R.id.activity_main_root_container) != null;
        int containerId = isMasterDetailsPage ? R.id.activity_main_grid_container : R.id.content_frame;
        navigator.navigateTo(containerId, GridFragment.Factory.newInstance(), false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Inject
    MainNavigator navigator;

    @Override
    public void decorate(DecorationInfo info) {
        if (info.isShouldDecorate()) {
            getSupportActionBar().setTitle(info.getTitleResourceId());
            getSupportActionBar().setDisplayShowHomeEnabled(info.isShowBackArrow());
            getSupportActionBar().setDisplayHomeAsUpEnabled(info.isShowBackArrow());
        }
    }
}
