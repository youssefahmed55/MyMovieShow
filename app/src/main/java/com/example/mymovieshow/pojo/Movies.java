
package com.example.mymovieshow.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Movies {

    @SerializedName("id")
    private Long mId;
    @SerializedName("results")
    private List<MoviesResult> mMoviesResults;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public List<MoviesResult> getResults() {
        return mMoviesResults;
    }

    public void setResults(List<MoviesResult> moviesResults) {
        mMoviesResults = moviesResults;
    }

}
