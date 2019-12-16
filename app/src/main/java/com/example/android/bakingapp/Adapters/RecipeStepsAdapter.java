package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.RecipeSteps;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsViewHolder> {

    private List<RecipeSteps> mRecipeStepsList;
    private OnItemClickListener listener;
    private TextView mRecipeStepTextView;
    private MaterialCardView mRecipeStepCardView;


    // interface
    public interface OnItemClickListener {
        void onItemClick(RecipeSteps recipeStep);
    }


    public RecipeStepsAdapter(List<RecipeSteps> recipeSteps,
                              OnItemClickListener listenerOnItemClickListener){
        mRecipeStepsList = recipeSteps;
        listener = listenerOnItemClickListener;
    }

    @NonNull
    @Override
    public RecipeStepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutIdForMovieItem = R.layout.recipe_step_item;

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForMovieItem, parent, false);

        return new RecipeStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeStepsViewHolder holder, int i) {
        holder.bind(mRecipeStepsList.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return mRecipeStepsList.size();
    }

    class RecipeStepsViewHolder extends RecyclerView.ViewHolder{

        RecipeStepsViewHolder(@NonNull View itemView) {

            super(itemView);
            mRecipeStepTextView = itemView.findViewById(R.id.recipe_step_tv);
            mRecipeStepCardView = itemView.findViewById(R.id.recipe_step_cv);
        }

        void bind(final RecipeSteps recipeStep, final OnItemClickListener listener){
            mRecipeStepTextView.setText(recipeStep.getNumberStep() +". " + recipeStep.getShortDescription());


            mRecipeStepCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(recipeStep);
                }
            });

        }
    }
}
