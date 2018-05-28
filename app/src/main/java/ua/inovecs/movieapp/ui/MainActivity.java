package ua.inovecs.movieapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.List;

import ua.inovecs.movieapp.model.Movie;
import ua.inovecs.movieapp.R;

public class MainActivity extends AppCompatActivity implements GridFragment.OnContentLoadedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        boolean isMasterDetailsPage = findViewById(R.id.activity_main_root_container) != null;
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in_medium, R.anim.fade_out)
                .replace(isMasterDetailsPage ? R.id.activity_main_grid_container : R.id.content_frame, GridFragment.Factory.newInstance())
                .commit();

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

    @Override
    public void onContentLoaded(List<Movie> movies) {
        if (findViewById(R.id.activity_main_root_container) != null)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_details_container, DetailsFragment.Factory.newInstance(movies.get(0)))
                    .commit();
    }
}
