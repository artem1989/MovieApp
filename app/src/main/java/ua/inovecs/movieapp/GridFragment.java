package ua.inovecs.movieapp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import ua.inovecs.movieapp.databinding.GridFragmentBinding;

public class GridFragment extends Fragment implements AdapterView.OnItemClickListener{

    private GridFragmentBinding binding;
    private MovieViewModel viewModel;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Movie movie = viewModel.getMovieList().getValue().get(position);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, DetailsFragment.Factory.newInstance(movie))
                .addToBackStack(null)
                .commit();
    }

    public static class Factory {
        /**
         * Create a new {@link GridFragment} for the supplied start page
         */
        public static GridFragment newInstance() {
            Bundle args = new Bundle(1);
            GridFragment fragment = new GridFragment();
            fragment.setArguments(args);
            return fragment;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.pop_movie);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GridFragmentBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(MovieViewModel.class);
        viewModel.fetchMovies().observe(this, this::updateGridView);
        return binding.getRoot();
    }

    private void updateGridView(List<Movie> movies) {
        binding.gridView.setAdapter(new MovieAdapter(getActivity(), movies));
        binding.gridView.setOnScrollListener(new ScrollViewListener(getActivity()));
        binding.gridView.setOnItemClickListener(this);
    }

}
