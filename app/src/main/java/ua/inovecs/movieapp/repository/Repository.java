package ua.inovecs.movieapp.repository;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.inovecs.movieapp.Movie;
import ua.inovecs.movieapp.model.MovieResponse;

import static ua.inovecs.movieapp.repository.ApiService.getRetrofit;

public class Repository {

    private static final String TAG = Repository.class.getSimpleName();

    MovieApi repository = getRetrofit(Data.BASE_URL).create(MovieApi.class);

    private OnResponseListener listener;

    public Repository(OnResponseListener onResponseListener) {
        this.listener = onResponseListener;
    }

    public void fetchMovies() {
        Call<MovieResponse> call = repository.getPopularMovies(Data.API_KEY);

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

    public interface OnResponseListener {
        void onResponse(List<Movie> movies);
    }
}
