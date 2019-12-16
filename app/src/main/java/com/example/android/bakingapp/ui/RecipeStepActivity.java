package com.example.android.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.RecipeSteps;
import com.example.android.bakingapp.ui.Fragments.RecipeStepFragment;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class RecipeStepActivity extends AppCompatActivity {

    public static final String EXTRA_STEP = "step";
    public static final RecipeSteps DEFAULT_STEP = new  RecipeSteps();

    private RecipeSteps mStep;
    private Fragment mFragmentStep;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_layout_step);



        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        mStep = Objects.requireNonNull(intent).getParcelableExtra(EXTRA_STEP);

        if (mStep == DEFAULT_STEP) {
            closeOnError();
            return;
        }
        getSupportActionBar().setTitle(mStep.getShortDescription());
        if (savedInstanceState != null) {
            mFragmentStep = getSupportFragmentManager().getFragment(savedInstanceState, "mediaFragment");
        } else {
            mFragmentStep = new RecipeStepFragment();
        }


        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeStepFragment.RECIPE_STEP, mStep);

        //Add to the mediaFragment object the bundle instance
        mFragmentStep.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_steps_container, mFragmentStep) //We don't want overlapping fragments
                .commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getSupportFragmentManager().putFragment(outState, "mediaFragment", mFragmentStep);
    }



    private void closeOnError() {
        finish();
    }
}
