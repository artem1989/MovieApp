package ua.inovecs.movieapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import ua.inovecs.movieapp.model.Movie;
import ua.inovecs.movieapp.viewmodel.MovieViewModel;
import ua.inovecs.movieapp.R;
import ua.inovecs.movieapp.databinding.GridFragmentBinding;

public class GridFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private GridFragmentBinding binding;
    private MovieViewModel viewModel;
    private OnContentLoadedListener listener;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Movie movie = viewModel.getMovieList().getValue().get(position);
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in_medium, R.anim.fade_out)
                .replace(viewModel.getMasterDetailsPage().getValue() ? R.id.activity_main_details_container : R.id.content_frame, DetailsFragment.Factory.newInstance(movie))
                .addToBackStack(null)
                .commit();
    }

    public static class Factory {
        /**
         * Create a new {@link GridFragment} for the actual start page
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
        listener = (OnContentLoadedListener) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GridFragmentBinding.inflate(inflater, container, false);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        viewModel = ViewModelProviders.of(getActivity()).get(MovieViewModel.class);
        viewModel.getMasterDetailsPage().setValue(getActivity().findViewById(R.id.activity_main_root_container) != null);
        viewModel.getMovieList().observe(this, this::updateGridView);
        viewModel.fetchMovies();
        return binding.getRoot();
    }

    @Override
    public void onRefresh() {
        viewModel.fetchMovies();
    }

    private void updateGridView(List<Movie> movies) {
        binding.swipeRefreshLayout.setRefreshing(false);
        binding.gridView.setAdapter(new MovieAdapter(getActivity(), movies));
        binding.gridView.setOnScrollListener(new ScrollViewListener(getActivity()));
        binding.gridView.setNumColumns(viewModel.getMasterDetailsPage().getValue() ? 3 : 2);
        binding.gridView.setOnItemClickListener(this);
        listener.onContentLoaded(movies);
    }

    interface OnContentLoadedListener {
        void onContentLoaded(List<Movie> movies);
    }

}
