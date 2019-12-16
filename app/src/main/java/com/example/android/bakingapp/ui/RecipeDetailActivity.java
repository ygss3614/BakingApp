package com.example.android.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.android.bakingapp.Adapters.RecipeStepsAdapter;
import com.example.android.bakingapp.BakingTimeService;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.RecipeIngredients;
import com.example.android.bakingapp.data.RecipeSteps;
import com.example.android.bakingapp.ui.Fragments.MasterListFragment;
import com.example.android.bakingapp.ui.Fragments.RecipeIngredientsFragment;
import com.example.android.bakingapp.ui.Fragments.RecipeStepFragment;

import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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
    private FragmentManager mFragmentManager;
    private RecyclerView mRecipeStepsRecyclerView;
    private GridLayoutManager mLayoutManager;


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


        mFragmentManager = getSupportFragmentManager();


        mIsPortrait = (findViewById(R.id.recipe_ingredients_container) != null);
        mTwoPanel = (findViewById(R.id.recipe_detail_ll) != null);
        mRecipeStepsRecyclerView = findViewById(R.id.recipe_steps_rv);

        if(savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelable(EXTRA_RECIPE);
            mWasPortraited = savedInstanceState.getBoolean(EXTRA_PORTRAIT);
        }


        // updates the action bar text to show the current recipe name
        getSupportActionBar().setTitle(mRecipe.getName());

        Log.d("FRAGMENT", "ON CREATE ACTIVITY");

        if (mTwoPanel){
            startFragmentsTwoPanel();
        }else{
            startFragmentsOnePanel();
        }

    }

    private void startFragmentsOnePanel(){
        Log.d("FRAGMENT ACTIVITY", "START ONE PANEL");

        if ( mIsPortrait && !mWasPortraited) {
            // Ingredients fragment
            RecipeIngredientsFragment recipeIngredientsFragment = new RecipeIngredientsFragment();
            recipeIngredientsFragment
                    .setIngredientsString(ingredientsToString(mRecipe.getIngredients()));
            mFragmentManager.beginTransaction()
                    .replace(R.id.recipe_ingredients_container, recipeIngredientsFragment)
                    .commit();
        }else{
            getSupportActionBar().hide();
        }


        mLayoutManager = new GridLayoutManager(RecipeDetailActivity.this, 1);
        mRecipeStepsRecyclerView.setLayoutManager(mLayoutManager);
        List<RecipeSteps> recipeSteps = mRecipe.getSteps();
        Context context = RecipeDetailActivity.this;

        RecipeStepsAdapter mAdapter = new RecipeStepsAdapter(recipeSteps, new RecipeStepsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecipeSteps recipeStep) {
            Intent intent = new Intent(RecipeDetailActivity.this, RecipeStepActivity.class);
            intent.putExtra(RecipeStepActivity.EXTRA_STEP, recipeStep);
            startActivity(intent);
            }
        });
        mRecipeStepsRecyclerView.setAdapter(mAdapter);
    }

    private void startFragmentsTwoPanel(){
        mRecipeIngredientsContainer = findViewById(R.id.recipe_ingredients_container);


        MasterListFragment masterListFragment = new MasterListFragment();
        masterListFragment
                .setmRecipeSteps(mRecipe.getSteps());

        mFragmentManager.beginTransaction()
                .replace(R.id.master_list_container, masterListFragment)
                .commit();


        RecipeIngredientsFragment recipeIngredientsFragment = new RecipeIngredientsFragment();
        recipeIngredientsFragment
                .setIngredientsString(ingredientsToString(mRecipe.getIngredients()));
        mFragmentManager.beginTransaction()
                .replace(R.id.recipe_ingredients_container, recipeIngredientsFragment)
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

        Log.d("FRAGMENT", "ON ITEM SELECTED");
        // if any step item is select, ingredients layout fragment must be hide
        mRecipeIngredientsContainer.setVisibility(View.GONE);


        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();

        RecipeSteps recipeStep = mRecipe.getSteps().get(stepIndex);
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeStepFragment.RECIPE_STEP, recipeStep);

        //Add to the stepFragment object the bundle instance
        recipeStepFragment.setArguments(bundle);


        mFragmentManager.beginTransaction()
                .replace(R.id.recipe_steps_container, recipeStepFragment)
                .commit();

    }

    @Override
    public void onIngredientsSelected() {
        // if recipe step is selected, then ingredients layout fragment must be visible again
        mRecipeIngredientsContainer.setVisibility(View.VISIBLE);


        RecipeIngredientsFragment recipeIngredientsFragment = new RecipeIngredientsFragment();
        recipeIngredientsFragment
                .setIngredientsString(ingredientsToString(mRecipe.getIngredients()));
        mFragmentManager.beginTransaction()
                .replace(R.id.recipe_ingredients_container, recipeIngredientsFragment)
                .commit();
    }
}
