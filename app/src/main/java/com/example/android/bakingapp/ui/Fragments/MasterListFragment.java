package com.example.android.bakingapp.ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Adapters.MasterListAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.RecipeSteps;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MasterListFragment extends Fragment {

    private List<RecipeSteps> mRecipeSteps;

    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnItemClickListener mCallback;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnItemClickListener {
        void onItemSelected(int stepIndex);
        void onIngredientsSelected();
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnItemClickListener");
        }
    }


    // Mandatory empty constructor
    public MasterListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        CardView ingredientsCardView = rootView.findViewById(R.id.recipe_ingredients_master_view_cv);

        ingredientsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onIngredientsSelected();
            }
        });

        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        RecyclerView recyclerViewSteps = rootView.findViewById(R.id.recipe_master_view_rv);
        recyclerViewSteps.setLayoutManager(mLayoutManager);

        MasterListAdapter mAdapter = new MasterListAdapter(mRecipeSteps,
                new MasterListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int stepIndex) {
                        mCallback.onItemSelected(stepIndex);
                    }
                });

        recyclerViewSteps.setAdapter(mAdapter);



        return rootView;
    }

    public void setmRecipeSteps(List<RecipeSteps> recipeSteps) {
        mRecipeSteps = recipeSteps;

    }

}

