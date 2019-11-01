package com.example.rapido.network;

import com.example.rapido.response.popularVideos.Example;
import com.example.rapido.response.popularVideos.Item;
import com.example.rapido.utils.Constants;
import com.google.api.services.youtube.YouTube;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    /*Most Popular Videos */
    @GET("videos")
    Single<Example> getVideos(@Query(Constants.KEY) String apiKey,
                              @Query(Constants.CHART)String mostPopular,
                              @Query(Constants.MAX_RESULTS)String maxResult,
                              @Query(Constants.PART)String snippet
                               );

    /*Single Video Details */
    @GET("videos")
    Single<Example>getVideoDetails(@Query(Constants.PART)String snippet,
                                @Query(Constants.ID)String videoID,
                                @Query(Constants.KEY)String apiKey);

}
