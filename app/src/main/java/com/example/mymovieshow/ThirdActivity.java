package com.example.mymovieshow;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mymovieshow.databinding.ActivityThirdBinding;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;






public class ThirdActivity extends AppCompatActivity {
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    private ActivityThirdBinding binding;
    private View view;
    private YouTubePlayerFragment youtubeFragment;
    private static final String TAG = "ThirdActivity";
    private boolean aBoolean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThirdBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();

        String s = getIntent().getExtras().getString("key");
        youtubeFragment = (YouTubePlayerFragment)
                getFragmentManager().findFragmentById(R.id.youtube_fragmentt);
        youtubeFragment.initialize("AIzaSyBM88KR3ZES2GCg2IQ3zypEOWegdfuFuA4", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                //load video
                youTubePlayer.loadVideo(s);
                //start video
                youTubePlayer.setFullscreen(true);
                youTubePlayer.setShowFullscreenButton(false);

                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(ThirdActivity.this, "Initialization Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}



