package com.example.memes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.memes.model.Meme
import com.example.memes.repository.MemeRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
/**
 * Unit tests for the implementation of [MainViewModel]
 */
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var memeRepository:MemeRepository

    // Subject under test
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setupViewModel() {
        memeRepository = mock(MemeRepository::class.java)
        mainViewModel = MainViewModel(memeRepository)
    }

    @Test
    fun updateExistingListOnActionInCheckbox() {
        val oldMemeList = listOf(
            Meme("1234", "test meme 1", "", 1,2,1),
            Meme("3456", "test meme 2", "", 1,2,1)
        )
        val updatedMemeList = listOf(
            Meme("1234", "test meme 1", "", 1,2,1),
            Meme("3456", "test meme 2", "", 1,2,1, true)
        )
        mainViewModel.memeList.value = oldMemeList
        val observer = mock(Observer::class.java) as Observer<in List<Meme>>
        mainViewModel.memeList.observeForever(observer)

        mainViewModel.updateMemeCheckedStatus("3456", true)
        var captor = ArgumentCaptor.forClass(List::class.java)
        captor.run {
            verify(observer, atLeastOnce()).onChanged(capture() as List<Meme>?)
            Assert.assertEquals(updatedMemeList, value)
        }

        mainViewModel.updateMemeCheckedStatus("3456", false)
        captor = ArgumentCaptor.forClass(List::class.java)
        captor.run {
            verify(observer, atLeastOnce()).onChanged(capture() as List<Meme>?)
            Assert.assertEquals(oldMemeList, value)
        }
    }
}