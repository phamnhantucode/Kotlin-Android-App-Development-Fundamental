package com.learning.loginsignupwithmvvm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.learning.loginsignupwithmvvm.repository.BaseRepository

abstract class BaseFragment<VM: ViewModel, B: ViewBinding, R: BaseRepository>(val layoutId: Int): Fragment() {

    private lateinit var binding: B
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,layoutId , container, false)
        return binding.root
    }

    abstract fun getViewModel(): VM
    abstract fun getRepository(): R


}