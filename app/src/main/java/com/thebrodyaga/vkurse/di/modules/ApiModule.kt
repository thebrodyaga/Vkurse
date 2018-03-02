package com.thebrodyaga.vkurse.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.thebrodyaga.vkurse.net.VkurseApi
import dagger.Module
import dagger.Provides
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
    fun provideRetrofit(builder: Retrofit.Builder): Retrofit {
        return builder.baseUrl("http://10.100.19.152:8080/api/").build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(converterFactory: Converter.Factory): Retrofit.Builder {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(converterFactory)
    }

    @Provides
    @Singleton
    fun provideConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder().create()
    }
}
