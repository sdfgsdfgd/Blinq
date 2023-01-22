package kaan.cashapp.domain.melbAirport

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kaan.cashapp.data.stocks.StocksAPI
import kaan.cashapp.data.stocks.model.AuthRequestBody
import kaan.cashapp.extra.Result
import kaan.cashapp.ui.home.models.Stock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StocksRepository @Inject constructor(private val stocksAPI: StocksAPI) {

    val error = MutableLiveData<String>()


    private val _stocks = MutableStateFlow<Result<List<Stock>>>(Result.NoData)
    val stocks = _stocks.asStateFlow()

    // Fetch stocks
    suspend fun fetchStocks(name: String, email: String) = _stocks.emit(getStocks(name, email))

    suspend fun getStocks(name: String, email: String): Result<List<Stock>> {
        try {
            _stocks.emit(Result.Loading)

            val response = stocksAPI.auth(AuthRequestBody(name, email))

//            error.value = response?.errorMessage

            if (response.toString().contains("registered", true))
                _stocks.value = Result.Success(emptyList())

            _stocks.value = Result.Success(emptyList())
        } catch (e: Exception) {

            e.printStackTrace()
            Log.e("XXX", "type [ ${e.cause} ]-----${e.message}")
            Log.e("XXX", "type [ ${e.javaClass} ]-----${e.stackTraceToString()}")
            _stocks.value = Result.Error
        }

        return _stocks.value
    }
}
