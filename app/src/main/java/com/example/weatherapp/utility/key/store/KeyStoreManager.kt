package com.example.weatherapp.utility.key.store

import com.example.weatherapp.utility.constant.KeyStoreKeys
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyStoreManager @Inject constructor(
    enCryptor: EnCryptor,
    deCryptor: DeCryptor
) {

    private val _weatherApiKey = enCryptor.encryptText(
        alias = KeyStoreKeys.WEATHER_API_KEY,
        textToEncrypt = "92a5052e96c8db0e7b09b39849e98df6"
    )

    val weatherApiKey = deCryptor.decryptData(
        alias = KeyStoreKeys.WEATHER_API_KEY,
        encryptedData = _weatherApiKey.first,
        encryptionIv = _weatherApiKey.second
    )
}