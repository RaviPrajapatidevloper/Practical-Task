package com.ravi.practicaltaskmvvm.di

import android.content.Context
import androidx.room.Room
import com.ravi.practicaltaskmvvm.BuildConfig
import com.ravi.practicaltaskmvvm.R
import com.ravi.practicaltaskmvvm.data.local.RepositoryDao
import com.ravi.practicaltaskmvvm.data.local.RepositoryDatabase
import com.ravi.practicaltaskmvvm.data.network.Api
import com.ravi.practicaltaskmvvm.data.network.ApiManger
import com.ravi.practicaltaskmvvm.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
   fun provideOkHttpClient(): OkHttpClient{
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG){
            okHttpClient.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return okHttpClient.build()
   }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideApiManager(apiService: ApiService,repositoryDao: RepositoryDao): Api {
        return ApiManger(apiService,repositoryDao)
    }


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RepositoryDatabase {
        return Room.databaseBuilder(
            context, RepositoryDatabase::class.java,
            context.getString(R.string.database_name)
        ).build()

    }

    @Provides
    @Singleton
    fun provideRepositoryDao(repositoryDatabase: RepositoryDatabase): RepositoryDao {
        return repositoryDatabase.daoClass()
    }
}