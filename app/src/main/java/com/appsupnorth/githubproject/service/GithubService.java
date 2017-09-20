package com.appsupnorth.githubproject.service;


import com.appsupnorth.githubproject.service.response_entities.GithubRepo;
import com.appsupnorth.githubproject.service.response_entities.GithubUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by mark.schumacher@scotiabank.com on 2017-09-19.
 */
public class GithubService {

    public static String ENDPOINT = "https://api.github.com/";

    public interface GithubApi {

        @GET("users/{userId}")
        Observable<GithubUser> userInfo(@Path("userId") String owner);


        @GET("users/{userId}/repos")
        Observable<List<GithubRepo>> userRepos(@Path("userId") String owner);

    }

    public static GithubApi create(String baseUrl){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubApi.class);
    }
}
