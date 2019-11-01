package com.example.rapido.modules.videoDetails;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.rapido.base.BaseViewModel;
import com.example.rapido.network.DataProvider;
import com.example.rapido.response.popularVideos.Example;
import com.example.rapido.response.popularVideos.Item;
import com.google.gson.Gson;

import java.util.List;

public class VideoViewModel extends BaseViewModel<VideoNavigator> {
    private MutableLiveData<List<Item>> item = new MutableLiveData<>();



    public MutableLiveData<List<Item>> getCurrentVideoDetail() {
        if(item==null){
            item=new MutableLiveData<>();
        }
        return item;
    }

    public void getVideoDetails(String videoID){
        mDisposable.add(DataProvider.getInstance().fetchVideoDetails(videoID,response -> {
            dialogVisibility.setValue(false);
            getCurrentVideoDetail().setValue(response);
            Log.d("RandomActTAG","gson: "+new Gson().toJson(response));
            mNavigator.onRequestSuccess(response.get(0));
        }, this::checkError));

    }

    @Override
    protected void checkError(Throwable throwable) {
        super.checkError(throwable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }



}



