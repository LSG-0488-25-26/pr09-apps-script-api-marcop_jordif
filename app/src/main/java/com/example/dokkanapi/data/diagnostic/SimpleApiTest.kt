package com.example.dokkanapi.data.diagnostic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

object SimpleApiTest {

    suspend fun testApi() {
        withContext(Dispatchers.IO) {
            try {
                println("=== TEST SIMPLE API ===")

                val url = "https://script.google.com/macros/s/AKfycbxLAvVdpj-bsPdg5VtYxPX0bS5CuygQOcp2Tc81SfE4ddag9smXF8WBAGcQXssYsESHsg/exec?action=getAllCards"

                println("URL: $url")

                val client = OkHttpClient.Builder()
                    .followRedirects(true)
                    .build()

                val request = Request.Builder()
                    .url(url)
                    .header("Accept", "application/json")
                    .build()

                val response = client.newCall(request).execute()

                println("Codi resposta: ${response.code}")
                println("Missatge: ${response.message}")
                println("Headers: ${response.headers}")

                val bodyString = response.body?.string()

                println("=== CONTINGUT COMPLET ===")
                println(bodyString)
                println("=== FI CONTINGUT ===")

                if (bodyString != null) {
                    println("Longitud: ${bodyString.length} caracters")
                    println("Primer caracter: '${bodyString.firstOrNull()}' (codi: ${bodyString.firstOrNull()?.code})")
                    println("Comença amb <: ${bodyString.trimStart().startsWith("<")}")
                    println("Comença amb {: ${bodyString.trimStart().startsWith("{")}")
                }

            } catch (e: Exception) {
                println("ERROR: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}