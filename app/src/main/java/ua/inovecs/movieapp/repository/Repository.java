package ua.inovecs.movieapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ua.inovecs.movieapp.model.Movie;
import ua.inovecs.movieapp.model.MovieResponse;
import ua.inovecs.movieapp.model.VideoDetails;
import ua.inovecs.movieapp.model.VideoResponse;

@Singleton
public class Repository {

    private static final String TAG = Repository.class.getSimpleName();
    private static final String API_KEY = "api_key";
    private static final String APPEND_TO_RESPONSE = "append_to_response";
    private static final String VIDEOS = "videos";

    private MovieApi api;

    @Inject
    Repository(Retrofit retrofit) {
        api = retrofit.create(MovieApi.class);
    }

    public LiveData<List<Movie>> fetchMovies() {
        Call<MovieResponse> call = api.getPopularMovies(Data.API_KEY);
        MutableLiveData<List<Movie>> data = new MutableLiveData<>();

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.body() != null)
                    data.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "Network error occured! " + t.getMessage());
            }
        });

        return data;
    }

    public LiveData<VideoResponse> fetchMovieVideo(int movieId) {
        Map<String, String> params = new HashMap<>();
        params.put(API_KEY, Data.API_KEY);
        params.put(APPEND_TO_RESPONSE, VIDEOS);

        Call<VideoDetails> call = api.getVideoDetails(movieId, params);
        MutableLiveData<VideoResponse> data = new MutableLiveData<>();

        call.enqueue(new Callback<VideoDetails>() {
            @Override
            public void onResponse(Call<VideoDetails> call, Response<VideoDetails> response) {
                if (response.body() != null)
                    data.postValue(response.body().getVideos());
            }

            @Override
            public void onFailure(Call<VideoDetails> call, Throwable t) {
                Log.e(TAG, "Network error occured! " + t.getMessage());
            }
        });

        return data;
    }
}
