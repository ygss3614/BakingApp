package com.example.android.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.android.bakingapp.data.Recipe;

import androidx.annotation.Nullable;

public class BakingTimeService extends IntentService {

    public static final String ACTION_UPDATE_RECIPE =
            "com.example.android.bakingapp.action.update_recipe";
    public static final String EXTRA_RECIPE = "com.example.android.bakingapp.extra.RECIPE";


    public BakingTimeService() {
        super("BakingTimeService");
    }


    public static void startActionUpdateRecipe(Context context, Recipe recipe){
        Intent intent = new Intent(context, BakingTimeService.class);
        intent.setAction(ACTION_UPDATE_RECIPE);
        intent.putExtra(EXTRA_RECIPE, recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if  (intent != null){
            final String action = intent.getAction();
            if (ACTION_UPDATE_RECIPE.equals(action)){
                final Recipe recipe = intent.getParcelableExtra(EXTRA_RECIPE);
                handleActionUpdateRecipeWidgets(recipe);
            }
        }
    }

    private void handleActionUpdateRecipeWidgets(Recipe recipe){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetsIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingTimeWidget.class));
        BakingTimeWidget.updateBakingWidgets(this, appWidgetManager, recipe, appWidgetsIds);
    }
}
