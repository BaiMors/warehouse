plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler) //плагин для версии котлин 2.ч и более или типа того

   kotlin("plugin.serialization") version "2.0.20"
/*     id("kotlin-kapt")*/
}

android {
    namespace = "com.example.warehouse"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.warehouse"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
/*        kapt {
            arguments {arg("room.schemaLocation", "$projectDir/schemas")}
        }*/
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

/*    implementation ("androidx.room:room-runtime:2.5.0") // Библиотека "Room"
    kapt ("androidx.room:room-compiler:2.5.0") // Кодогенератор
    implementation ("androidx.room:room-ktx:2.5.0") // Дополнительно для Kotlin Coroutines, Kotlin Flows*/

    implementation(platform("io.github.jan-tennert.supabase:bom:3.0.0")) //основа супабейз???
    implementation("io.github.jan-tennert.supabase:postgrest-kt") //для поддержки постгри
    implementation("io.github.jan-tennert.supabase:auth-kt") //для встр авторизации супабейз
    implementation("io.github.jan-tennert.supabase:realtime-kt") //для рилтайма в таблицах супабейз
    implementation("io.github.jan-tennert.supabase:storage-kt:3.0.2") //для storage

    implementation("io.ktor:ktor-client-android:3.0.0") //для подключения

    implementation("io.coil-kt:coil-compose:2.4.0")//для отрисовки картинок

    implementation(libs.androidx.ui.text.google.fonts) //для гугл шрифтов
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.foundation.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}