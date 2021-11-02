package com.example.memes

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.IdlingResource
import com.example.memes.api.MemeService
import com.example.memes.databinding.ActivityMainBinding
import com.example.memes.idlingResource.SimpleIdlingResource
import com.example.memes.repository.MemeRepository

class MainActivity : AppCompatActivity() {

    private val memeService = MemeService.getInstance()
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory(MemeRepository(memeService))
        )[MainViewModel::class.java]
    }

    private val memeListAdapter by lazy {
        MemeListAdapter(viewModel)
    }

    private var mIdlingResource: SimpleIdlingResource? = null

    // TODO: move setting of idle state to viewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.memesRecyclerView.adapter = memeListAdapter

        viewModel.memeList.observe(this, {
            memeListAdapter.setMemeList(it)
            mIdlingResource?.setIdleState(true)
        })
        viewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            mIdlingResource?.setIdleState(true)
        })

        viewModel.loadPopularMemes()

        mIdlingResource?.setIdleState(false)
    }

    /**
     * Only called from test, creates and returns a new [SimpleIdlingResource].
     */
    @VisibleForTesting
    fun getIdlingResource(): IdlingResource {
        if (mIdlingResource == null) {
            mIdlingResource = SimpleIdlingResource()
        }
        return mIdlingResource as SimpleIdlingResource
    }
}