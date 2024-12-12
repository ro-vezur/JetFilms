import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id ("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    alias(libs.plugins.kotlin.serialization)
    id("com.google.gms.google-services")
}

tasks{
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += listOf("-Xskip-prerelease-check")
        }
    }
}

android {
    namespace = "com.example.jetfilms"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.jetfilms"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val compose_version = "1.7.4"
    val hilt = "2.51.1"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // Auto scaling
    implementation ("com.intuit.sdp:sdp-android:1.0.6")

    // material compose
    implementation ("androidx.compose.material:material-icons-extended:$compose_version")

    // glass effect
    implementation("com.github.prime-zs.toolkit:core-ktx:2.0.2-alpha")

    // Coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:$hilt")
    kapt("com.google.dagger:hilt-compiler:$hilt")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //glass morphism effect
    implementation("dev.chrisbanes.haze:haze-jetpack-compose:0.4.1")

    //gson
    implementation("com.google.code.gson:gson:2.8.8")

    //pager
    implementation("androidx.compose.foundation:foundation:1.4.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    //Gson converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //paging
    val paging_version = "3.1.1"

    implementation("androidx.paging:paging-runtime:$paging_version")
    implementation("androidx.paging:paging-compose:1.0.0-alpha17")

    // room Local Db
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    // youtube video player
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.1.0")

    //Firebase
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)

    //live data
    implementation("androidx.compose.runtime:runtime-livedata:1.7.6")
}