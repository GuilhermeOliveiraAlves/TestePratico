package com.avalicaopraticast.avalicaopraticast.apis;

import com.avalicaopraticast.avalicaopraticast.model.Config;
import com.avalicaopraticast.avalicaopraticast.model.MovieJson;
import com.avalicaopraticast.avalicaopraticast.model.TrailerJson;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IThemoviedbApi {
    @GET("/3/configuration")
    Call<Config> getConfiguracoes(@Query("api_key")String apiKey);
    @GET("3/discover/movie")
    Call<MovieJson> getMovies(@Query("page")String page,@Query("sort_by")String sortBy,@Query("api_key")String apiKey);
    @GET("/3/movie/{movieId}/videos")
    Call<TrailerJson> getTrailers(@Path("movieId") String movieId,@Query("api_key")String apiKey);

}
