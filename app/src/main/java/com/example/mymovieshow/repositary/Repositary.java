package com.example.mymovieshow.repositary;

import com.example.mymovieshow.apiui.MyApiInterface;
import com.example.mymovieshow.pojo.Movies;
import com.example.mymovieshow.pojo.Popular;
import com.example.mymovieshow.pojo.Result2;
import com.example.mymovieshow.pojo.UpComing;
import com.example.mymovieshow.rroom.MyInterfaceDao;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Action;

public class Repositary implements @NonNull Action {
    private static final String TAG = "Repositary";
   private MyApiInterface myApiInterface;
    private MyInterfaceDao myInterfaceDao;



    @Inject
    public Repositary(MyApiInterface myApiInterface ,MyInterfaceDao myInterfaceDao) {
        this.myApiInterface = myApiInterface;
        this.myInterfaceDao = myInterfaceDao;

    }
    //Api
    public Single<Popular> getobservabledataapiPopular(){
        return myApiInterface.getdatap();
    }

    public Single<Popular> getobservabledataapiTopRating(){
        return myApiInterface.getdatar();
    }
    public Single<UpComing> getobservabledataapiUpComing(){
        return myApiInterface.getdataipcoming();
    }
    public Single<Popular> getobservabledataapiSearch(String name){
        return myApiInterface.searchmovies(name);
    }
    public Single<Movies> getmoviedetailss(Long mid){
        return myApiInterface.getmoviesDetails(mid);
    }

    //Room
    public Completable insert (List<Result2> movieList){
        return myInterfaceDao.insertAll(movieList);
    }

    public Single<List<Result2>> getalldata(){
        return myInterfaceDao.getmovies();
    }

    public Completable deleteallmovies(){
        return  myInterfaceDao.deleteAllMovies();
    }


    @Override
    public void run() throws Throwable {

    }
}
