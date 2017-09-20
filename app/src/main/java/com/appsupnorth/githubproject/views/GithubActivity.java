package com.appsupnorth.githubproject.views;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsupnorth.githubproject.GithubPresenter;
import com.appsupnorth.githubproject.R;
import com.appsupnorth.githubproject.views.GithubRepoAdapter.RecyclerViewClickListener;
import com.appsupnorth.githubproject.service.GithubService;
import com.appsupnorth.githubproject.service.response_entities.GithubRepo;
import com.appsupnorth.githubproject.service.response_entities.GithubUser;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GithubActivity extends AppCompatActivity implements GithubView {

    ViewGroup mainLayout;
    ViewGroup githubHeaderLayout;
    Button searchButton;
    EditText githubSearchView;
    RecyclerView repoRecyclerView;
    ImageView profilePic;
    TextView profileName;

    GithubPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (ViewGroup) findViewById(R.id.activity_main);
        githubHeaderLayout  = (ViewGroup) findViewById(R.id.github_header_layout);
        searchButton        = (Button) findViewById(R.id.search_button);
        githubSearchView    = (EditText) findViewById(R.id.input_github_user);
        repoRecyclerView    = (RecyclerView) findViewById(R.id.repository_recycler_view);
        profilePic          = (ImageView) findViewById(R.id.header_profilepic);
        profileName         = (TextView) findViewById(R.id.header_username);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gitHubId = githubSearchView.getText().toString();
                presenter.searchButtonClicked(gitHubId);
            }
        });
        presenter = new GithubPresenter(this, GithubService.create(GithubService.ENDPOINT));
    }


    @Override
    public void showRepositories(List<GithubRepo> listOfRepos) {
        repoRecyclerView.setVisibility(View.INVISIBLE);
        repoRecyclerView.setAdapter(new GithubRepoAdapter(listOfRepos, new RecyclerViewClickListener() {
            @Override
            public void githubRepoClicked(GithubRepo repo) {
                presenter.repositoryClicked(repo);
            }
        }));
        Animation animslideup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        repoRecyclerView.startAnimation(animslideup);
    }

    @Override
    public void setSearchButtonClickable(Boolean isEnabled) {
        searchButton.setClickable(isEnabled);
    }

    @Override
    public void showHeader(final GithubUser githubUser) {
        Picasso.with(this).load(githubUser.avatar_url).noPlaceholder().into(profilePic, new Callback() {

            @Override
            public void onSuccess() {
                profileName.setText(githubUser.name);
                profilePic.setVisibility(View.VISIBLE);
                profileName.setVisibility(View.VISIBLE);
                Animation animslideup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                githubHeaderLayout.startAnimation(animslideup);
                presenter.getRepositories(githubSearchView.getText().toString());
            }

            @Override
            public void onError() {}
        });
    }

    public void showBottomSheet(GithubRepo repo) {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.github_repository_bottomsheet, null);
        ((TextView) sheetView.findViewById(R.id.last_updated)).setText(repo.updated_at);
        ((TextView) sheetView.findViewById(R.id.stars)).setText(String.valueOf(repo.stargazers_count));
        ((TextView) sheetView.findViewById(R.id.forks)).setText(String.valueOf(repo.forks));
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
    }

    public void setViewsToInvisible() {
        repoRecyclerView.setVisibility(View.INVISIBLE);
        profilePic.setVisibility(View.INVISIBLE);
        profileName.setVisibility(View.INVISIBLE);
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
    }

}
