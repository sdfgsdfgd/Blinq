package kaan.cashapp.domain

import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kaan.cashapp.domain.melbAirport.StocksRepository
import kaan.cashapp.extra.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class, sdk = [31])
class TestHilt {
    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var stocksRepository: StocksRepository

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `Test stocks (Portfolio) API successful and alternative responses integrity, mapping and API contract`() {
        runTest {
            val stocks = stocksRepository.getStocks()

            Truth.assertThat(stocks.javaClass).isAssignableTo(Result::class.java)

            // Happy Path tests
            stocks.successData()?.size?.let {
                Truth.assertThat(stocks).isInstanceOf(Result.Success::class.java)
                Truth.assertThat(it).isAtLeast(0)

                // Happy Path non-empty tests
                // + Check Non-null fields
                // Chooses a random stock from the response, if any.
                stocks.successData()?.random()?.let { testStock ->
                    Truth.assertThat(testStock.name).isNotNull()
                    Truth.assertThat(testStock.ticker).isNotNull()
                    Truth.assertThat(testStock.date).isNotNull()
                    Truth.assertThat(testStock.price).isNotNull()
                } // Unhappy path tests
            } ?: Truth.assertThat(stocks).isAnyOf(Result.NoData, Result.Loading, Result.Error)
        }
    }

    @Test
    fun `Test stocks (Portfolio) no data response and correct parsing of no data state`() {
        runTest {
            val stocks = stocksRepository.getStocks()

            Truth.assertThat(stocks).isInstanceOf(Result.NoData.javaClass)
        }
    }

    @Test
    fun `Test stocks (Portfolio) malformed response and correct parsing of error state`() {
        runTest {
            val stocks = stocksRepository.getStocks()

            Truth.assertThat(stocks).isInstanceOf(Result.Error.javaClass)
        }
    }
}
