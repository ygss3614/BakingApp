package com.example.android.bakingapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.RecipeSteps;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.MasterListViewHolder> {

    private List<Recipe> mRecipeList;
    private RecipeAdapter.OnItemClickListener listener;
    private TextView mRecipeNameTextView;
    private TextView mRecipeServingCountTextView ;
    private MaterialCardView mRecipeNameCardView;


    // interface
    public interface OnItemClickListener {
        void onItemClick(RecipeSteps recipeSteps);
    }


    public MasterListAdapter(List<Recipe> recipeList,
                         RecipeAdapter.OnItemClickListener listenerOnItemClickListener){
        mRecipeList = recipeList;
        listener = listenerOnItemClickListener;
    }

    @NonNull
    @Override
    public MasterListAdapter.MasterListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutIdForMovieItem = R.layout.recipe_master_list_item;

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForMovieItem, parent, false);

        return new MasterListAdapter.MasterListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MasterListAdapter.MasterListViewHolder holder, int i) {
        holder.bind(mRecipeList.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    class MasterListViewHolder extends RecyclerView.ViewHolder{

        MasterListViewHolder(@NonNull View itemView) {

            super(itemView);
            mRecipeNameTextView = itemView.findViewById(R.id.recipe_name_tv);
            mRecipeNameCardView = itemView.findViewById(R.id.recipe_name_cv);
            mRecipeServingCountTextView = itemView.findViewById(R.id.recipe_serving_count_tv);
        }

        void bind(final Recipe recipe, final RecipeAdapter.OnItemClickListener listener){
            mRecipeNameTextView.setText(recipe.getName());
            mRecipeServingCountTextView.setText(Integer.toString(recipe.getServings()));

            mRecipeNameCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(recipe);
                }
            });

        }
    }
}
