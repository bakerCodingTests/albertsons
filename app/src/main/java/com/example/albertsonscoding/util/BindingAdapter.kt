package com.example.albertsonscoding.util

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.albertsonscoding.view.search.LongformAdapter
import com.example.albertsonscoding.view.search.SearchState

@BindingAdapter("android:updateState")
fun RecyclerView.updateState(state: SearchState) {
    adapter?.let{
        (adapter as LongformAdapter).submitList(state.longforms)
    }
}

@BindingAdapter("android:showSnackBar")
fun ViewGroup.showSnackBar(state: SearchState) {
    if (state.showError) showSnackbar(context.getString(state.errorStringId))
}