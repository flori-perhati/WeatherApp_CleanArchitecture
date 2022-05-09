package com.example.weatherapp.utility.key.store

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.example.weatherapp.utility.constant.KeyStoreKeys
import java.io.IOException
import java.security.*
import javax.crypto.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EnCryptor @Inject constructor() {

    @Throws(
        UnrecoverableEntryException::class,
        NoSuchAlgorithmException::class,
        KeyStoreException::class,
        NoSuchProviderException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        IOException::class,
        InvalidAlgorithmParameterException::class,
        SignatureException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )

    fun encryptText(alias: String, textToEncrypt: String): Pair<ByteArray,ByteArray> {
        val cipher: Cipher = Cipher.getInstance(KeyStoreKeys.TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(alias))
        cipher.doFinal(textToEncrypt.toByteArray(charset("UTF-8"))).also { data ->
            return Pair(first = data, second = cipher.iv)
        }
    }

    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchProviderException::class,
        InvalidAlgorithmParameterException::class
    )
    private fun getSecretKey(alias: String): SecretKey {
        val keyGenerator: KeyGenerator = KeyGenerator
            .getInstance(KeyProperties.KEY_ALGORITHM_AES, KeyStoreKeys.ANDROID_KEY_STORE)
        keyGenerator.init(
            KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
        )
        return keyGenerator.generateKey()
    }

}