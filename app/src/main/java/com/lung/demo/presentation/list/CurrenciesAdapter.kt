package com.lung.demo.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.*
import javax.inject.Inject
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lung.demo.databinding.ItemCurrencyBinding
import com.lung.demo.domain.model.Currency
import com.lung.demo.presentation.model.CurrenciesVM
import java.math.BigDecimal


class CurrenciesAdapter @Inject constructor(private val vm: CurrenciesVM) :
    RecyclerView.Adapter<CurrencyViewHolder>() {

    private val currencies = mutableListOf<Currency>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrencyBinding.inflate(inflater, parent, false)
        return CurrencyViewHolder(binding, vm)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {}
    override fun onBindViewHolder(
        holder: CurrencyViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.isNotEmpty()) {
            if (payloads[0] is BigDecimal) {
                val amount = payloads[0] as BigDecimal
                holder.onBindPayloads(amount)
                return
            }
        }
        val currency = currencies[position]
        holder.onBind(currency)
    }

    override fun getItemCount() = currencies.size

    fun refresh(newCurrencies: List<Currency>) {
        val callback = CurrenciesDiffCallback(currencies, newCurrencies)
        val diffResult = DiffUtil.calculateDiff(callback)
        currencies.clear()
        currencies.addAll(newCurrencies)
        diffResult.dispatchUpdatesTo(this)
    }

    fun swap(fromPosition: Int) {
        Collections.swap(currencies, fromPosition, 0)
        notifyItemMoved(fromPosition, 0)
    }

    fun amountChanged(newCurrencies: List<Currency>) {
        currencies.clear()
        currencies.addAll(newCurrencies)
        newCurrencies.forEachIndexed { index, amount ->
            notifyItemChanged(index, amount.amount)
        }
    }

    fun clear() {
        currencies.clear()
        notifyDataSetChanged()
    }

}