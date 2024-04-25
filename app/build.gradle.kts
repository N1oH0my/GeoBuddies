plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    //id("com.google.dagger.hilt.android")
    //id("com.google.dagger.hilt.android") version "2.44" apply false
}

android {
    namespace = "com.surf2024.geobuddies"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.surf2024.geobuddies"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    /*packagingOptions {
        pickFirst("META-INF/gradle/incremental.annotation.processors")
    }*/
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.viewbindingpropertydelegate.full)
    implementation(libs.dagger)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.parent)
    implementation(libs.cicerone)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    //kapt("com.google.dagger:hilt-android-compiler:2.44")
    kapt(libs.androidx.room.compiler)
    implementation(libs.rxandroid)
    implementation(libs.androidx.room.runtime)

    implementation(libs.androidx.room.rxjava3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
kapt {
    correctErrorTypes = true
}
