package com.example.mymovieshow.viewmodel;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymovieshow.pojo.Model;
import com.example.mymovieshow.pojo.Movies;
import com.example.mymovieshow.pojo.Popular;
import com.example.mymovieshow.pojo.Result2;

import com.example.mymovieshow.pojo.UpComing;
import com.example.mymovieshow.repositary.Repositary;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MoviesViewModel extends ViewModel {
    private static final String TAG = "Youssef";
    private Repositary repositary;
    private MutableLiveData<Model> arrayListMutableLiveDataa = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Result2>> arrayListMutableLiveData4 = new MutableLiveData<>();
    private MutableLiveData<Movies> arrayListMutableLiveData5 = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Result2>> arrayListMutableLiveData6 = new MutableLiveData<>();
    private MutableLiveData<String> username = new MutableLiveData<>();
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Saved");
    private FirebaseAuth mAuth ;






    @Inject
    public MoviesViewModel(Repositary repositary) {
        this.repositary = repositary;
    }

    public MutableLiveData<Model> getArrayListMutableLiveDataa() {
        return arrayListMutableLiveDataa;
    }


    public MutableLiveData<ArrayList<Result2>> getArrayListMutableLiveData4() {
        return arrayListMutableLiveData4;
    }

    public MutableLiveData<Movies> getArrayListMutableLiveData5() {
        return arrayListMutableLiveData5;
    }

    public MutableLiveData<ArrayList<Result2>> getArrayListMutableLiveData6() {
        return arrayListMutableLiveData6;
    }


    public MutableLiveData<String> getUsername() {
        return username;
    }



    public void getDatafromPopularMoviestoUpcoming(){

       repositary.getobservabledataapiPopular().subscribeOn(Schedulers.io())
               .map(new Function<Popular, ArrayList<Result2>>() {

                   @Override
                   public ArrayList<Result2> apply(Popular popular) throws Throwable {
                       ArrayList<Result2> list = popular.getResults();
                       for(Result2 result2 : list){
                           String url = "https://image.tmdb.org/t/p/w500" ;
                           result2.setBackdropPath(url+ result2.getBackdropPath());
                           result2.setPosterPath(url + result2.getPosterPath());
                       }

                       return list;
                   }
               })
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new SingleObserver<ArrayList<Result2>>() {
                   @Override
                   public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                   }

                   @Override
                   public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull ArrayList<Result2> result2s) {
                       getTopRatingMovies(result2s);


                   }

                   @Override
                   public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                       Log.d(TAG, "onError: "+e.getMessage());
                   }
               });

    }
    public void getTopRatingMovies(ArrayList<Result2> arr ){

        repositary.getobservabledataapiTopRating().subscribeOn(Schedulers.io())
                .map(new Function<Popular, ArrayList<Result2>>() {

                    @Override
                    public ArrayList<Result2> apply(Popular popular) throws Throwable {
                        ArrayList<Result2> list = popular.getResults();
                        for(Result2 result2 : list){
                            String url = "https://image.tmdb.org/t/p/w500" ;
                            result2.setBackdropPath(url+ result2.getBackdropPath());
                            result2.setPosterPath(url + result2.getPosterPath());
                        }

                        return list;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<Result2>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull ArrayList<Result2> result2s) {
                        getUpcomingMovies(arr,result2s);

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d(TAG, "getTopRatingMovies: " + e.getMessage());
                    }
                });

    }
    public void getUpcomingMovies(ArrayList<Result2> arr ,ArrayList<Result2> arr2){

        repositary.getobservabledataapiUpComing().subscribeOn(Schedulers.io())
                .map(new Function<UpComing, ArrayList<Result2>>() {

                    @Override
                    public ArrayList<Result2> apply(UpComing upComing) throws Throwable {
                        ArrayList<Result2> list = upComing.getResults();
                        for(Result2 result2 : list){
                            String url = "https://image.tmdb.org/t/p/w500" ;
                            result2.setBackdropPath(url+ result2.getBackdropPath());
                            result2.setPosterPath(url + result2.getPosterPath());
                        }

                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<Result2>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull ArrayList<Result2> result2s) {

                        ArrayList<String> stringArrayList = new ArrayList<>();
                        stringArrayList.add(String.valueOf(arr.size()));
                        arr.addAll(arr2);
                        stringArrayList.add(String.valueOf(arr.size()));
                        arr.addAll(result2s);
                        stringArrayList.add(String.valueOf(arr.size()));

                        arrayListMutableLiveDataa.setValue(new Model(arr,stringArrayList));
                        deleteandinsert(arr);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d(TAG, "getTopRatingMovies: " + e.getMessage());
                    }
                });


    }

    public void SearchMovie(String name){
        repositary.getobservabledataapiSearch(name).subscribeOn(Schedulers.io())
                .map(new Function<Popular, ArrayList<Result2>>() {

                    @Override
                    public ArrayList<Result2> apply(Popular popular) throws Throwable {
                        ArrayList<Result2> list = popular.getResults();
                        for(Result2 result2 : list){
                            String url = "https://image.tmdb.org/t/p/w500" ;
                            result2.setBackdropPath(url+ result2.getBackdropPath());
                            result2.setPosterPath(url + result2.getPosterPath());
                        }

                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o-> arrayListMutableLiveData4.setValue(o),e-> Log.d(TAG, "SearchMovies: " + e.getMessage()));


    }

    public void getmoviesDetails(Long mid){
        Log.d(TAG, "getmoviesDetailsss: " + mid);
        repositary.getmoviedetailss(mid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o-> arrayListMutableLiveData5.setValue(o),e-> Log.d(TAG, "getMoviesDetailsss2: " + e.getMessage()));

    }

    public void getSavedMovies(){
        mAuth = FirebaseAuth.getInstance();
        ArrayList<Result2> savedarrayList = new ArrayList<>();
        myRef.child("Users").child(mAuth.getUid()).child("movies_saved").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    savedarrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        savedarrayList.add(dataSnapshot.getValue(Result2.class));
                    }
                    arrayListMutableLiveData6.setValue(savedarrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: " + error.getMessage());
            }
        });

    }

    public void getuser(){
        mAuth = FirebaseAuth.getInstance();
        myRef.child("Users").child(mAuth.getCurrentUser().getUid()).child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setValue(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: " + error.getMessage());
            }
        });

    }


 //Room
   public void getdata(ArrayList<String> ar){


       repositary.getalldata().subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new SingleObserver<List<Result2>>() {
                           @Override
                           public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                           }

                           @Override
                           public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Result2> result2s) {
                               ArrayList<Result2> movieArrayList = new ArrayList<>();
                               movieArrayList.addAll(result2s);
                               arrayListMutableLiveDataa.setValue(new Model(movieArrayList,ar));
                               Log.d(TAG, "onSuccessc getdataroom: iam here ");
                           }

                           @Override
                           public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                           }
                       });
    }


   public void deleteandinsert(List<Result2> movieList){

       repositary.deleteallmovies().andThen(repositary.insert(movieList)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
           @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: delete&insert");
            }

           @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onErrorinsert: " + e.getMessage());
            }
        });
   }




}
