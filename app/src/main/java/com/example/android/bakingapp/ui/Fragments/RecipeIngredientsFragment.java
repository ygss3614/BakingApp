package com.example.android.bakingapp.ui.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;

import androidx.fragment.app.Fragment;

public class RecipeIngredientsFragment extends Fragment {

    public static final String RECIPE_INGREDIENTS = "recipe_ingredients";

    private TextView mRecipeIngredientsTextView;

    private String mIngredients;

    public RecipeIngredientsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);

        mRecipeIngredientsTextView = rootView.findViewById(R.id.ingredients_list_tv);

        if (savedInstanceState != null) {
            mIngredients = savedInstanceState.getString(RECIPE_INGREDIENTS);
        }

        mRecipeIngredientsTextView.setText(mIngredients);
        return rootView;
    }

    public void setIngredientsString(String ingredients){
        mIngredients = ingredients;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(RECIPE_INGREDIENTS, mIngredients);
    }


}
