package com.example.albertsonscoding.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(error: String) {
    Snackbar.make(this, error, Snackbar.LENGTH_SHORT).show()
}