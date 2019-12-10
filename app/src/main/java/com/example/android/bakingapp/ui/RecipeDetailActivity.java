package com.example.android.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.RecipeIngredients;

import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeDetailActivity extends AppCompatActivity {
    public static final String EXTRA_RECIPE = "recipe";
    private static final Recipe DEFAULT_RECIPE = new Recipe();

    private Recipe mRecipe;
    private TextView mRecipeIngredientsTextView;
    private RecyclerView mRecipeStepsRecyclerView;
    private GridLayoutManager mLayoutManager;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_activity);

        Intent intent = getIntent();
        if (intent == null) { closeOnError(); }
        mRecipe = Objects.requireNonNull(intent).getParcelableExtra(EXTRA_RECIPE);
        if (mRecipe == DEFAULT_RECIPE) {
            closeOnError();
            return;
        }

        loadRecipeDetails(mRecipe);

        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setmRecipeSteps(mRecipe.getSteps());
        recipeStepFragment.setmListIndex(0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_steps_container, recipeStepFragment)
                .commit();
    }

    public void loadRecipeDetails( Recipe recipe) {
        // updates the action bar text to show the current recipe name
        getSupportActionBar().setTitle(recipe.getName());

        // initializes ingredients cards
        mRecipeIngredientsTextView = findViewById(R.id.ingredients_list_tv);
        mRecipeIngredientsTextView.setText(ingredientstoString(recipe.getIngredients()));

        // iniatilize steps cards list
//        mRecipeStepsRecyclerView = findViewById(R.id.recipe_steps_rv);
//        mLayoutManager = new GridLayoutManager(RecipeDetailActivity.this, 1);
//        mRecipeStepsRecyclerView.setLayoutManager(mLayoutManager);
//        List<RecipeSteps> mRecipeSteps = recipe.getSteps();
//        RecipeDetailAdapter mAdapter = new RecipeDetailAdapter( mRecipeSteps,
//                new RecipeDetailAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(RecipeSteps recipeStep) {
//                Context context = RecipeDetailActivity.this;
//                if (recipeStep != null) {
//                    Intent intent = new Intent(context, RecipeDetailActivity.class);
//                    intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE, recipeStep);
//                    context.startActivity(intent);
//                } else {
//                    Toast.makeText(context, "recipe object is null", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//        mRecipeStepsRecyclerView.setAdapter(mAdapter);
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


}
