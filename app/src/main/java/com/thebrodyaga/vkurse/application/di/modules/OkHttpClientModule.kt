package com.thebrodyaga.vkurse.application.di.modules

import android.content.Context
import com.thebrodyaga.vkurse.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Singleton


/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */
@Module
class OkHttpClientModule {
    @Provides
    @Singleton
    fun okHttpClient(context: Context): OkHttpClient {
        return OkHttpClient()
                .newBuilder()
                .cache(cache(file(context)))
                .addInterceptor(httpLoggingInterceptor())
                .build()
    }

    private fun cache(cacheFile: File): Cache {
        return Cache(cacheFile, 10 * 1000 * 1000) //10 MB
    }

    private fun file(context: Context): File {
        val file = File(context.cacheDir, "HttpCache")
        file.mkdirs()
        return file
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE)
    }
}