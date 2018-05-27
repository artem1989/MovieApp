package ua.inovecs.movieapp.repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ua.inovecs.movieapp.model.MovieResponse;

public interface MovieRepository {

    String API_KEY = "api_key";

    @GET("movie/popular")
    Call<MovieResponse> getMovies(@Query(API_KEY) String apiKey);

}
