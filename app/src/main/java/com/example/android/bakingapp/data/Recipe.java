package com.example.android.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {

    private int id;
    private String name;
    private List<RecipeIngredients> ingredients = new ArrayList<>();
    private List<RecipeSteps> steps = new ArrayList<>();

    public Recipe() {}

    public Recipe(int id, String name, List<RecipeIngredients> ingredients){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }

    public Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        in.readTypedList(ingredients , RecipeIngredients.CREATOR);
        in.readTypedList(steps , RecipeSteps.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(steps);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) { return new Recipe(in); }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(List<RecipeIngredients> ingredients) {
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<RecipeIngredients> getIngredients() {
        return ingredients;
    }

    public List<RecipeSteps> getSteps() {
        return steps;
    }

    public void setSteps(List<RecipeSteps> steps) {
        this.steps = steps;
    }


}
