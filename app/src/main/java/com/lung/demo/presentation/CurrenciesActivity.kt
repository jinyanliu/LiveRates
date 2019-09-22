package com.lung.demo.presentation

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lung.demo.R
import com.lung.demo.databinding.ActivityCurrenciesBinding
import com.lung.demo.presentation.base.MvvmActivity
import com.lung.demo.presentation.list.CurrenciesAdapter
import com.lung.demo.presentation.animation.CurrenciesItemAnimator
import com.lung.demo.presentation.model.CurrenciesVM
import javax.inject.Inject

class CurrenciesActivity : MvvmActivity<ActivityCurrenciesBinding, CurrenciesVM>() {

    @Inject
    lateinit var currenciesAdapter: CurrenciesAdapter

    @Inject
    lateinit var currenciesItemAnimator: CurrenciesItemAnimator

    override val layout = R.layout.activity_currencies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.currenciesList.run {
            layoutManager = LinearLayoutManager(context)
            adapter = currenciesAdapter
            itemAnimator = currenciesItemAnimator
        }

        setSupportActionBar(binding.toolbar)

        binding.data = vm

        vm.currenciesChanges.observe(this, Observer {
            currenciesAdapter.refresh(it)
        })
        vm.amountChanges.observe(this, Observer {
            currenciesAdapter.amountChanged(it)
        })
        vm.baseChanged.observe(this, Observer {
            binding.currenciesList.scrollToPosition(0)
            currenciesAdapter.swap(it)
        })
        vm.errorEvent.observe(this, Observer {
            currenciesAdapter.clear()
            Snackbar.make(binding.root, R.string.error_text, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) { vm.retry() }
                .show()
        })

    }

}
