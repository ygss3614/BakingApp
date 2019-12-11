package com.example.android.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.RecipeIngredients;

import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


public class RecipeDetailActivity extends AppCompatActivity {

    // Final Strings to store state information about the list of recipes and list index
    public static final String LIST_INDEX = "list_index";

    public static final String EXTRA_RECIPE = "recipe";
    public static final String EXTRA_PORTRAIT = "is_portrait";
    private static final Recipe DEFAULT_RECIPE = new Recipe();

    private Recipe mRecipe;
    private Boolean mIsPortrait;
    private Boolean mWasPortraited;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_activity);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        mRecipe = Objects.requireNonNull(intent).getParcelableExtra(EXTRA_RECIPE);

        if (mRecipe == DEFAULT_RECIPE) {
            closeOnError();
            return;
        }

        // updates the action bar text to show the current recipe name
        getSupportActionBar().setTitle(mRecipe.getName());


        mIsPortrait = (findViewById(R.id.recipe_ingredients_container) != null);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            if (mIsPortrait) {
                // Ingredients fragment
                RecipeIngredientsFragment recipeIngredientsFragment = new RecipeIngredientsFragment();
                recipeIngredientsFragment
                        .setIngredientsString(ingredientstoString(mRecipe.getIngredients()));
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_ingredients_container, recipeIngredientsFragment)
                        .commit();
            }


            // Step fragment
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            recipeStepFragment.setmRecipeSteps(mRecipe.getSteps());
            recipeStepFragment.setmListIndex(0);

            fragmentManager.beginTransaction()
                    .add(R.id.recipe_steps_container, recipeStepFragment)
                    .commit();

        }else{
            mRecipe = savedInstanceState.getParcelable(EXTRA_RECIPE);
            mWasPortraited = savedInstanceState.getBoolean(EXTRA_PORTRAIT);
            if(!mWasPortraited && (findViewById(R.id.recipe_ingredients_container) != null)){
                FragmentManager fragmentManager = getSupportFragmentManager();
                RecipeIngredientsFragment recipeIngredientsFragment = new RecipeIngredientsFragment();
                recipeIngredientsFragment
                        .setIngredientsString(ingredientstoString(mRecipe.getIngredients()));
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_ingredients_container, recipeIngredientsFragment)
                        .commit();

            }

        }

    }

    private String ingredientstoString (List<RecipeIngredients> ingredients){

        String ingredientsListString = "";
        for(int i = 0; i < ingredients.size(); i++){
            RecipeIngredients ingredient = ingredients.get(i);
            ingredientsListString += String.format("%s %s %s\n",
                    ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient());
        }
        return ingredientsListString;
    }

    private void closeOnError() {
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_RECIPE, mRecipe);
        outState.putBoolean(EXTRA_PORTRAIT, mIsPortrait);

    }


}
