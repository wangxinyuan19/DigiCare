package comp5216.sydney.edu.au.digicare.model.service

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.security.SecureRandom

class SecurityUtil {

    private val algorithm = "AES/CBC/PKCS5Padding"
    private val keySize = 256

    // Generate AES key
    fun generateKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(keySize)
        return keyGen.generateKey()
    }

    // Encrypt data using AES encryption
    fun encryptData(plainText: String, secretKey: SecretKey): Pair<String, String> {
        val cipher = Cipher.getInstance(algorithm)

        // Generate a random IV
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)  // SecureRandom ensures a unique IV every time

        // Initialize the cipher with encryption mode, key, and IV
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))
        val encryptedBytes = cipher.doFinal(plainText.toByteArray())

        // Encode encrypted data and IV to Base64 for safe storage
        val encryptedData = Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        val ivString = Base64.encodeToString(iv, Base64.DEFAULT)

        return Pair(encryptedData, ivString)  // Return both encrypted data and IV
    }

    // Decrypt data using AES encryption
    fun decryptData(encryptedData: String, secretKey: SecretKey, ivString: String): String {
        val cipher = Cipher.getInstance(algorithm)

        // Decode the IV from the stored Base64 string
        val iv = Base64.decode(ivString, Base64.DEFAULT)

        // Initialize the cipher with decryption mode, key, and IV
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
        val decryptedBytes = cipher.doFinal(Base64.decode(encryptedData, Base64.DEFAULT))

        return String(decryptedBytes)  // Convert decrypted bytes back to a string
    }

    // Convert key to Base64 string for storage
    fun keyToString(secretKey: SecretKey): String {
        return Base64.encodeToString(secretKey.encoded, Base64.DEFAULT)
    }

    // Convert Base64 string back to SecretKey
    fun stringToKey(keyString: String): SecretKey {
        val decodedKey = Base64.decode(keyString, Base64.DEFAULT)
        return SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")
    }
}
