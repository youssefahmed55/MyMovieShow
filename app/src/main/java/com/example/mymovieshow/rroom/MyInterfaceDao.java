package com.example.mymovieshow.rroom;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mymovieshow.pojo.Result2;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;


@Dao
public interface MyInterfaceDao {
    @Query("select * from mytable")
   public Single<List<Result2>> getmovies();

    @Insert
    public Completable insertAll(List<Result2> movieList);

    @Query("Delete FROM mytable")
    public  Completable deleteAllMovies();

}
