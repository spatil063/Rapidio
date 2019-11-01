package com.example.rapido.network;


import com.example.rapido.BaseApp;
import com.example.rapido.R;
import com.example.rapido.response.popularVideos.Example;
import com.example.rapido.response.popularVideos.Item;
import com.example.rapido.utils.Constants;
import com.example.rapido.utils.NoInternetException;
import com.example.rapido.utils.Utils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DataProvider implements RemoteDataProvider {

    private static DataProvider mInstance;
    private static ApiInterface mServices;
    private static Utils utils;

    private DataProvider() {
        mServices = ApiClient.getClient();
        utils = Utils.getInstance();
    }

    public static DataProvider getInstance() {
        if (mInstance == null) {
            synchronized (DataProvider.class) {
                if (mInstance == null) {
                    mInstance = new DataProvider();
                }
            }
        }
        return mInstance;
    }

    private boolean isNetworkAvailable() {
        return utils.isNetworkAvailable(BaseApp.getContext());
    }

    private void noInternetAvailable(Consumer<Throwable> error) {
        try {
            error.accept(new NoInternetException(BaseApp.getContext().getString(R.string.no_internet_connection)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleResponse(Example baseResponse, Consumer<Throwable> error) {
        try {
            if (baseResponse.getItems().size() == 0) {
                error.accept(new Throwable(BaseApp.getContext().getString(R.string.went_wrong)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Disposable getDefaultDisposable() {
        return new Disposable() {
            @Override
            public void dispose() {

            }

            @Override
            public boolean isDisposed() {
                return false;
            }
        };
    }


    @Override
    public Disposable fetchPopularVideos(Consumer<List<Item>> success, Consumer<Throwable> error) {


        if (isNetworkAvailable()) {

            return mServices.getVideos(Constants.API_KEY, Constants.MOST_POPULAR, Constants.MAX_VIDEOS, Constants.SNIPPET).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(example -> {
                        if (example == null) {
                            handleResponse(example, error);
                            return;
                        }
                        success.accept(example.getItems());

                    }, error);
        } else {
            noInternetAvailable(error);
            return getDefaultDisposable();
        }
    }

    @Override
    public Disposable fetchVideoDetails(String videoID, Consumer<List<Item>>  success, Consumer<Throwable> error) {

        if (isNetworkAvailable()) {
            return mServices.getVideoDetails(Constants.SNIPPET, videoID, Constants.API_KEY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(item -> {
                        if (item == null) {
                            handleResponse(item, error);
                            return;
                        }
                        success.accept(item.getItems());
                    }, error);


        } else {
            noInternetAvailable(error);
            return getDefaultDisposable();
        }

    }

}


