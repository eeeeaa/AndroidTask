package com.hitenderpannu.base.di

import com.hitenderpannu.base.BuildConfig
import com.hitenderpannu.base.MainApplication
import com.hitenderpannu.base.network.ConnectionManagerFactory
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class CoreModule(private val application: MainApplication) {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Singleton
    @Provides
    fun provideNetworkConnectionChecked(): NetworkConnectionChecker {
        val manager = ConnectionManagerFactory(application).getConnectionManager()
        return object : NetworkConnectionChecker {
            override suspend fun isConnected(): Boolean {
                return manager.isConnected()
            }
        }
    }
}