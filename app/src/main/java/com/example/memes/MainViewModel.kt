package com.example.memes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.memes.model.Meme
import com.example.memes.model.MemeResponse
import com.example.memes.repository.MemeRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MemeRepository) : ViewModel() {

    val memeList = MutableLiveData<List<Meme>>()
    val errorMessage = MutableLiveData<String>()

    fun loadPopularMemes() {
        val response = repository.getPopularMemes()
        response.enqueue(object : Callback<MemeResponse>{
            override fun onResponse(call: Call<MemeResponse>, response: Response<MemeResponse>) {
                val memeResponse: MemeResponse? = response.body()
                if (memeResponse?.success == true) {
                    memeList.postValue(memeResponse.data.memes)
                } else {
                    errorMessage.postValue("Error while fetching memes")
                }
            }

            override fun onFailure(call: Call<MemeResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }
}