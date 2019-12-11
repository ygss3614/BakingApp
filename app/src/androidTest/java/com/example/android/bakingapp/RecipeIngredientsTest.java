package com.example.android.bakingapp;

import android.content.Intent;

import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.RecipeIngredients;
import com.example.android.bakingapp.data.RecipeSteps;
import com.example.android.bakingapp.ui.BakingAppActivity;
import com.example.android.bakingapp.ui.RecipeDetailActivity;
import com.example.android.bakingapp.utils.BakingAppUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeIngredientsTest {
    public static final String RECIPE_INGREDIENTS = "Ingredients";

    @Rule
    public ActivityTestRule<RecipeDetailActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipeDetailActivity.class,
                    true, false);

    @Test
    public void checkIngredientsCard(){
        Intent intent = new Intent();
        List<RecipeIngredients> ingredients = new ArrayList<>();
        List<RecipeSteps> steps = new ArrayList<>();
        ingredients.add(new RecipeIngredients(1,"cups","sugar"));
        steps.add(new RecipeSteps(0, "Recipe Introduction",
                "Recipe Introduction",
                "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4", ""));
        Recipe recipe = new Recipe(1,"pinaple pie", ingredients, steps);

        intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE, recipe);

        mActivityTestRule.launchActivity(intent);
        onView((withId(R.id.ingredients_label_tv))).
                check(matches(withText(RECIPE_INGREDIENTS)));
    }

}
