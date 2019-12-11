package com.example.android.bakingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.RecipeIngredients;
import com.example.android.bakingapp.data.RecipeSteps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class RecipeIngredientsFragment extends Fragment {

    public static final String RECIPE_INGREDIENTS = "recipe_ingredients";

    private TextView mRecipeIngredientsTextView;

    private String mIngredients;

    public RecipeIngredientsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ingredient_fragment, container, false);

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
