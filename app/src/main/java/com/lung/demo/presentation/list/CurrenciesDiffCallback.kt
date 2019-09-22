package com.lung.demo.presentation.list

import androidx.recyclerview.widget.DiffUtil
import com.lung.demo.domain.model.Currency

class CurrenciesDiffCallback(private val oldCollection: List<Currency>,
                             private val newCollection: List<Currency>): DiffUtil.Callback() {

    override fun getOldListSize() = oldCollection.size
    override fun getNewListSize() = newCollection.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCollection[oldItemPosition].name == newCollection[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldContent = oldCollection[oldItemPosition]
        val newContent = newCollection[newItemPosition]
        return oldContent == newContent
    }

}