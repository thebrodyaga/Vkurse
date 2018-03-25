package com.thebrodyaga.vkurse.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.thebrodyaga.vkurse.BuildConfig
import com.thebrodyaga.vkurse.net.VkurseApi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Emelyanov.N4 on 24.02.2018.
 */

@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): VkurseApi {
        return retrofit.create<VkurseApi>(VkurseApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(converterFactory: Converter.Factory, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .baseUrl("http://192.168.0.77:8080/api/")
                .addConverterFactory(converterFactory)
                .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE)
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder().create()
    }
}
