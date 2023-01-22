package kaan.cashapp.data.stocks.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StocksResponse(
    @Json(name = "stocks")
    val stocks: List<StockX>
)