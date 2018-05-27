package ua.inovecs.movieapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

import ua.inovecs.movieapp.Movie;
import ua.inovecs.movieapp.R;

import ua.inovecs.movieapp.databinding.DetailsFragmentBinding;
import ua.inovecs.movieapp.repository.Data;

public class DetailsFragment extends Fragment {

    private DetailsFragmentBinding binding;

    public static class Factory {
        /**
         * Create a new {@link GridFragment} for the supplied pageId
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.details);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DetailsFragmentBinding.inflate(inflater, container, false);
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
        binding.toolbarView.setTitleTextColor(getContext().getResources().getColor(R.color.colorWhite));
        binding.toolbarView.setTitle(movie.getTitle());
    }
}
