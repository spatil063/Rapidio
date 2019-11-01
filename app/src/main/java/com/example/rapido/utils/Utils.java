package com.example.rapido.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.InputFilter;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    private static Utils instance;

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    public void setNumberFilter(TextInputEditText edt, final int maxDigitsBeforeDecimalPoint, final int maxDigitsAfterDecimalPoint) {
        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            StringBuilder builder = new StringBuilder(dest);
            builder.replace(dstart, dend, source.subSequence(start, end).toString());
            if (!builder.toString().matches(
                    "(([1-9]{1})([0-9]{0," + (maxDigitsBeforeDecimalPoint - 1) + "})?)?(\\.[0-9]{0," + maxDigitsAfterDecimalPoint + "})?"

            )) {
                if (source.length() == 0)
                    return dest.subSequence(dstart, dend);
                return "";
            }

            return null;
        };
        edt.setFilters(new InputFilter[]{filter});
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public String getFormattedDate(String date, String inputFormat, String outputFormat) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(inputFormat, Locale.US);
            Date myDate = dateFormat.parse(date);
            SimpleDateFormat timeFormat = new SimpleDateFormat(outputFormat, Locale.US);
            return timeFormat.format(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public double parseAmountFromString(String num) {
        try {
            String strDouble = String.format(Locale.US, "%.2f", Double.parseDouble(num));
            return Double.parseDouble(strDouble);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public String parseAmountToTwoDecimals(double num) {
        try {
            DecimalFormat df = new DecimalFormat("#.##");
            return String.valueOf(df.format(num));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }



}
