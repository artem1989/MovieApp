package ua.inovecs.movieapp.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.inovecs.movieapp.model.Movie;
import ua.inovecs.movieapp.model.MovieResponse;
import ua.inovecs.movieapp.model.VideoDetails;
import ua.inovecs.movieapp.model.VideoResponse;

import static ua.inovecs.movieapp.repository.ApiService.getRetrofit;

public class Repository {

    private static final String TAG = Repository.class.getSimpleName();
    private static final String API_KEY = "api_key";
    private static final String APPEND_TO_RESPONSE = "append_to_response";
    private static final String VIDEOS = "videos";

    private MovieApi api;
    private OnResponseListener listener;
    private OnVideoResponseListener onVideoResponseListener;

    public Repository(Context context, OnResponseListener onResponseListener, OnVideoResponseListener listener) {
        this.listener = onResponseListener;
        this.onVideoResponseListener = listener;
        api = getRetrofit(Data.BASE_URL, context).create(MovieApi.class);
    }

    public void fetchMovies() {
        Call<MovieResponse> call = api.getPopularMovies(Data.API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.body() != null)
                    listener.onResponse(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "Network error occured! " + t.getMessage());
            }
        });
    }

    public void fetchMovieVideo(int movieId) {
        Map<String, String> params = new HashMap<>();
        params.put(API_KEY, Data.API_KEY);
        params.put(APPEND_TO_RESPONSE, VIDEOS);

        Call<VideoDetails> call = api.getVideoDetails(movieId, params);

        call.enqueue(new Callback<VideoDetails>() {
            @Override
            public void onResponse(Call<VideoDetails> call, Response<VideoDetails> response) {
                if (response.body() != null)
                    onVideoResponseListener.onVideoResponse(response.body().getVideos());
            }

            @Override
            public void onFailure(Call<VideoDetails> call, Throwable t) {
                Log.e(TAG, "Network error occured! " + t.getMessage());
            }
        });
    }

    public interface OnResponseListener {
        void onResponse(List<Movie> movies);
    }

    public interface OnVideoResponseListener {
        void onVideoResponse(VideoResponse response);
    }
}
