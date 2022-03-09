package com.example.mymovieshow.rroom;


import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class MyDatabaseModule {

    @Provides
    @Singleton
    public static MyDatabase ProvideDatabase(Application application){
        return Room.databaseBuilder(application,MyDatabase.class,"mytable")
                .fallbackToDestructiveMigration()
                .build();

    }

    @Provides
    @Singleton
    public static MyInterfaceDao ProvideDao(MyDatabase myDatabase){
       return myDatabase.myInterfaceDao();
    }


}
