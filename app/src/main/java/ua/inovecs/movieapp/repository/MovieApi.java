package ua.inovecs.movieapp.repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ua.inovecs.movieapp.model.MovieResponse;

public interface MovieApi {

    String API_KEY = "api_key";

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query(API_KEY) String apiKey);

}
