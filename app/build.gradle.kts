plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)  // Plugin per convertir JSON a objectes
}

android {
    namespace = "com.example.dokkanapi"
    compileSdk = 36  // Versio del SDK per compilar

    defaultConfig {
        applicationId = "com.example.dokkanapi"
        minSdk = 24  // Versio minima d'Android suportada
        targetSdk = 36  // Versio per la qual esta optimitzada
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false  // No ofusquem el codi
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"  // Versio de Java per Kotlin
    }

    buildFeatures {
        compose = true  // Activem Jetpack Compose
    }
}

dependencies {
    // ===== LLIBRERIES PER LA API =====

    // Retrofit - per fer peticions HTTP a la teva API
    implementation(libs.retrofit.core)

    // Kotlin Serialization - per convertir les respostes JSON a objectes Kotlin
    implementation(libs.kotlinx.serialization.json)

    // Connecta Retrofit amb Kotlin Serialization
    implementation(libs.retrofit.serialization.converter)

    // OkHttp Logging - per veure les peticions al Logcat (depuracio)
    implementation(libs.okhttp.logging)

    // Coroutines - per fer crides asincrones sense bloquejar la UI
    implementation(libs.kotlinx.coroutines.android)

    // ===== VIEWMODEL I COMPOSE =====

    // ViewModel per guardar l'estat de la UI
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Funcions extra per lifecycle amb Compose
    implementation(libs.androidx.lifecycle.runtime.compose)

    // ===== ANDROID BASIC =====

    // Funcionalitats basiques d'Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // ===== JETPACK COMPOSE (UI) =====

    // Bill of Materials - gestiona versions de Compose
    implementation(platform(libs.androidx.compose.bom))

    // Llibreries basiques de Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.material3)

    // ===== FIREBASE (si ho necessites) =====
    // Aquesta llibreria es per distribuir l'app, normalment no es necessita
    // Si no la uses, pots treure-la
    // implementation(libs.firebase.appdistribution.gradle)

    // ===== TEST (per proves) =====

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}