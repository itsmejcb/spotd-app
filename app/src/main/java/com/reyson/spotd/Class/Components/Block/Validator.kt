package com.reyson.spotd.Class.Components.Block

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class Validator(private val context: Context) {
    private val TAG = "HarmfulTextValidator"
    private val harmfulWords: MutableList<String> = mutableListOf()

    fun loadHarmfulWords() {
        try {
            val inputStream = context.assets.open("validator.txt")
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    line?.let { harmfulWords.add(it) }
                }
            }
            inputStream.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error loading harmful words", e)
        }
    }

    fun isTextHarmful(text: String): Boolean {
        val lowercaseText = text.toLowerCase()
        for (word in harmfulWords) {
            if (lowercaseText.contains(word.toLowerCase())) {
                return true
            }
        }
        return false
    }
}


//
// import android.content.Context
// import android.content.res.AssetManager
// import android.util.Log
// import java.io.BufferedReader
// import java.io.IOException
// import java.io.InputStreamReader
//
// class Validator(private val context: Context) {
//     private val TAG = "HarmfulTextValidator"
//     private val harmfulWords: MutableList<String> = ArrayList()
//
//     fun loadHarmfulWords() {
//         val assetManager: AssetManager = context.assets
//         try {
//             BufferedReader(InputStreamReader(assetManager.open("validator.txt"))).use { reader ->
//                 var line: String?
//                 while (reader.readLine().also { line = it } != null) {
//                     line?.let { harmfulWords.add(it) }
//                 }
//             }
//         } catch (e: IOException) {
//             Log.e(TAG, "Error loading harmful words", e)
//         }
//     }
//
//     fun isTextHarmful(text: String): Boolean {
//         for (word in harmfulWords) {
//             if (text.toLowerCase().contains(word)) {
//                 return true
//             }
//         }
//         return false
//     }
// }


//
// import android.content.Context
// import android.util.Log
// import java.io.BufferedReader
// import java.io.IOException
// import java.io.InputStreamReader
// import java.util.Locale
//
// class Validator(private val context: Context) {
//     private val harmfulWords: MutableList<String>
//
//     init {
//         harmfulWords = ArrayList()
//     }
//
//     fun loadHarmfulWords() {
//         val assetManager = context.assets
//         try {
//             BufferedReader(InputStreamReader(assetManager.open("validator.txt"))).use { reader ->
//                 var line: String
//                 while (reader.readLine().also { line = it } != null) {
//                     harmfulWords.add(line)
//                 }
//             }
//         } catch (e: IOException) {
//             Log.e(TAG, "Error loading harmful words", e)
//         }
//     }
//
//     fun isTextHarmful(text: String): Boolean {
//         for (word in harmfulWords) {
//             if (text.lowercase(Locale.getDefault()).contains(word)) {
//                 return true
//             }
//         }
//         return false
//     }
//
//     companion object {
//         private const val TAG = "HarmfulTextValidator"
//     }
// }