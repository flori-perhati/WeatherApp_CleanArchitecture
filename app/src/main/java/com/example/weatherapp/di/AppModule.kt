package com.example.weatherapp.di

import com.example.weatherapp.utility.DefaultDispatchers
import com.example.weatherapp.utility.DispatcherProvider
import com.example.weatherapp.utility.constant.KeyStoreKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.security.KeyStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKeyStore() : KeyStore {
        return KeyStore.getInstance(KeyStoreKeys.ANDROID_KEY_STORE)
    }

    @Provides
    @Singleton
    fun provideDispatcherProvider() : DispatcherProvider {
        return DefaultDispatchers()
    }
}