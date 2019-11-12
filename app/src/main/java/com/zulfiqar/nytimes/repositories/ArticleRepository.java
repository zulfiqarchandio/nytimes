package com.zulfiqar.nytimes.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.widget.Toast;

import com.zulfiqar.nytimes.model.Article;
import com.zulfiqar.nytimes.rest.ApiClient;
import com.zulfiqar.nytimes.utils.Constants;
import com.zulfiqar.nytimes.utils.JSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {

    private static ArticleRepository instance;
    private ArrayList<Article> dataSet = new ArrayList<>();
    ApiClient api;
    Context context;

    public static ArticleRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ArticleRepository(context);

        }
        return instance;
    }

    public ArticleRepository(Context context) {
        this.context = context;
        api = ApiClient.getInstance(context);
    }

    // Pretend to get data from a webservice or online source
    public MutableLiveData<List<Article>> getArticles() {

        final MutableLiveData<List<Article>> data = new MutableLiveData<>();

        String url = ApiClient.BASE_URL + ApiClient.PATH_URL + ApiClient.Sections + "/7" + ".json?api-key=" + Constants.api_key;


        api.getUrlRequest(url, api.new GetCallback() {
            @Override
            public void onResponseReceived(boolean success, JSONObject response) {
                if (success) {


                    try {


                        if (JSON.getStringValue(response, "status").equals("OK")) {

                            JSONArray array = JSON.getJSONArray(response, "results");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                Article article = new Article(obj);
                                dataSet.add(article);

                            }

                            data.setValue(dataSet);

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return data;
    }

}
