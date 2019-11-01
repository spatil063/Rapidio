package com.example.rapido.modules.mostPopular;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.rapido.base.BaseViewModel;
import com.example.rapido.network.DataProvider;
import com.example.rapido.response.popularVideos.Item;
import com.google.gson.Gson;

import java.util.List;

public class MostPopularViewModel extends BaseViewModel<MostPopularNavigator> {

    private MutableLiveData<List<Item>> userLists = new MutableLiveData<>();




    public MutableLiveData<List<Item>> getPopularList() {
        if(userLists==null){
            userLists=new MutableLiveData<>();
        }
        return userLists;
    }



    public void getPopularListCall(){

        mDisposable.add(DataProvider.getInstance().fetchPopularVideos(response -> {
            dialogVisibility.setValue(false);
            getPopularList().setValue(response);
            Log.d("RandomActTAG","gson: "+new Gson().toJson(response));
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
