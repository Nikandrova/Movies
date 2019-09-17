package com.example.movies.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.example.movies.R;

public class BaseActivity extends MvpAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount() == 0)
            finish();
    }

    void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void showProgress() {
        hideKeyboard();
        View progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        View progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }
}
