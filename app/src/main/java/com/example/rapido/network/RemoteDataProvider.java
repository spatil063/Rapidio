package com.example.rapido.network;


import com.example.rapido.response.popularVideos.Item;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public interface RemoteDataProvider {

    Disposable fetchPopularVideos(Consumer<List<Item>> success, Consumer<Throwable> error);

    Disposable fetchVideoDetails(String videoID, Consumer<List<Item>> success, Consumer<Throwable> error);
}
