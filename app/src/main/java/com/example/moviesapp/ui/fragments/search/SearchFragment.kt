package com.example.moviesapp.ui.fragments.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.moviesapp.databinding.FragmentSearchMoviesBinding
import com.example.moviesapp.ui.fragments.base.BindingFragment

class SearchFragment : BindingFragment<FragmentSearchMoviesBinding>() {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchMoviesBinding =
        FragmentSearchMoviesBinding.inflate(inflater, container, false)

}