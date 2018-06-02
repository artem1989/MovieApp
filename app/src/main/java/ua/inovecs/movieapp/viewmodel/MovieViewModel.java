package ua.inovecs.movieapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

import javax.inject.Inject;

import ua.inovecs.movieapp.model.Movie;
import ua.inovecs.movieapp.model.VideoResponse;
import ua.inovecs.movieapp.repository.Repository;

public class MovieViewModel extends AndroidViewModel {

    private Repository repository;

    private MediatorLiveData<List<Movie>> movieList = new MediatorLiveData<>();
    private MediatorLiveData<VideoResponse> video = new MediatorLiveData<>();

    @Inject
    MovieViewModel(Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }

    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }

    public LiveData<VideoResponse> getVideoList() {
        return video;
    }

    public LiveData<List<Movie>> fetchMovies() {
        movieList.addSource(repository.fetchMovies(), data -> movieList.setValue(data));
        return movieList;
    }

    public void fetchMovieVideo(int movieId) {
        video.addSource(repository.fetchMovieVideo(movieId), data -> video.setValue(data));
        repository.fetchMovieVideo(movieId);
    }

}
