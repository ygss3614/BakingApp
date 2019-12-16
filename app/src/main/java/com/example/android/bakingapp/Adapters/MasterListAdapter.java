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

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.MasterListViewHolder> {

    private List<RecipeSteps> mRecipeStepsList;
    private MasterListAdapter.OnItemClickListener listener;
    private TextView mRecipeShortDescription;
    private MaterialCardView mRecipeShortDescriptionCardView;


    // interface
    public interface OnItemClickListener {
        void onItemClick(int stepIndex);
    }


    public MasterListAdapter(List<RecipeSteps> recipeStepsList,
                             MasterListAdapter.OnItemClickListener listenerOnItemClickListener){
        mRecipeStepsList = recipeStepsList;
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
        holder.bind(i, listener);
    }

    @Override
    public int getItemCount() {
        return mRecipeStepsList.size();
    }

    class MasterListViewHolder extends RecyclerView.ViewHolder{

        MasterListViewHolder(@NonNull View itemView) {

            super(itemView);
            mRecipeShortDescription = itemView.findViewById(R.id.recipe_master_view_tv);
            mRecipeShortDescriptionCardView = itemView.findViewById(R.id.recipe_master_view_cv);

        }

        void bind(final int stepIndex, final MasterListAdapter.OnItemClickListener listener){
            mRecipeShortDescription.setText(mRecipeStepsList.get(stepIndex).getShortDescription());


            mRecipeShortDescriptionCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(stepIndex);
                }
            });

        }
    }
}
