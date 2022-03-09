
package com.example.mymovieshow.pojo;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;



public class UpComing {

    @SerializedName("dates")
    private UpComingResult mUpComingResult;
    @SerializedName("page")
    private Long mPage;
    @SerializedName("results")
    private ArrayList<Result2> mResult2s;
    @SerializedName("total_pages")
    private Long mTotalPages;
    @SerializedName("total_results")
    private Long mTotalResults;

    public UpComingResult getDates() {
        return mUpComingResult;
    }

    public void setDates(UpComingResult upComingResult) {
        mUpComingResult = upComingResult;
    }

    public Long getPage() {
        return mPage;
    }

    public void setPage(Long page) {
        mPage = page;
    }

    public ArrayList<Result2> getResults() {
        return mResult2s;
    }

    public void setResults(ArrayList<Result2> result2s) {
        mResult2s = result2s;
    }

    public Long getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(Long totalPages) {
        mTotalPages = totalPages;
    }

    public Long getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(Long totalResults) {
        mTotalResults = totalResults;
    }

}
