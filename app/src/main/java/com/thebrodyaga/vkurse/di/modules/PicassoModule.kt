package com.thebrodyaga.vkurse.di.modules

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import dagger.Module
import okhttp3.OkHttpClient
import com.squareup.picasso.Picasso
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */

@Module(includes = [OkHttpClientModule::class])
class PicassoModule {
    @Provides
    @Singleton
    fun picasso(context: Context, okHttp3Downloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(context).downloader(okHttp3Downloader).build()
    }

    @Provides
    @Singleton
    fun okHttp3Downloader(okHttpClient: OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(okHttpClient)
    }
}