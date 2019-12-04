package com.example.android.bakingapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Recipe;

import java.text.BreakIterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.android.bakingapp.ui.RecipeAdapter.RecipeViewHolder.*;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipeList;
    private OnItemClickListener listener;
    private TextView recipeName;


    // interface
    public interface OnItemClickListener {
        void onItemClick(Recipe recipe);
    }


    public RecipeAdapter(List<Recipe> recipeList,
                         OnItemClickListener listenerOnItemClickListener){
        mRecipeList = recipeList;
        listener = listenerOnItemClickListener;

    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutIdForMovieItem = R.layout.recipe_item;

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForMovieItem, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int i) {
        holder.bind(mRecipeList.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder{

        RecipeViewHolder(@NonNull View itemView) {

            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name_tv);

        }

        void bind(final Recipe recipe, final OnItemClickListener listener){
            recipeName.setText(recipe.getName());
//            recipeName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onItemClick(recipe);
//                }
//            });

        }
    }
}
