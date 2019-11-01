package com.example.rapido.base;

import androidx.databinding.ViewDataBinding;

import androidx.recyclerview.widget.RecyclerView;

public class GenericViewHolder<V extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public V mBinding;

    public GenericViewHolder(ViewDataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());
        mBinding = (V) viewDataBinding;
    }
}
