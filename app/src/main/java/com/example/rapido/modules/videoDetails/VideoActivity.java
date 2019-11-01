package com.example.rapido.modules.videoDetails;

import android.app.PictureInPictureParams;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Rational;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.example.rapido.R;
import com.example.rapido.base.BaseActivity;
import com.example.rapido.base.BaseNavigator;
import com.example.rapido.databinding.ActivityVideoBinding;
import com.example.rapido.response.popularVideos.Item;
import com.example.rapido.utils.Constants;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class VideoActivity extends BaseActivity<ActivityVideoBinding, VideoViewModel> implements YouTubePlayer.OnInitializedListener, VideoNavigator {


    private static final int RECOVERY_REQUEST = 1;
    String TAG = "VideoActivity";
    private String mVideoId;
    YouTubePlayerFragment youTubePlayerFragment;


    @Override
    public int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    public Class getViewModel() {
        return VideoViewModel.class;
    }

    @Override
    public BaseNavigator getNavigatorReference() {
        return VideoActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(mContext, "Video play.....", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBinding() {
        mContext = this;
        mBinding.setViewModel(mViewModel);
        if (getIntent().getExtras() != null) {
            mVideoId = getIntent().getExtras().getString(Constants.VIDEO_ID);
        }


        youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtubeview);
        youTubePlayerFragment.initialize(Constants.API_KEY, this);

        mViewModel.getVideoDetails(mVideoId);



    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (mVideoId != null && !b) {
            youTubePlayer.cueVideo(mVideoId);
        }

    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            Toast.makeText(this, "Error Intializing Youtube Player", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECOVERY_REQUEST) {
            getYouTubePlayerProvider().initialize(Constants.API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubePlayerFragment;
    }


    @Override
    public void onError(String message) {

    }

    @Override
    public void onNoInternet() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void enterPiP() {
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            //Trigger PiP mode
            try {
                Rational rational = new Rational(mBinding.framePlayer.getWidth(), mBinding.framePlayer.getHeight());

                PictureInPictureParams mParams =
                        new PictureInPictureParams.Builder()
                                .setAspectRatio(rational)
                                .build();

                enterPictureInPictureMode(mParams);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(VideoActivity.this, "API 26 needed to perform PiP", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestSuccess(Item item) {
        mBinding.mVideoTitile.setText(item.getSnippet().getTitle());
        mBinding.mVideoDescription.setText(item.getSnippet().getDescription());
    }
}
