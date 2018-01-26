package com.example.win10.vkurse

import android.app.Application

import com.vk.sdk.VKSdk

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
* Created by Win10 on 18.07.2017.
*/

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.vk.com/method/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val vkApi = retrofit.create(VkApi::class.java)
        VKSdk.initialize(this)
    }

    /*companion object {
        var vkApi: VkApi
    }*/
}
