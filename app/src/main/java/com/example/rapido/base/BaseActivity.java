package com.example.rapido.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import androidx.lifecycle.ViewModelProviders;

import com.example.rapido.R;
import com.example.rapido.databinding.ToolbarBinding;
import com.example.rapido.utils.LoadingDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity
        implements UICallbacks {

    protected T mBinding;
    protected V mViewModel;
    protected Context mContext;
    private LoadingDialog loadingDialog;
    private BottomSheetDialog tokenExpireDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mBinding = DataBindingUtil.setContentView(BaseActivity.this, getLayoutId());
        mViewModel = (V) ViewModelProviders.of(BaseActivity.this).get(getViewModel());
        mViewModel.setNavigator(getNavigatorReference());
        createDialog();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        onBinding();
    }

    public V getParentViewModel() {
        return mViewModel;
    }

    private void createDialog() {
        loadingDialog = new LoadingDialog(BaseActivity.this);
        loadingDialog.setCancelable(false);

        mViewModel.dialogMessage.observe(BaseActivity.this, msg -> {
            if (loadingDialog != null) {
                loadingDialog.setMessage(String.valueOf(msg));
            }
        });

        mViewModel.dialogVisibility.observe(this, showDialog -> {
            if ((Boolean) showDialog) {
                if (loadingDialog != null && !loadingDialog.isShowing())
                    loadingDialog.show();

            } else {
                if (loadingDialog != null && loadingDialog.isShowing())
                    loadingDialog.dismiss();
            }

        });

        mViewModel.isTokenExpire.observe(this, isTokenExpire -> {
        });
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_from_right_enter, R.anim.anim_slide_in_from_right_exit);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.anim_slide_in_from_right_enter, R.anim.anim_slide_in_from_right_exit);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_slide_in_from_left_enter, R.anim.anim_slide_in_from_left_exit);
    }

    protected void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    protected void setToolbar(ToolbarBinding toolbarBinding, int toolbarColor, int toolbarTextColor, boolean showBackArrow, String title, int statusbarColor) {
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showBackArrow);
        getSupportActionBar().setDisplayShowHomeEnabled(showBackArrow);
        toolbarBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, toolbarColor));
        toolbarBinding.txtToolbarTitle.setText(title);
        toolbarBinding.txtToolbarTitle.setTextColor(ContextCompat.getColor(mContext, toolbarTextColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(mContext, statusbarColor));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
