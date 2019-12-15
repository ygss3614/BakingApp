package com.example.android.bakingapp.utils;

import android.content.Context;
import android.os.Bundle;

import java.io.IOException;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

public class MyAsyncTaskLoader extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String>{


    public static final String SEARCH_RECIPE_EXTRA = "query";
    public static final int RECIPE_SEARCH_LOADER = 22;
    public AsyncResponse delegate = null;
    public Context context;


    public MyAsyncTaskLoader(AsyncResponse delegate, Context context){
        this.delegate = delegate;
        this.context = context;

    }

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public void startAsyncTaskLoader(URL movieURL, LoaderManager loaderManager, int loaderID){
        Bundle urlBundle = new Bundle();
        urlBundle.putString(SEARCH_RECIPE_EXTRA, movieURL.toString());

        Loader<String> movieDbSearchLoader = loaderManager
                .getLoader(loaderID);

        if (movieDbSearchLoader == null){
            loaderManager.initLoader(loaderID, urlBundle, this);
        }else{
            loaderManager.restartLoader(loaderID, urlBundle, this);
        }
    }

    @Override
    public Loader<String> onCreateLoader(int i, final Bundle bundle) {

        return new AsyncTaskLoader<String>(this.context) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                if(bundle == null)
                    return;
                forceLoad();
            }

            @Override
            public String loadInBackground() {
                String searchResult = null;
                if (bundle.containsKey(SEARCH_RECIPE_EXTRA)){
                    String searchUrl = bundle.getString(SEARCH_RECIPE_EXTRA);

                    if (searchUrl == null){
                        return null;
                    }
                    try {
                        URL movieDbUrl = new URL(searchUrl);
                        searchResult = NetworkUtils.getResponseFromHttpUrl(movieDbUrl);
                        return searchResult;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                return searchResult;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String searchResult) {
        delegate.processFinish(searchResult);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

}

