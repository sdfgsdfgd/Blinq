package kaan.cashapp.data.stocks.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data layer Stock `POJO`
 */
@JsonClass(generateAdapter = true)
data class StockX(
    @Json(name = "currency")
    val currency: String,
    @Json(name = "current_price_cents")
    val currentPriceCents: Int,
    @Json(name = "current_price_timestamp")
    val currentPriceTimestamp: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "quantity")
    val quantity: Int?,
    @Json(name = "ticker")
    val ticker: String
)
