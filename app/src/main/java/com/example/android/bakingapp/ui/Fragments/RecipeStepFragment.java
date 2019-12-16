package com.example.android.bakingapp.ui.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.RecipeSteps;
import com.example.android.bakingapp.ui.RecipeDetailActivity;
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
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RecipeStepFragment extends Fragment {

    public static final String RECIPE_STEP = "recipe_step";
    public static final String EXOPLAYER_LAST_POSITION = "exoplayer_last_position";
    public static final String  VIDEO_IS_READY = "video_ready";


    private static final String TAG = RecipeDetailActivity.class.getSimpleName();

    private RecipeSteps mRecipeStep;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private TextView stepShortDescription;
    private TextView stepDescription;
    private MaterialCardView mPlayerViewMaterialCard;
    private String mStepVideoUrl;
    private long mExoPlayerPosition = 0;
    private Boolean mVideoIsReady = false;


    public RecipeStepFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mRecipeStep = bundle.getParcelable(RECIPE_STEP);
        }

        mStepVideoUrl = mRecipeStep.getVideoURL();


        if (savedInstanceState != null) {
            mExoPlayerPosition = savedInstanceState.getLong(EXOPLAYER_LAST_POSITION, 0);
            mVideoIsReady = savedInstanceState.getBoolean(VIDEO_IS_READY, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        mPlayerView = rootView.findViewById(R.id.playerView);
        stepShortDescription = rootView.findViewById(R.id.step_short_description_tv);
        stepDescription = rootView.findViewById(R.id.step_description_tv);
        mPlayerViewMaterialCard = rootView.findViewById(R.id.player_view_mc);



        Log.d("FRAGMENT", "ON CREATE FRAGMENT");


        showCurrentRecipeStep();

        return rootView;
    }


    public void showCurrentRecipeStep() {
        // portrait mode has only recipe video
        if (stepShortDescription != null && stepDescription != null ){
            stepShortDescription.setText(mRecipeStep.getShortDescription());
            stepDescription.setText(mRecipeStep.getDescription());
        }

    }


    private void initializePlayer() {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

        }




        // Prepare the MediaSource.
        String userAgent = Util.getUserAgent(getContext(), "Baking Time App");
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(mStepVideoUrl),
                new DefaultDataSourceFactory(getContext(), userAgent),
                new DefaultExtractorsFactory(), null, null);

        mExoPlayer.prepare(mediaSource);
        mExoPlayer.seekTo(mExoPlayerPosition);
        mExoPlayer.setPlayWhenReady(mVideoIsReady);
    }

    @Override
    public void onPause() {
        if (mExoPlayer != null) {
            saveExoPlayerState();
            releaseExoPlayer();
        }

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mStepVideoUrl.length() != 0){
            mPlayerViewMaterialCard.setVisibility(View.VISIBLE);
            initializePlayer();
        }else{
            mPlayerViewMaterialCard.setVisibility(View.GONE);
        }
    }

    private void releaseExoPlayer() {
        mPlayerView.setPlayer(null);
        mExoPlayer.release();
        mExoPlayer = null;
    }

    private void saveExoPlayerState(){

        mExoPlayerPosition = mExoPlayer.getContentPosition();
        mVideoIsReady = mExoPlayer.getPlayWhenReady();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(EXOPLAYER_LAST_POSITION, mExoPlayerPosition);
        outState.putBoolean(VIDEO_IS_READY, mVideoIsReady);

    }


}
