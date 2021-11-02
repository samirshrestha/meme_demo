package com.example.memes.model

import com.google.gson.annotations.SerializedName

data class MemeResponse(val success: Boolean, val data: MemeData)

data class MemeData(val memes: List<Meme>)

data class Meme(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("url")
    val url: String,
    @field:SerializedName("width")
    val width: Int,
    @field:SerializedName("height")
    val height: Int,
    @field:SerializedName("box_count")
    val boxCount: Int
)