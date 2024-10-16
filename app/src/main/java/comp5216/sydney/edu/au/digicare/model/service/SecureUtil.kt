package comp5216.sydney.edu.au.digicare.model.service

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class SecurityUtil {

    private val algorithm = "AES/CBC/PKCS5Padding"
    private val keySize = 256

    // Generate AES key
    private fun generateKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(keySize)
        return keyGen.generateKey()
    }

    // Encrypt data using AES encryption
    fun encryptData(plainText: String, secretKey: SecretKey): Pair<String, String> {
        val cipher = Cipher.getInstance(algorithm)
        val iv = ByteArray(16)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))
        val encryptedBytes = cipher.doFinal(plainText.toByteArray())
        val encryptedData = Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        val ivString = Base64.encodeToString(iv, Base64.DEFAULT)
        return Pair(encryptedData, ivString)
    }

    // Decrypt data using AES encryption
    fun decryptData(encryptedData: String, secretKey: SecretKey, ivString: String): String {
        val cipher = Cipher.getInstance(algorithm)
        val iv = Base64.decode(ivString, Base64.DEFAULT)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
        val decryptedBytes = cipher.doFinal(Base64.decode(encryptedData, Base64.DEFAULT))
        return String(decryptedBytes)
    }

    // Convert key to Base64 string for storage
    // Save this key to txt file or somewhere safe
    fun keyToString(secretKey: SecretKey): String {
        return Base64.encodeToString(secretKey.encoded, Base64.DEFAULT)
    }

    // Convert Base64 string back to key
    // Convert the key back to original form
    fun stringToKey(keyString: String): SecretKey {
        val decodedKey = Base64.decode(keyString, Base64.DEFAULT)
        return SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")
    }

    // Example how to use:
    // val securityUtil = SecurityUtil()

    // Generate a new AES key (store it securely)
    // val secretKey = securityUtil.generateKey()

    // Encrypt some data
    // val encryptedPair = securityUtil.encryptData("Sensitive Data", secretKey) --> This return a pair (encryptedData, ivString)
    // get encryptedData by using encryptedPair.first
    // get ivString by using encryptedPair.second

    // Decrypt the data
    // We have save the encrypted Data, secretKey and ivString, we can just use retrieve it and use to get data back
    // val decryptedData = securityUtil.decryptData(encryptedData, secretKey, ivString)

    //println("Decrypted data: $decryptedData")
}
