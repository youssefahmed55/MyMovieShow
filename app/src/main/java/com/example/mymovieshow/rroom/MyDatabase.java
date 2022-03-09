package com.example.mymovieshow.rroom;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mymovieshow.pojo.Converters;
import com.example.mymovieshow.pojo.Result2;


@Database(entities = Result2.class,version = 10 , exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MyDatabase extends RoomDatabase {
    public abstract MyInterfaceDao myInterfaceDao();

}
