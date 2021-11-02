package com.example.memes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.memes.repository.MemeRepository
import java.lang.IllegalArgumentException

class MainViewModelFactory constructor(private val repository: MemeRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}