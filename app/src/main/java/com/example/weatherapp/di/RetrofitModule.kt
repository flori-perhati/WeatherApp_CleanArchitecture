package com.example.weatherapp.di

import androidx.databinding.library.BuildConfig
import com.example.weatherapp.utility.constant.Server
import com.example.weatherapp.utility.network.InternetConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        loggingInterceptor.redactHeader("Authorization")
        loggingInterceptor.redactHeader("Cookie")
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        internetConnectionInterceptor: InternetConnectionInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(internetConnectionInterceptor)

        if (BuildConfig.DEBUG)
            okHttpClient.addInterceptor(httpLoggingInterceptor)

        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Server.BASE_URL)
            .client(okHttpClient)
            .build()
    }
}