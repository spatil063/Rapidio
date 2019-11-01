package com.example.rapido.modules.mostPopular;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rapido.R;
import com.example.rapido.base.BaseActivity;
import com.example.rapido.base.BaseNavigator;
import com.example.rapido.base.GenericAdapter;
import com.example.rapido.databinding.ActivityMostPopularBinding;
import com.example.rapido.databinding.VideoListBinding;
import com.example.rapido.modules.videoDetails.VideoActivity;
import com.example.rapido.response.popularVideos.Item;
import com.example.rapido.utils.Constants;
import com.example.rapido.utils.Utils;


public class MostPopularActivity extends BaseActivity<ActivityMostPopularBinding, MostPopularViewModel> implements MostPopularNavigator {

    private static final String TAG = "fetchActTAG";
    private Context mContext;
    Animation animation;


    @Override
    public int getLayoutId() {
        return R.layout.activity_most_popular;
    }

    @Override
    public Class getViewModel() {
        return MostPopularViewModel.class;
    }

    @Override
    public BaseNavigator getNavigatorReference() {
        return MostPopularActivity.this;
    }

    @Override
    public void onBinding() {
        mContext = this;
        mBinding.setViewModel(mViewModel);

        if (getIntent().getExtras() != null) {
            String data = getIntent().getExtras().getString(Constants.ACCESS_TOKEN);
            Log.d("Token", data);

        }
        animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_slide_in_from_right_exit);
        setUpListRecyclerView();
        mViewModel.getPopularListCall();

    }

    @Override
    public void onDecline() {

    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onError(String message) {
        Utils.getInstance().showMessage(mContext, message);

    }

    @Override
    public void onNoInternet() {

    }

    private void setUpListRecyclerView() {
        mBinding.mpopularList.setLayoutManager(new LinearLayoutManager(mContext));
        GenericAdapter<Item, VideoListBinding> adapter = new GenericAdapter<Item, VideoListBinding>(mContext) {
            @Override
            public int getLayoutId() {
                return R.layout.video_list;
            }

            @Override
            public void onBindView(VideoListBinding binding, Item item, int position) {
                binding.setModel(item);
                Glide.with(mContext).load(item.getSnippet().getThumbnails().getHigh().getUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(1600, 1600)
                        .into(binding.ivThumbnail);

                binding.ivThumbnail.setOnClickListener(view -> {
                    launchActivity(position, item);

                });


            }
        };
        mBinding.mpopularList.setAdapter(adapter);
        mViewModel.getPopularList().observe(this, adapter::updateData);


    }

    private void launchActivity(int position, Item item) {
        Intent mIntent = new Intent(MostPopularActivity.this, VideoActivity.class);
        mIntent.putExtra(Constants.VIDEO_ID, item.getId());
        startActivity(mIntent);
    }



}
