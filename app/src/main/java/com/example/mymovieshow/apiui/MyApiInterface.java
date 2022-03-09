package com.example.mymovieshow.apiui;

import com.example.mymovieshow.pojo.Movies;
import com.example.mymovieshow.pojo.Popular;
import com.example.mymovieshow.pojo.UpComing;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyApiInterface {
   final String API_KEY= "3874e3f19db8cd587f6c3864e2a22b20";

    @GET("3/movie/popular?api_key=" + API_KEY)
    Single<Popular> getdatap ();

    @GET("3/movie/top_rated?api_key=" + API_KEY)
    Single<Popular> getdatar ();

    @GET("3/movie/upcoming?api_key=" + API_KEY)
    Single<UpComing> getdataipcoming ();

    @GET("3/search/movie?api_key=" + API_KEY)
    Single<Popular> searchmovies (@Query("query") String name);

    @GET("3/movie/{movie_id}/videos?api_key=" + API_KEY)
    Single<Movies> getmoviesDetails (@Path("movie_id") Long mid);


}
