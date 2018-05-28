package ua.inovecs.movieapp.ui;

import android.arch.lifecycle.ViewModelProviders;
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

import ua.inovecs.movieapp.model.Movie;
import ua.inovecs.movieapp.R;

import ua.inovecs.movieapp.databinding.DetailsFragmentBinding;
import ua.inovecs.movieapp.model.VideoResponse;
import ua.inovecs.movieapp.repository.Data;
import ua.inovecs.movieapp.viewmodel.MovieViewModel;

public class DetailsFragment extends Fragment {

    private DetailsFragmentBinding binding;
    private MovieViewModel viewModel;

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
        if(!viewModel.getMasterDetailsPage().getValue()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.details);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DetailsFragmentBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(MovieViewModel.class);
        viewModel.getVideoList().observe(this, this::updateRecyclerView);
        Movie movie = (Movie) getArguments().getSerializable("movie");
        viewModel.fetchMovieVideo(movie.getId());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Movie movie = (Movie) getArguments().getSerializable("movie");
        Picasso.get().load(Data.BASE + movie.getBackDropPath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .fit()
                .tag(getActivity())
                .into(binding.logo);
        binding.description.setText(movie.getOverview());
        binding.rating.setText(String.format("%s%s", String.valueOf(movie.getVoteAverage()), getString(R.string.from)));
        Calendar releaseDate = Calendar.getInstance();
        releaseDate.setTime(movie.getReleaseDate());
        binding.releaseYear.setText(String.valueOf(releaseDate.get(Calendar.YEAR)));
        binding.toolbarView.setTitleTextColor(Color.parseColor("#FFFFFF"));
        binding.toolbarView.setTitle(movie.getTitle());
    }

    private void updateRecyclerView(VideoResponse response) {
        binding.trailers.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.trailers.setHasFixedSize(true);
        binding.trailers.setAdapter(new TrailersAdapter(response.getResults()));
    }
}
