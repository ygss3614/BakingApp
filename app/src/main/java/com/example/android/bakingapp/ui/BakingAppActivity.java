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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;

public class BakingAppActivity extends AppCompatActivity {


    // TODO (1) Create a layout file that displays one body part image named fragment_body_part.xml
    // This layout should contain a single ImageView

    // TODO (2) Create a new class called BodyPartFragment to display an image of an Android-Me body part
    // In this class, you'll need to implement an empty constructor and the onCreateView method
    // TODO (3) Show the first image in the list of head images
    // Soon, you'll update this image display code to show any image you want

    private GridLayoutManager mLayoutManager;
    private RecyclerView mRecipeRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baking_app_activity);

        loadRecipes();

        // TODO (5) Create a new BodyPartFragment instance and display it using the FragmentManager
    }

    public void loadRecipes(){
        mRecipeRecyclerView = findViewById(R.id.recipes_rv);

        mLayoutManager = new GridLayoutManager(BakingAppActivity.this, 1);

        mRecipeRecyclerView.setLayoutManager(mLayoutManager);

        RecipeAdapter mAdapter = new RecipeAdapter(loadJsonData(), new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe recipe) {
                Context context = BakingAppActivity.this;
                if (recipe != null) {
//                    Intent intent = new Intent(context, MoviesDetailsActivity.class);
//                    intent.putExtra(MoviesDetailsActivity.EXTRA_MOVIE, movieDB);
//                    context.startActivity(intent);
                    Toast.makeText(context, "recipe object is not null", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "recipe object is null", Toast.LENGTH_LONG).show();
                }
            }
        });
        mRecipeRecyclerView.setAdapter(mAdapter);

    }

    public List<Recipe> loadJsonData()  {
        // FROM https://stackoverflow.com/questions/29965764/how-to-parse-json-file-with-gson/53278191
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
