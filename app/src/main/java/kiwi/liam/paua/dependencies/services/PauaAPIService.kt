package kiwi.liam.paua.dependencies.services

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import kiwi.liam.paua.dependencies.models.Route
import kiwi.liam.paua.dependencies.models.Stop
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ApiEndpoints {
    @GET("/v1/gtfs/stops")
    suspend fun getStops(): List<Stop>

    @GET("/v1/gtfs/routes")
    suspend fun getRoutes(): List<Route>
}

class PauaAPIService(
    private val moshi: Moshi,
    private val context: Context,
) {
    private val okHttpClient: OkHttpClient = okHttpClient()
    val api: ApiEndpoints = endpoints()

    private fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader(
                        "x-api-key",
                        "mpytA4LWoc7UGwUo482091VHn42fH9hd3zAEMKyq"
                    )
                    .build()
            )
        }
        .addInterceptor(ChuckerInterceptor.Builder(context).build())
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private fun endpoints(): ApiEndpoints = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .client(okHttpClient)
        .baseUrl("https://api.opendata.metlink.org.nz")
        .build()
        .create(ApiEndpoints::class.java)
}