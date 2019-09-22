package com.lung.demo.presentation.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class MvvmActivity<DB : ViewDataBinding, VM : BaseViewModel<*>> : DaggerAppCompatActivity() {

    protected lateinit var binding: DB

    @Inject
    lateinit var vm: VM

    protected abstract val layout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layout)
        lifecycle.addObserver(vm)
    }

    override fun onDestroy() {
        lifecycle.removeObserver(vm)
        super.onDestroy()
    }


}