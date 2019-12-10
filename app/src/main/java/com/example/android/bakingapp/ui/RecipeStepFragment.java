package com.example.android.bakingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.RecipeSteps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import androidx.fragment.app.Fragment;

public class RecipeStepFragment extends Fragment {

    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    private List<RecipeSteps> mRecipeSteps;
    private int mListIndex;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private TextView stepShortDescription;
    private TextView stepDescription;
    private ImageButton previousStepButton;
    private ImageButton nextStepButton;


    public RecipeStepFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.step_fragment, container, false);

        mPlayerView = rootView.findViewById(R.id.playerView);
        stepShortDescription = rootView.findViewById(R.id.step_short_description_tv);
        stepDescription = rootView.findViewById(R.id.step_description_tv);
        previousStepButton = rootView.findViewById(R.id.previous_step_bt);
        nextStepButton = rootView.findViewById(R.id.next_step_bt);

        stepShortDescription.setText(mRecipeSteps.get(mListIndex).getShortDescription());
        stepDescription.setText(mRecipeSteps.get(mListIndex).getDescription());
        String videoURL = mRecipeSteps.get(mListIndex).getVideoURL();
        initializePlayer(videoURL);

        // Set a click listener on the previous step button
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Increment position as long as the index remains <= the size of the image ids list
                if(mListIndex < mRecipeSteps.size()-1) {
                    mListIndex++;
                } else {
                    // The end of list has been reached, so return to beginning index
                    mListIndex = 0;
                }

                stepShortDescription.setText(mRecipeSteps.get(mListIndex).getShortDescription());
                stepDescription.setText(mRecipeSteps.get(mListIndex).getDescription());
                String stepVideoUrl = mRecipeSteps.get(mListIndex).getVideoURL();
                initializePlayer(stepVideoUrl);
            }
        });

        // Set a click listener on the next step button
        previousStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increment position as long as the index remains <= the size of the image ids list
                if(mListIndex > 0) {
                    mListIndex--;
                } else {
                    // The begin of list has been reached, so return to the end
                    mListIndex = mRecipeSteps.size()-1;
                }
                // Get recipe video url
                stepShortDescription.setText(mRecipeSteps.get(mListIndex).getShortDescription());
                stepDescription.setText(mRecipeSteps.get(mListIndex).getDescription());
                String stepVideoUrl = mRecipeSteps.get(mListIndex).getVideoURL();
                initializePlayer(stepVideoUrl);
            }
        });

        return rootView;
    }

    public void setmRecipeSteps(List<RecipeSteps> recipeSteps ){
        mRecipeSteps = recipeSteps;

    }
    public void setmListIndex(int index) {
        mListIndex = index;
    }

    private void initializePlayer(String stepVideoURL) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "Baking Time App");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(stepVideoURL),
            new DefaultDataSourceFactory(getContext(), userAgent),
            new DefaultExtractorsFactory(), null, null);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        } else {
            String userAgent = Util.getUserAgent(getContext(), "Baking Time App");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(stepVideoURL),
                    new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(), null, null);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }



    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            Log.d("PREVIOUS", "PREVIOUS");
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {

            Log.d("PREVIOUS", "PREVIOUS");
            mExoPlayer.setPlayWhenReady(false);
        }
    }



}
