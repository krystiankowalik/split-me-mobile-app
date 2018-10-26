package com.github.krystiankowalik.splitme.mobile.net.auth.keycloak


import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.krystiankowalik.splitme.mobile.App
import com.github.krystiankowalik.splitme.mobile.util.SharedPreferencesManager
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class KeycloakApiClient() {

    @Inject private lateinit var prefsManager: SharedPreferencesManager
    @Inject private lateinit var gson: Gson

    companion object {
        private val BASE_URL = "http://192.168.0.12:8080/"
    }

    lateinit var service: KeycloakApiService

    init {
        App.appComponent.inject(this)
        createRetrofit(prefsManager)
    }

    private fun createRetrofit(prefsManager: SharedPreferencesManager) {
        val clientBuilder = OkHttpClient.Builder()

        // Add logging interceptor to see communication between app and server
        val loggingInterceptor = HttpLoggingInterceptor()
//        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        }

        // Add interceptors to OkHttpClient
        clientBuilder.addInterceptor(loggingInterceptor)
        // Set timeouts
        clientBuilder.addNetworkInterceptor(StethoInterceptor())
        clientBuilder.connectTimeout(1, TimeUnit.MINUTES)
        clientBuilder.writeTimeout(1, TimeUnit.MINUTES)
        clientBuilder.readTimeout(1, TimeUnit.MINUTES)

        // Create retrofit instance
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuilder.build())
                .build()

        // Create service(interface) instance
        service = retrofit.create(KeycloakApiService::class.java)
    }

}
