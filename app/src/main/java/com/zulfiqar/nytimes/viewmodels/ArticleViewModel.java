package com.zulfiqar.nytimes.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zulfiqar.nytimes.model.Article;
import com.zulfiqar.nytimes.repositories.ArticleRepository;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {

    private MutableLiveData<List<Article>> mArticles;
    private ArticleRepository mRepo;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        if (mArticles != null) {
            return;
        }
        mRepo = ArticleRepository.getInstance(getApplication().getApplicationContext());
        mArticles = mRepo.getArticles();
    }

    public LiveData<List<Article>> getArticles() {
        return mArticles;
    }
}
