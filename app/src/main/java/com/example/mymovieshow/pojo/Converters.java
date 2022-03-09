package com.example.mymovieshow.pojo;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public String fromlisttoString(List<Long> value) {
        return new Gson().toJson(value);
    }
    @TypeConverter
    public List<Long> fromStringtolist(String s) {
        return new Gson().fromJson(s,new TypeToken<List<Long>>() {}.getType());
    }
}
