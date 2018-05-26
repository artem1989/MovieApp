package ua.inovecs.movieapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieViewModel extends ViewModel {

    private static final String TAG = MovieViewModel.class.getSimpleName();

    public MutableLiveData<List<Movie>> getMovieList() {
        return movieList;
    }

    private MutableLiveData<List<Movie>> movieList;

    public LiveData<List<Movie>> fetchMovies() {
        if (movieList == null) {
            movieList = new MutableLiveData<>();
            loadMovies();
        }

        return movieList;
    }

    private void loadMovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Data.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieRepository repository = retrofit.create(MovieRepository.class);
        Call<MovieResponse> call = repository.getMovies(Data.API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.body() != null)
                movieList.setValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "Network error occured!");
            }
        });
    }

}
