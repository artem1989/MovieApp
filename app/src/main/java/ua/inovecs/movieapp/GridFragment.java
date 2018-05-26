package ua.inovecs.movieapp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ua.inovecs.movieapp.databinding.GridFragmentBinding;

public class GridFragment extends Fragment {

    private GridFragmentBinding binding;

    public static class Factory {
        /**
         * Create a new {@link GridFragment} for the supplied pageId
         */
        public static GridFragment newInstance() {
            Bundle args = new Bundle(1);
            GridFragment fragment = new GridFragment();
            fragment.setArguments(args);
            return fragment;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GridFragmentBinding.inflate(inflater, container, false);
        MovieViewModel model = ViewModelProviders.of(getActivity()).get(MovieViewModel.class);
        model.getMovies().observe(this, this::updateGridView);
        return binding.getRoot();
    }

    private void updateGridView(List<Movie> movies) {
        binding.gridView.setAdapter(new MovieAdapter(getActivity(), movies));
        binding.gridView.setOnScrollListener(new ScrollViewListener(getActivity()));
    }

}
