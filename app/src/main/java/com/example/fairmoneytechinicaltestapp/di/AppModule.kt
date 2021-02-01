package com.example.fairmoneytechinicaltestapp.di

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.example.fairmoneytechinicaltestapp.BuildConfig.BASE_URL
import com.example.fairmoneytechinicaltestapp.data.datasource.local.LocalDatabase
import com.example.fairmoneytechinicaltestapp.data.datasource.local.UserDatabase
import com.example.fairmoneytechinicaltestapp.data.datasource.local.UserDatabaseImpl
import com.example.fairmoneytechinicaltestapp.data.datasource.local.dao.UserDao
import com.example.fairmoneytechinicaltestapp.data.datasource.remote.APIService
import com.example.fairmoneytechinicaltestapp.data.datasource.remote.FetchRemoteData
import com.example.fairmoneytechinicaltestapp.data.datasource.remote.FetchRemoteDataImpl
import com.example.fairmoneytechinicaltestapp.data.datasource.repository.UserRepository
import com.example.fairmoneytechinicaltestapp.data.datasource.repository.UserRepositoryImpl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun providesUserDao(@ApplicationContext context: Context): UserDao {
        return UserDatabase.getDatabase(context).userDao()
    }

    @Provides
    @Singleton
    fun providesRepository(
        remoteSource: FetchRemoteData,
        localSource: LocalDatabase
    ): UserRepository {
        return UserRepositoryImpl(remoteSource, localSource)
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(service: APIService): FetchRemoteData {
        return FetchRemoteDataImpl(service)
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    @Singleton
    @Provides
    fun providesLocalDataSource(
        userDao: UserDao,
    ): LocalDatabase {
        return UserDatabaseImpl(userDao)
    }


    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(interceptor)
        }

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .client(builder.build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()
    }

}