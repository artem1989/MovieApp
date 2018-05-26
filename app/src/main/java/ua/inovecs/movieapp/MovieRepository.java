package ua.inovecs.movieapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieRepository {

    @GET("movie/popular")
    Call<MovieResponse> getMovies(@Query("api_key") String apiKey);

}
