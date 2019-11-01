package com.example.rapido.base;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment implements UICallbacks {

    protected V mViewModel;
    protected T mBinding;
    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mViewModel = (V) ViewModelProviders.of(getBaseActivity()).get(getViewModel());
        return mBinding.getRoot();
    }

    public BaseViewModel getParentViewModel() {
        return getBaseActivity().getParentViewModel();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        onBinding();
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

}
