package ua.inovecs.movieapp.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ua.inovecs.movieapp.model.Movie;
import ua.inovecs.movieapp.R;

import ua.inovecs.movieapp.databinding.DetailsFragmentBinding;
import ua.inovecs.movieapp.model.VideoResponse;
import ua.inovecs.movieapp.repository.Data;
import ua.inovecs.movieapp.viewmodel.MovieViewModel;

public class DetailsFragment extends DaggerFragment {

    private static final String KEY = "movie";

    @Inject
    ViewModelProvider.Factory mModelFactory;

    @Inject
    SharedPreferences mPrefs;

    private DetailsFragmentBinding binding;
    OnToolbarDecoratorListener listener;

    public static class Factory {
        /**
         * Create a new {@link DetailsFragment} for the supplied movie instance
         */
        public static DetailsFragment newInstance(Movie movie) {
            Bundle args = new Bundle(1);
            DetailsFragment fragment = new DetailsFragment();
            args.putSerializable("movie", movie);
            fragment.setArguments(args);
            return fragment;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        DecorationInfo info = new DecorationInfo();
        info.setShouldDecorate(!mPrefs.getBoolean(Data.DEVICE_TYPE_KEY, false));
        info.setShowBackArrow(true);
        info.setTitleResourceId(R.string.details);
        listener.decorate(info);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnToolbarDecoratorListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DetailsFragmentBinding.inflate(inflater, container, false);
        MovieViewModel viewModel = ViewModelProviders.of(this, mModelFactory).get(MovieViewModel.class);
        viewModel.getVideoList().observe(this, this::updateRecyclerView);
        Movie movie = (Movie) getArguments().getSerializable(KEY);
        viewModel.fetchMovieVideo(movie.getId());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Movie movie = (Movie) getArguments().getSerializable(KEY);
        Picasso.get().load(Data.BASE + movie.getBackDropPath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .fit()
                .tag(getContext())
                .into(binding.logo);
        binding.description.setText(movie.getOverview());
        binding.rating.setText(String.format("%s%s", String.valueOf(movie.getVoteAverage()), getString(R.string.from)));
        setYear(movie);
        binding.toolbarView.setTitleTextColor(Color.parseColor("#FFFFFF"));
        binding.toolbarView.setTitle(movie.getTitle());
    }

    private void setYear(Movie movie) {
        Calendar releaseDate = Calendar.getInstance();
        releaseDate.setTime(movie.getReleaseDate());
        binding.releaseYear.setText(String.valueOf(releaseDate.get(Calendar.YEAR)));
    }

    private void updateRecyclerView(VideoResponse response) {
        binding.trailers.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.trailers.setHasFixedSize(true);
        binding.trailers.setAdapter(new TrailersAdapter(response.getResults()));
    }

    interface OnToolbarDecoratorListener {
        void decorate(DecorationInfo info);
    }
}
