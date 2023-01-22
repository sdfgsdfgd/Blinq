package kaan.cashapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kaan.cashapp.R
import kaan.cashapp.ui.home.models.Stock
import kaan.cashapp.domain.melbAirport.StocksRepository
import kaan.cashapp.extra.asLiveData
import kaan.cashapp.extra.recyclerview.NegativeDiffCallback
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: StocksRepository) : ViewModel() {

    // Stocks Recyclerview Config
    private val _stocks = MutableLiveData<List<Stock>>()
    val stocks = _stocks.asLiveData()
    val stocksDiff: DiffUtil.ItemCallback<Stock> = NegativeDiffCallback()
    val stocksLayoutProvider: (Stock) -> Int = { stock ->
        R.layout.view_stock
    }

    fun setStocks(stocks: List<Stock>) {
        _stocks.value = stocks
    }
}
