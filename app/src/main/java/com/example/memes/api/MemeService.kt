package com.example.memes.api

import com.example.memes.model.MemeResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MemeService {
    @GET("get_memes")
    fun getMemes(): Call<MemeResponse>

    companion object {
        private const val BASE_URL = "https://api.imgflip.com/"
        var memeService: MemeService? = null

        fun getInstance() : MemeService {
            if (memeService == null) {
                val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                memeService = retrofit.create(MemeService::class.java)
            }
            return memeService!!
        }
    }
}