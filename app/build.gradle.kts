import com.android.build.api.variant.BuildConfigField

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
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
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

androidComponents {
    onVariants {
        it.buildConfigFields.put(
            "API_BASE_URL", BuildConfigField(
                "String", "\"${properties["API_BASE_URL"]}\"", "API_BASE_URL"
            )
        )
    }
}

dependencies {

    val activity_version = "1.9.0"
    val security_version = "1.1.0-alpha03"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(libs.viewbindingpropertydelegate.full)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.parent)
    implementation(libs.cicerone)
    implementation(libs.rxandroid)
    implementation(libs.adapter.rxjava3)
    implementation(libs.gson)
    implementation(libs.circle.view)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Activity ktx
    implementation("androidx.activity:activity-ktx:$activity_version")
    //Encryption
    implementation("androidx.security:security-crypto:$security_version")

}
kapt {
    correctErrorTypes = true
}
