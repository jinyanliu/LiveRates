package com.lung.demo.presentation.list

import android.text.Editable
import android.text.TextWatcher
import com.lung.demo.databinding.ItemCurrencyBinding
import com.lung.demo.domain.model.Currency
import com.lung.demo.presentation.base.MvvmViewHolder
import com.lung.demo.presentation.model.CurrenciesVM
import com.lung.demo.utils.toAmount
import java.math.BigDecimal
import android.text.InputFilter

class CurrencyViewHolder(binding: ItemCurrencyBinding, vm: CurrenciesVM) :
    MvvmViewHolder<ItemCurrencyBinding, CurrenciesVM, Currency>(binding, vm) {

    private val itemData = CurrencyItemData()

    init {
        binding.data = itemData
    }

    fun onBindPayloads(data: BigDecimal) {
        if (!isBaseCurrency()) {
            itemData.setAmount(data)
        }
    }

    private fun CurrencyItemData.setAmount(data: BigDecimal) {
        if (data > BigDecimal.ZERO) {
            amount.set(data.toPlainString())
        } else {
            amount.set("")
        }
    }

    override fun onBind(data: Currency) {
        itemData.run {
            title.set(data.name)
            description.set(data.description)
            img.set(data.img)
            setAmount(data.amount)
        }
        if (isBaseCurrency()) {
            setBaseCurrencyViewHolder()
        } else {
            setSubCurrencyViewHolder()
        }
    }

    private fun setSubCurrencyViewHolder() {
        binding.currencyAmount.filters = arrayOf<InputFilter>()
        binding.currencyAmount.removeTextChangedListener(textWatcher)
        binding.currencyAmount.setOnFocusChangeListener { _, active ->
            if (active)
                handleClick()
        }
        itemView.setOnClickListener { handleClick() }
    }

    private fun setBaseCurrencyViewHolder() {
        binding.currencyAmount.addTextChangedListener(textWatcher)
        binding.currencyAmount.onFocusChangeListener = null
        itemView.setOnClickListener(null)
        binding.currencyAmount.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val newValue = s?.toString()
            handleAmountChange(newValue.toAmount())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }


    private fun handleClick() {
        if (!isBaseCurrency()) {
            binding.currencyAmount.onFocusChangeListener = null
            itemView.setOnClickListener(null)
            val newValue = itemData.amount.get().toAmount()
            vm.currencySelected(adapterPosition, itemData.title.get()!!, newValue)
            binding.currencyAmount.post {
                binding.currencyAmount.requestFocus()
                binding.currencyAmount.setSelection(binding.currencyAmount.text.length)
            }
        }
    }

    private fun handleAmountChange(amount: BigDecimal) {
        if (isBaseCurrency()) {
            vm.amountChanged(amount)
        }
    }

    private fun isBaseCurrency() = adapterPosition == 0

}