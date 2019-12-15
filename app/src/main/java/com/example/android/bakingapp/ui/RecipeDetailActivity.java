package com.example.android.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.android.bakingapp.BakingTimeService;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.RecipeIngredients;
import com.example.android.bakingapp.data.RecipeSteps;

import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


public class RecipeDetailActivity extends AppCompatActivity implements MasterListFragment.OnItemClickListener {

    // Final Strings to store state information about the list of recipes and list index
    public static final String LIST_INDEX = "list_index";

    public static final String EXTRA_RECIPE = "recipe";
    public static final String EXTRA_PORTRAIT = "is_portrait";
    private static final Recipe DEFAULT_RECIPE = new Recipe();

    private Recipe mRecipe;
    private Boolean mIsPortrait = false;
    private Boolean mWasPortraited = false;
    private Boolean mTwoPanel = false;
    private FrameLayout mRecipeIngredientsContainer;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_activity);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        mRecipe = Objects.requireNonNull(intent).getParcelableExtra(EXTRA_RECIPE);

        BakingTimeService.startActionUpdateRecipe(this, mRecipe);


        if (mRecipe == DEFAULT_RECIPE) {
            closeOnError();
            return;
        }

        // updates the action bar text to show the current recipe name
        getSupportActionBar().setTitle(mRecipe.getName());


        mIsPortrait = (findViewById(R.id.recipe_ingredients_container) != null);
        mTwoPanel = (findViewById(R.id.recipe_detail_ll) != null);

        if(savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelable(EXTRA_RECIPE);
            mWasPortraited = savedInstanceState.getBoolean(EXTRA_PORTRAIT);
        }


        if (mTwoPanel){
            startFragmentsTwoPanel();
        }else{
            startFragmentsOnePanel();
        }

    }

    private void startFragmentsOnePanel(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        if ( mIsPortrait && !mWasPortraited) {


            // Ingredients fragment
            RecipeIngredientsFragment recipeIngredientsFragment = new RecipeIngredientsFragment();
            recipeIngredientsFragment
                    .setIngredientsString(ingredientsToString(mRecipe.getIngredients()));
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_ingredients_container, recipeIngredientsFragment)
                    .commit();
        }else{
            getSupportActionBar().hide();
        }

        // Step fragment
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setmRecipeSteps(mRecipe.getSteps());
        recipeStepFragment.setmListIndex(0);

        fragmentManager.beginTransaction()
                .add(R.id.recipe_steps_container, recipeStepFragment)
                .commit();


    }

    private void startFragmentsTwoPanel(){
        mRecipeIngredientsContainer = findViewById(R.id.recipe_ingredients_container);
        FragmentManager fragmentManager = getSupportFragmentManager();

        MasterListFragment masterListFragment = new MasterListFragment();
        masterListFragment
                .setmRecipeSteps(mRecipe.getSteps());

        fragmentManager.beginTransaction()
                .add(R.id.master_list_container, masterListFragment)
                .commit();


        RecipeIngredientsFragment recipeIngredientsFragment = new RecipeIngredientsFragment();
        recipeIngredientsFragment
                .setIngredientsString(ingredientsToString(mRecipe.getIngredients()));
        fragmentManager.beginTransaction()
                .add(R.id.recipe_ingredients_container, recipeIngredientsFragment)
                .commit();

    }
    private String ingredientsToString (List<RecipeIngredients> ingredients){

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


    @Override
    public void onItemSelected(int stepIndex) {

        // if any step item is select, ingredients layout fragment must be hide
        mRecipeIngredientsContainer.setVisibility(View.GONE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setmRecipeSteps(mRecipe.getSteps());
        recipeStepFragment.setmListIndex(stepIndex);

        fragmentManager.beginTransaction()
                .add(R.id.recipe_steps_container, recipeStepFragment)
                .commit();

    }

    @Override
    public void onIngredientsSelected() {
        // if recipe step is selected, then ingredients layout fragment must be visible again
        mRecipeIngredientsContainer.setVisibility(View.VISIBLE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeIngredientsFragment recipeIngredientsFragment = new RecipeIngredientsFragment();
        recipeIngredientsFragment
                .setIngredientsString(ingredientsToString(mRecipe.getIngredients()));
        fragmentManager.beginTransaction()
                .add(R.id.recipe_ingredients_container, recipeIngredientsFragment)
                .commit();
    }
}
