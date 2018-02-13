package com.example.win10.vkurse.app

import android.app.Application

import com.vk.sdk.VKSdk

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
* Created by Win10 on 18.07.2017.
*/

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.vk.com/method/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val vkApi = retrofit.create(VkurseApi::class.java)
        VKSdk.initialize(this)
    }
}
