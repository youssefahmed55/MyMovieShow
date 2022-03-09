package com.example.mymovieshow.pojo;

import java.util.ArrayList;

public class Model {
    private ArrayList<Result2> result2ArrayList;
    private ArrayList<String> num;

    public Model(ArrayList<Result2> result2ArrayList, ArrayList<String> num) {
        this.result2ArrayList = result2ArrayList;
        this.num = num;
    }

    public ArrayList<Result2> getResult2ArrayList() {
        return result2ArrayList;
    }

    public void setResult2ArrayList(ArrayList<Result2> result2ArrayList) {
        this.result2ArrayList = result2ArrayList;
    }

    public ArrayList<String> getNum() {
        return num;
    }

    public void setNum(ArrayList<String> num) {
        this.num = num;
    }
}
