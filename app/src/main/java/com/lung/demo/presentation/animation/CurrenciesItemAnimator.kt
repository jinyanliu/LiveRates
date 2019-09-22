package com.lung.demo.presentation.animation

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class CurrenciesItemAnimator @Inject constructor(private val currencyAnimationsUpdateService: CurrencyAnimationsUpdateService) :
    DefaultItemAnimator() {

    init {
        supportsChangeAnimations = false
    }

    override fun onAnimationFinished(viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder.oldPosition != 0 && viewHolder.adapterPosition == 0) {
            currencyAnimationsUpdateService.onIsAnimationsEnd(true)
        }
    }
}