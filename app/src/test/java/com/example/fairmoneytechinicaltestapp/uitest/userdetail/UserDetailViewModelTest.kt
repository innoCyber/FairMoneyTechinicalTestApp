package com.example.fairmoneytechinicaltestapp.uitest.userdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fairmoneytechinicaltestapp.helpers.MainCoroutineRule
import com.example.fairmoneytechinicaltestapp.helpers.TestHelperObject
import com.example.fairmoneytechinicaltestapp.helpers.TestRepository
import com.example.fairmoneytechinicaltestapp.helpers.getOrAwaitValue
import com.example.fairmoneytechinicaltestapp.ui.userdetail.UserDetailViewModel
import com.example.fairmoneytechinicaltestapp.util.UIStatus
import com.nhaarman.mockitokotlin2.argumentCaptor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserDetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutine = MainCoroutineRule()

    private lateinit var viewModel: UserDetailViewModel

    private val repository = TestRepository()


    @Before
    fun setup() {
        viewModel = UserDetailViewModel(repository)
    }

    @Test
    fun `network call returns success`() {
        val id = argumentCaptor<String>()

        mainCoroutine.runBlockingTest {

            val status = viewModel.getUserFromRemote(id.toString()).getOrAwaitValue()
            MatcherAssert.assertThat(status, `is`(UIStatus.success(Unit)))
        }
    }

}