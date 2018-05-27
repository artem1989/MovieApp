package ua.inovecs.movieapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import ua.inovecs.movieapp.Movie;
import ua.inovecs.movieapp.repository.Repository;

public class MovieViewModel extends ViewModel implements Repository.OnResponseListener {

    private Repository repository;
    private MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();

    public MovieViewModel() {
        this.repository = new Repository(this);
    }

    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }

    public void fetchMovies() {
        repository.fetchMovies();
    }

    @Override
    public void onResponse(List<Movie> movies) {
        movieList.setValue(movies);
    }
}
