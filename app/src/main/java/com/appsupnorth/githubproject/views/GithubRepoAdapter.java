package com.appsupnorth.githubproject.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsupnorth.githubproject.R;
import com.appsupnorth.githubproject.service.response_entities.GithubRepo;

import java.util.List;

/**
 * Created by mark.schumacher@scotiabank.com on 2017-09-19.
 */

public class GithubRepoAdapter extends RecyclerView.Adapter<GithubRepoAdapter.RepositoryViewHolder> {

    private List<GithubRepo> repoList;
    private RecyclerViewClickListener itemListener;

    public GithubRepoAdapter(List<GithubRepo> contactList, RecyclerViewClickListener itemListener) {
        this.repoList = contactList;
        this.itemListener = itemListener;
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder repoViewHolder, int i) {
        GithubRepo ci = repoList.get(i);
        repoViewHolder.repoTitle.setText(ci.name);
        repoViewHolder.repoDesc.setText(ci.description);
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.github_repository_card, viewGroup, false);

        return new RepositoryViewHolder(itemView);
    }

    class RepositoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView repoTitle;
        protected TextView repoDesc;

        RepositoryViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            repoTitle = (TextView) v.findViewById(R.id.repo_title);
            repoDesc = (TextView) v.findViewById(R.id.repo_description);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            itemListener.githubRepoClicked(repoList.get(pos));
        }
    }


    interface RecyclerViewClickListener {

        void githubRepoClicked(GithubRepo repo);
    }
}
