package com.zulfiqar.nytimes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zulfiqar.nytimes.R;
import com.zulfiqar.nytimes.adapter.ArticleAdapter;
import com.zulfiqar.nytimes.model.Article;
import com.zulfiqar.nytimes.utils.RecyclerTouchListener;
import com.zulfiqar.nytimes.viewmodels.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView articlesRecyclerView;
    private ArticleAdapter adapter;
    private ArticleViewModel articleViewModel;
    List<Article> articles = new ArrayList<Article>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        articlesRecyclerView = (RecyclerView) findViewById(R.id.articlesRecyclerView);
        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        articleViewModel.init();

        articleViewModel.getArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> mArticles) {
                adapter.setData(mArticles);
                articles = mArticles;

            }
        });

        initRecyclerView();

        articlesRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), articlesRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Article article = articles.get(position);
                //Toast.makeText(getApplicationContext(), article.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this, ArticleDetailActivity.class);
                i.putExtra("title", article.getTitle());
                i.putExtra("byline", article.getByline());
                i.putExtra("published_date", article.getPublished_date());
                i.putExtra("abstractData", article.getAbstractData());
                i.putExtra("image", article.getImage());
                startActivity(i);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    private void initRecyclerView() {
        adapter = new ArticleAdapter(articleViewModel.getArticles().getValue());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        articlesRecyclerView.setLayoutManager(linearLayoutManager);
        articlesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        articlesRecyclerView.setAdapter(adapter);
    }


}
