package com.example.android.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class BakingAppActivity extends AppCompatActivity {

    private GridLayoutManager mLayoutManager;
    private RecyclerView mRecipeRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baking_app_activity);
        loadRecipes();
    }

    public void loadRecipes(){
        mRecipeRecyclerView = findViewById(R.id.recipes_rv);

        mLayoutManager = new GridLayoutManager(BakingAppActivity.this, 2);

        mRecipeRecyclerView.setLayoutManager(mLayoutManager);

        List<Recipe> mRecipeList = loadJsonData();

        RecipeAdapter mAdapter = new RecipeAdapter(mRecipeList, new RecipeAdapter.OnItemClickListener() {
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

    public List<Recipe> loadJsonData()  {

        Gson gson = new Gson();
        List<Recipe> recipeList = null;

        InputStream is = getResources().openRawResource(R.raw.recipes);

        Type RECIPE_TYPE = new TypeToken<List<Recipe>>() {}.getType();

        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            recipeList = gson.fromJson(reader, RECIPE_TYPE);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return recipeList;
    }

}
