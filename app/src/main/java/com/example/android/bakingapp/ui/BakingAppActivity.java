package com.example.android.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.Adapters.RecipeAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.utils.MyAsyncTaskLoader;
import com.example.android.bakingapp.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

public class BakingAppActivity extends AppCompatActivity {

    private GridLayoutManager mLayoutManager;
    private RecyclerView mRecipeRecyclerView;
    private TextView mConnectionError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baking_app_activity);

        mRecipeRecyclerView = findViewById(R.id.recipes_rv);
        mConnectionError = findViewById(R.id.connection_error_tv);

        mLayoutManager = new GridLayoutManager(BakingAppActivity.this, 1);

        mRecipeRecyclerView.setLayoutManager(mLayoutManager);

        makeRecipeSearch();
    }

    public void loadRecipes(List<Recipe> recipes){


        RecipeAdapter mAdapter = new RecipeAdapter(recipes, new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe recipe) {
                Context context = BakingAppActivity.this;
                if (recipe != null) {
                    Intent intent = new Intent(context, RecipeDetailActivity.class);
                    intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE, recipe);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "recipe object is null", Toast.LENGTH_LONG).show();
                }
            }
        });
        mRecipeRecyclerView.setAdapter(mAdapter);
    }

    public void makeRecipeSearch(){
        URL recipeURL = NetworkUtils.buildRecipeUrl();

        LoaderManager loaderManager = getSupportLoaderManager();

        new MyAsyncTaskLoader(
                new MyAsyncTaskLoader.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        Log.d("BAKING_APP_ACTIVITY", "LOAD RECIPE FROM INTERNET");
                        if( output != null){
                            mConnectionError.setVisibility(View.GONE);
                            List<Recipe> recipeList = loadJsonData(output);
                            loadRecipes(recipeList);
                        }else{
                            Log.d("BAKING_APP_ACTIVITY", "NO INTERNET");
                            mConnectionError.setVisibility(View.VISIBLE);
                            mConnectionError.setText(R.string.no_internet_connection);
                        }

                    }
                }, this
        ).startAsyncTaskLoader(recipeURL, loaderManager, MyAsyncTaskLoader.RECIPE_SEARCH_LOADER);

    }


    public List<Recipe> loadJsonData(String recipeJson)  {
        Gson gson = new Gson();
        List<Recipe> recipeList;
        Type RECIPE_TYPE = new TypeToken<List<Recipe>>() {}.getType();
        recipeList = gson.fromJson(recipeJson, RECIPE_TYPE);
        return recipeList;
    }



}
