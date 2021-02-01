package com.example.fairmoneytechinicaltestapp.uitest.userlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fairmoneytechinicaltestapp.helpers.MainCoroutineRule
import com.example.fairmoneytechinicaltestapp.helpers.TestHelperObject
import com.example.fairmoneytechinicaltestapp.helpers.TestRepository
import com.example.fairmoneytechinicaltestapp.helpers.getOrAwaitValue
import com.example.fairmoneytechinicaltestapp.ui.userlist.MainViewModel
import com.example.fairmoneytechinicaltestapp.util.UIStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.hamcrest.CoreMatchers.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutine = MainCoroutineRule()

    private lateinit var mainViewModel: MainViewModel

    private val repository = TestRepository()

    @Before
    fun setup() {
        mainViewModel = MainViewModel(repository)

    }

    @Test
    fun `network call returns success`() {
        mainCoroutine.runBlockingTest {
            mainViewModel.getAllUsers()

            val status = mainViewModel.fetchAllUsers.getOrAwaitValue()
            MatcherAssert.assertThat(status, `is`(UIStatus.success(Unit)))
        }
    }


    @Test
    fun `network call saves user data`() {
        mainCoroutine.runBlockingTest {
            mainViewModel.getAllUsers()

            val status = mainViewModel.fetchAllUsers.getOrAwaitValue()
            MatcherAssert.assertThat(status, `is`(UIStatus.success(Unit)))

            val subjects = mainViewModel.userList.getOrAwaitValue()
            MatcherAssert.assertThat(subjects, `is`(TestHelperObject.userList))
        }
    }
}