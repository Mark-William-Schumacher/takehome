package com.appsupnorth.githubproject.views;

import com.appsupnorth.githubproject.service.response_entities.GithubRepo;
import com.appsupnorth.githubproject.service.response_entities.GithubUser;

import java.util.List;

/**
 * Created by mark.schumacher@scotiabank.com on 2017-09-19.
 */

public interface GithubView {

    void showHeader(GithubUser user);

    void showRepositories(List<GithubRepo> listOfRepos);

    void hideKeyboard();

    void setSearchButtonClickable(Boolean isEnabled);

    void setViewsToInvisible();

    void showBottomSheet(GithubRepo repo);


}
