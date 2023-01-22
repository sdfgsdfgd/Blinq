package kaan.cashapp.data.stocks

import kaan.cashapp.data.stocks.model.AuthRequestBody
import kaan.cashapp.data.stocks.model.ErrorResponse
import kaan.cashapp.data.stocks.model.StocksResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface StocksAPI {


    @Headers("Content-Type: application/json")
    @POST(ENDPOINT_FAKE_AUTH)
    suspend fun auth(@Body req: AuthRequestBody): Any?


    @POST(ENDPOINT_FAKE_AUTH)
    suspend fun auth1(name: String = "test", email: String = "test@email"): Any?

    @GET(ENDPOINT_PORTFOLIO_EMPTY)
    suspend fun getStocksEmpty(): StocksResponse

    @GET(ENDPOINT_PORTFOLIO_MALFORMED)
    suspend fun getStocksMalformed(): StocksResponse

    companion object {
        // API
        private const val ENDPOINT_FAKE_AUTH: String = "fakeAuth"

        // API - Testing
        private const val ENDPOINT_PORTFOLIO_EMPTY: String = "portfolio_empty.json"
        private const val ENDPOINT_PORTFOLIO_MALFORMED: String = "portfolio_malformed.json"
    }
}
