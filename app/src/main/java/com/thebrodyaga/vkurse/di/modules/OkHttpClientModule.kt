package com.thebrodyaga.vkurse.di.modules

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
@Module(includes = [ContextModule::class])
class OkHttpClientModule {
    @Provides
    @Singleton
    fun okHttpClient(cache: Cache, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient()
                .newBuilder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }

    @Provides
    fun cache(cacheFile: File): Cache {
        return Cache(cacheFile, 10 * 1000 * 1000) //10 MB
    }

    @Provides
    fun file(context: Context): File {
        val file = File(context.cacheDir, "HttpCache")
        file.mkdirs()
        return file
    }

    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE)
    }
}