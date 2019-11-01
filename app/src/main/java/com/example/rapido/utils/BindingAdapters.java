package com.example.rapido.utils;

import androidx.databinding.BindingAdapter;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import com.google.android.material.textfield.TextInputLayout;


public class BindingAdapters {

    @BindingAdapter({"fieldName"})
    public static void hideError(TextInputLayout textInputLayout, String fieldName) {
        if (!TextUtils.isEmpty(fieldName)) {
            textInputLayout.setError(null);
        }
    }

    @BindingAdapter({"rangeMin", "rangeMax"})
    public static void inputRange(EditText editText, int rangeMin, int rangeMax) {
        Log.e("inputRange", "editText=" + editText + " rangeMax=" + rangeMax);
        editText.setFilters(new InputFilter[]{new InputFilterMinMax(rangeMin, rangeMax)});
    }

    @BindingAdapter({"rangeMin"})
    public static void inputRange(EditText editText, int rangeMin) {
        editText.setFilters(new InputFilter[]{new InputFilterMinMax(rangeMin, 99999999)});
    }

    @BindingAdapter("imgResource")
    public static void setImageResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter("showHide")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}

