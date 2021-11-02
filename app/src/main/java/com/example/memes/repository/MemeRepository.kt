package com.example.memes.repository

import com.example.memes.api.MemeService

class MemeRepository constructor(private val memeService: MemeService) {
    fun getPopularMemes() = memeService.getMemes()
}