package ua.inovecs.movieapp;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieViewModel model = ViewModelProviders.of(this).get(MovieViewModel.class);
        model.getMovies().observe(this, this::updateGridView);

    }

    private void updateGridView(List<Movie> movies) {
        GridView gv = findViewById(R.id.grid_view);
        gv.setAdapter(new MovieAdapter(this, movies));
        gv.setOnScrollListener(new ScrollViewListener(this));
    }
}
