package com.example.android.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.RecipeIngredients;
import com.example.android.bakingapp.data.RecipeSteps;
import com.example.android.bakingapp.ui.BakingAppActivity;
import com.example.android.bakingapp.ui.RecipeDetailActivity;
import com.example.android.bakingapp.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingTimeWidget extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                Recipe recipe, int appWidgetId) {

        // set the click handler to open the correct activity
        Intent intent;
        if(recipe == null){
            intent = new Intent(context, BakingAppActivity.class);
        }else{
            intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE, recipe);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 , intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_time_widget);

        // Update recipe name and ingredients
        if (recipe == null){
            views.setTextViewText(R.id.widget_recipe_name_tv, "No recipe selected");
            views.setTextViewText(R.id.widget_recipe_ingredients_tv, "Tap to select");
        }else{
            views.setTextViewText(R.id.widget_recipe_name_tv, recipe.getName());
            views.setTextViewText(R.id.widget_recipe_ingredients_tv, ingredientstoString(recipe.getIngredients()));
        }


        views.setOnClickPendingIntent(R.id.widget_recipe_name_tv, pendingIntent);
        views.setOnClickPendingIntent(R.id.widget_recipe_ingredients_tv, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager,
                                           Recipe recipe, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipe, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, null, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static String ingredientstoString(List<RecipeIngredients> ingredients){

        String ingredientsListString = "";
        for(int i = 0; i < ingredients.size(); i++){
            RecipeIngredients ingredient = ingredients.get(i);
            ingredientsListString += String.format("%s %s %s\n",
                    ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient());
        }
        return ingredientsListString;
    }
}

