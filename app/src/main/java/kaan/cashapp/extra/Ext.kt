package kaan.cashapp.extra

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.*
import kaan.cashapp.data.stocks.model.StockX
import kaan.cashapp.extra.DateTimeUtils.toReadableDate
import kaan.cashapp.ui.home.models.Stock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

fun StockX.toStock() = Stock(
    ticker = ticker,
    name = name,
    date = currentPriceTimestamp.timestampToReadableDate(),
    price = currentPriceCents.toDouble().convert().replace("$", "$currency ")
)


fun Double.convert(): String = NumberFormat.getCurrencyInstance(Locale.US).format(this / 100.0)

fun Int.timestampToReadableDate() = Date(toLong() * 1000L).toReadableDate()

fun View.visible(value: Boolean) {
    isVisible = value
}

/**
 *
 * @author
 * Inspired by  Google Developer  Manuel Vivo.
 *
 * https://medium.com/androiddevelopers/repeatonlifecycle-api-design-story-8670d1a7d333
 *
 *
 */

/* Copyright 2022 Google LLC.
   SPDX-License-Identifier: Apache-2.0 */
inline fun <T> Flow<T>.launchAndCollectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend CoroutineScope.(T) -> Unit
) = owner.lifecycleScope.launch {
    owner.repeatOnLifecycle(minActiveState) {
        collect {
            action(it)
        }
    }
}

suspend fun MutableStateFlow<Result<List<Stock>>>.loading() {
    this.emit(Result.Loading)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>
