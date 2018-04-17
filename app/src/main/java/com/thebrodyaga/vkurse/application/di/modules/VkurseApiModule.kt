package com.thebrodyaga.vkurse.application.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.thebrodyaga.vkurse.data.net.VkurseApi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Emelyanov.N4
 *         on 24.02.2018.
 */

@Module(includes = [OkHttpClientModule::class])
class VkurseApiModule {
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): VkurseApi {
        return retrofit.create<VkurseApi>(VkurseApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .baseUrl("http://10.100.19.152:8080/api/")
                .addConverterFactory(provideConverterFactory())
                .build()
    }

    private fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create(provideGson())
    }

    private fun provideGson(): Gson {
        return GsonBuilder().create()
    }
}
