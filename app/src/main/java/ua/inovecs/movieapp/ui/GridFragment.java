package ua.inovecs.movieapp.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ua.inovecs.movieapp.model.Movie;
import ua.inovecs.movieapp.repository.Data;
import ua.inovecs.movieapp.viewmodel.MovieViewModel;
import ua.inovecs.movieapp.R;
import ua.inovecs.movieapp.databinding.GridFragmentBinding;

public class GridFragment extends DaggerFragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private GridFragmentBinding binding;
    private MovieViewModel viewModel;
    private DetailsFragment.OnToolbarDecoratorListener listener;

    @Inject
    MainNavigator navigator;
    @Inject
    SharedPreferences mPrefs;
    @Inject
    ViewModelProvider.Factory mModelFactory;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Movie movie = viewModel.getMovieList().getValue().get(position);
        boolean isMasterDetailsPage = getActivity().findViewById(R.id.activity_main_root_container) != null;
        int containerId = isMasterDetailsPage ? R.id.activity_main_details_container : R.id.content_frame;
        navigator.navigateTo(containerId, DetailsFragment.Factory.newInstance(movie), true);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (DetailsFragment.OnToolbarDecoratorListener) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        DecorationInfo info = new DecorationInfo();
        info.setShouldDecorate(!mPrefs.getBoolean(Data.DEVICE_TYPE_KEY, false));
        info.setShowBackArrow(false);
        info.setTitleResourceId(R.string.pop_movie);
        listener.decorate(info);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GridFragmentBinding.inflate(inflater, container, false);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        viewModel = ViewModelProviders.of(this, mModelFactory).get(MovieViewModel.class);
        viewModel.fetchMovies().observe(this, this::updateGridView);
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
        binding.gridView.setNumColumns(mPrefs.getBoolean(Data.DEVICE_TYPE_KEY, false) ? 3 : 2);
        if(mPrefs.getBoolean(Data.DEVICE_TYPE_KEY, false)) {
            navigator.navigateTo(R.id.activity_main_details_container, DetailsFragment.Factory.newInstance(movies.get(0)), false);
        }
        binding.gridView.setOnItemClickListener(this);
    }

}
