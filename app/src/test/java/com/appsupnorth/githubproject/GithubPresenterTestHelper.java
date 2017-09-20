package com.appsupnorth.githubproject;

import com.appsupnorth.githubproject.service.response_entities.GithubRepo;
import com.appsupnorth.githubproject.service.response_entities.GithubUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mark.schumacher@scotiabank.com on 2017-09-20.
 */

public class GithubPresenterTestHelper {

    public static GithubUser mockUser() {
        GithubUser ghu = new GithubUser();
        ghu.avatar_url = "https://avatars0.githubusercontent.com/u/9395257?v=4";
        ghu.name = "mark";
        return ghu;
    }

    public static List<GithubRepo> mockRepos() {
        GithubRepo r1 = new GithubRepo();
        r1.description = "Android library for better Picker DialogFragments";
        r1.name = "android-shoes";
        r1.stargazers_count = 100;
        r1.forks = 0;
        r1.updated_at = "2016-03-24T18:25:56Z";
        List<GithubRepo> ghlist = new ArrayList<>();
        ghlist.add(r1);
        ghlist.add(r1);
        ghlist.add(r1);
        ghlist.add(r1);
        return ghlist;
    }


}



