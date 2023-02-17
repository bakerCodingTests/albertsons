package com.example.albertsonscoding.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.albertsonscoding.R
import com.example.albertsonscoding.databinding.FragmentSearchBinding
import com.example.albertsonscoding.viewmodel.AcronymVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val acronymVM: AcronymVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DataBindingUtil.inflate<FragmentSearchBinding>(
            inflater, R.layout.fragment_search,
            container,
            false
        ).apply {
            acronymVM = this@SearchFragment.acronymVM
            lfAdapter = LongformAdapter()
            lifecycleOwner = this@SearchFragment.viewLifecycleOwner
        }.root
    }
}
