package com.example.fairmoneytechinicaltestapp.datatest.repository

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.fairmoneytechinicaltestapp.data.datasource.local.LocalDatabase
import com.example.fairmoneytechinicaltestapp.data.datasource.remote.FetchRemoteData
import com.example.fairmoneytechinicaltestapp.data.datasource.repository.UserRepositoryImpl
import com.example.fairmoneytechinicaltestapp.data.model.User
import com.example.fairmoneytechinicaltestapp.helpers.MainCoroutineRule
import com.example.fairmoneytechinicaltestapp.helpers.TestHelperObject
import com.example.fairmoneytechinicaltestapp.helpers.getOrAwaitValue
import com.example.fairmoneytechinicaltestapp.util.UIStatus
import com.nhaarman.mockitokotlin2.argumentCaptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class UserRepositoryTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: UserRepositoryImpl
    private val remoteDataSource = mock(FetchRemoteData::class.java)
    private val localDataSource = mock(LocalDatabase::class.java)

    @Before
    fun setup() {
        repository = UserRepositoryImpl(remoteDataSource, localDataSource, Dispatchers.Main)
    }


    @Test
    fun `app persists data when network call returns success`() =
        mainCoroutineRule.runBlockingTest {
            `when`(remoteDataSource.getAllUsers()).thenReturn(UIStatus.success(TestHelperObject.userResponse))
            `when`(localDataSource.saveUsers(TestHelperObject.userList)).thenReturn(Unit)

            val response = repository.getAllUsersFromRemote().getOrAwaitValue()

            assertThat(response, `is`(UIStatus.success(Unit)))
            verify(remoteDataSource).getAllUsers()
            verify(localDataSource).saveUsers(TestHelperObject.userList)
        }

    @Test
    fun `repository returns data`() {
        val data = MutableLiveData<List<User>>()
        data.value = TestHelperObject.userList

        `when`(localDataSource.getUsers()).thenReturn(data)

        val response = repository.getAllUsersFromLocal().getOrAwaitValue()
        verify(localDataSource).getUsers()
        assertThat(response, `is`(TestHelperObject.userList))
    }


    @Test
    fun `network call returns success`() = mainCoroutineRule.runBlockingTest {

        `when`(remoteDataSource.getAllUsers()).thenReturn(UIStatus.success(TestHelperObject.userResponse))

        val response = repository.getAllUsersFromRemote().getOrAwaitValue()

        verify(remoteDataSource).getAllUsers()
        assertThat(response, `is`(UIStatus.success(Unit)))
    }


    @Test
    fun `repository saves data`() = mainCoroutineRule.runBlockingTest {
        val argumentCapture = argumentCaptor<List<User>>()

        repository.saveAllUsers(TestHelperObject.userList)
        verify(localDataSource).saveUsers(argumentCapture.capture())

        val subjects = argumentCapture.firstValue
        assertThat(subjects, `is`(TestHelperObject.userList))
    }


}