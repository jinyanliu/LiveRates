package com.lung.demo.presentation.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class MvvmViewHolder<B : ViewDataBinding, VM : BaseViewModel<*>, in D>(
    @JvmField protected val binding: B,
    @JvmField protected val vm: VM
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun onBind(data: D)

}