package com.example.memes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.memes.api.MemeService
import com.example.memes.databinding.ActivityMainBinding
import com.example.memes.repository.MemeRepository

class MainActivity : AppCompatActivity() {

    private val memeService = MemeService.getInstance()
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory(MemeRepository(memeService))
        )[MainViewModel::class.java]
    }

    private val memeListAdapter by lazy {
        MemeListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.memesRecyclerView.adapter = memeListAdapter

        viewModel.memeList.observe(this, {
            memeListAdapter.setMemeList(it)
        })
        viewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.loadPopularMemes()
    }
}