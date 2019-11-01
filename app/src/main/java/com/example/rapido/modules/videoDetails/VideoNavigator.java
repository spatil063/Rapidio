package com.example.rapido.modules.videoDetails;

import com.example.rapido.base.BaseNavigator;
import com.example.rapido.response.popularVideos.Item;

public interface VideoNavigator extends BaseNavigator {

    void onRequestSuccess(Item item);
}
