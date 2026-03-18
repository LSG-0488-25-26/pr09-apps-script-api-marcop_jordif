package com.example.dokkanapi.data.diagnostic

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

object ApiDiagnostic {

    suspend fun diagnosticApi() {
        println("INICI DIAGNOSTIC API")

        val url = "https://script.google.com/macros/s/AKfycbxLAvVdpj-bsPdg5VtYxPX0bS5CuygQOcp2Tc81SfE4ddag9smXF8WBAGcQXssYsESHsg/exec?action=getAllCards"

        println("Prova 1: Sense seguir redireccions")
        val client1 = OkHttpClient.Builder()
            .followRedirects(false)
            .build()

        try {
            val request = Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .build()

            client1.newCall(request).execute().use { response ->
                println("Codi: ${response.code}")
                println("Headers: ${response.headers}")

                if (response.isRedirect) {
                    val location = response.header("Location")
                    println("Redirigeix a: $location")

                    if (location != null) {
                        println("Prova 2: Seguint redireccio a $location")
                        val request2 = Request.Builder()
                            .url(location)
                            .header("Accept", "application/json")
                            .build()

                        client1.newCall(request2).execute().use { response2 ->
                            println("Codi final: ${response2.code}")
                            println("Content-Type: ${response2.header("Content-Type")}")
                            val body = response2.body?.string()
                            println("Primers 500 caracters:")
                            println(body?.substring(0, minOf(500, body.length)))
                        }
                    }
                } else {
                    val body = response.body?.string()
                    println("Primers 500 caracters:")
                    println(body?.substring(0, minOf(500, body.length)))
                }
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}