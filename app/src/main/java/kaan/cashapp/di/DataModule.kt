package kaan.cashapp.di

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import kaan.cashapp.data.RetrofitProvider
import kaan.cashapp.data.stocks.StocksAPI
import kaan.cashapp.data.stocks.model.ErrorResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val BASE_URL = "https://us-central1-blinkapp-684c1.cloudfunctions.net/"

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = OkHttpProfilerInterceptor()

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()
    }


//    val errorResponseJsonAdapter = ErrorResponseJsonAdapter()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()



    @Singleton
    @Provides
    fun provideMoshiConverter(moshi: Moshi): Converter.Factory = MoshiConverterFactory.create(moshi).asLenient()

    @Singleton
    @IntoSet
    @Provides
    fun provideOkHttpProfilerInterceptor(): Interceptor = OkHttpProfilerInterceptor()


    @Singleton
    @Provides
    fun provideRetrofit(
        converterFactory: Converter.Factory,
        interceptors: Set<@JvmSuppressWildcards Interceptor>
    ): Retrofit = RetrofitProvider(
        BASE_URL,
        converterFactory,
        interceptors.toList()   // easily extendable interceptors for client
    ).retrofit

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): StocksAPI = retrofit.create(StocksAPI::class.java)


    class ErrorResponseJsonAdapter {
        @FromJson
        fun fromJson(reader: JsonReader): ErrorResponse {
            reader.beginObject()
            var errorMessage: String = ""
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "errorMessage" -> errorMessage = reader.nextString()
                    else -> reader.skipValue()
                }
            }
            reader.endObject()
            return ErrorResponse(errorMessage)
        }

        @ToJson
        fun toJson(writer: JsonWriter, errorResponse: ErrorResponse) {
            writer.beginObject()
            writer.name("errorMessage").value(errorResponse.errorMessage)
            writer.endObject()
        }
    }
}
