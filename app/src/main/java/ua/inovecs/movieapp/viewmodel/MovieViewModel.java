package ua.inovecs.movieapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import ua.inovecs.movieapp.model.Movie;
import ua.inovecs.movieapp.model.VideoResponse;
import ua.inovecs.movieapp.repository.Repository;

public class MovieViewModel extends AndroidViewModel implements Repository.OnResponseListener, Repository.OnVideoResponseListener{

    private Repository repository;
    private MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();
    private MutableLiveData<VideoResponse> video = new MutableLiveData<>();
    private MutableLiveData<Boolean> masterDetailsPage = new MutableLiveData<>();

    public MovieViewModel(Application application) {
        super(application);
        this.repository = new Repository(application.getApplicationContext(),this, this);
    }

    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }

    public LiveData<VideoResponse> getVideoList() {
        return video;
    }

    public MutableLiveData<Boolean> getMasterDetailsPage() {
        return masterDetailsPage;
    }

    public void fetchMovies() {
        repository.fetchMovies();
    }

    public void fetchMovieVideo(int movieId) {
        repository.fetchMovieVideo(movieId);
    }

    @Override
    public void onResponse(List<Movie> movies) {
        movieList.setValue(movies);
    }

    @Override
    public void onVideoResponse(VideoResponse response) {
        video.setValue(response);
    }
}
