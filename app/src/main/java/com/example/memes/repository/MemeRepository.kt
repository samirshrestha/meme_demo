package com.example.memes.repository

import com.example.memes.api.MemeService
import com.example.memes.model.MemeResponse
import retrofit2.Call

class MemeRepository constructor(private val memeService: MemeService) {
    fun getPopularMemes(): Call<MemeResponse> = memeService.getMemes()
}