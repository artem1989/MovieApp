package ua.inovecs.movieapp.repository;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import ua.inovecs.movieapp.model.MovieResponse;
import ua.inovecs.movieapp.model.VideoDetails;

public interface MovieApi {

    String API_KEY = "api_key";
    String MOVIE_ID = "movie_id";

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query(API_KEY) String apiKey);

    @GET("movie/{movie_id}")
    Call<VideoDetails> getVideoDetails(@Path(MOVIE_ID) int movieId, @QueryMap Map<String, String> options);

}
