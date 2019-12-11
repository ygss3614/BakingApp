package com.example.android.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeSteps implements Parcelable {

    private int id;
    private String shortDescription, description, videoURL, thumbnailURL;

    public RecipeSteps() {}

    public RecipeSteps(int id, String shortDescription, String description, String videoURL,
                String thumbnailURL ) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    protected RecipeSteps(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<RecipeSteps> CREATOR = new Creator<RecipeSteps>() {
        @Override
        public RecipeSteps createFromParcel(Parcel in) {
            return new RecipeSteps(in);
        }

        @Override
        public RecipeSteps[] newArray(int size) {
            return new RecipeSteps[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public int getId() {

        return id;
    }

    public String getShortDescription() {

        return shortDescription;
    }

    public String getDescription() {

        return description;
    }

    public String getVideoURL() {

        return videoURL;
    }

    public String getThumbnailURL() {

        return thumbnailURL;
    }


}
