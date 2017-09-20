package com.appsupnorth.githubproject;

import com.appsupnorth.githubproject.service.GithubService.GithubApi;
import com.appsupnorth.githubproject.service.response_entities.GithubRepo;
import com.appsupnorth.githubproject.service.response_entities.GithubUser;
import com.appsupnorth.githubproject.views.GithubView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by mark.schumacher@scotiabank.com on 2017-09-19.
 */
public class GithubPresenter {

    public static final String TAG = GithubPresenter.class.getSimpleName();

    GithubView view;
    GithubApi service;

    public GithubPresenter(GithubView view, GithubApi service) {
        this.view = view;
        this.service = service;
    }


    public void searchButtonClicked(String userId) {
        view.setViewsToInvisible();
        view.setSearchButtonClickable(false);
        view.hideKeyboard();
        service.userInfo(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<GithubUser>() {
                    @Override
                    public void call(GithubUser githubUser) {
                        view.showHeader(githubUser);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.setSearchButtonClickable(true);
                    }
                });
    }


    public void getRepositories(String userId) {
        service.userRepos(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<List<GithubRepo>>() {
                    @Override
                    public void call(List<GithubRepo> gitHubRepos) {
                        view.showRepositories(gitHubRepos);
                        view.setSearchButtonClickable(true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.setSearchButtonClickable(true);
                    }
                });
    }

    public void repositoryClicked(GithubRepo gitRepo) {
        view.showBottomSheet(gitRepo);
    }
}
