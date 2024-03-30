@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.easyfood"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.easyfood"
        minSdk = 24
        targetSdk = 33
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    val navVersion = "2.7.7"
    val retrofitVersion = "2.10.0"
    val lifecycleVersion = "2.7.0"
    val archVersion = "1.1.0"
    val intuitVersion = "1.1.0"
    val gifVersion = "1.2.28"
    val glideVersion = "4.16.0"
    val roomVersion = "2.6.1"
    val lottieVersion = "6.4.0"

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //Retrofit & GSON Converter
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    //View Model & Live Data
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("android.arch.lifecycle:extensions:$archVersion")

    //Intuit
    implementation("com.intuit.sdp:sdp-android:$intuitVersion")
    implementation("com.intuit.ssp:ssp-android:$intuitVersion")

    //Gif
    implementation("pl.droidsonroids.gif:android-gif-drawable:$gifVersion")

    //Glide
    implementation("com.github.bumptech.glide:glide:$glideVersion")

    //Room
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$roomVersion")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")

    //Lottie
    implementation("com.airbnb.android:lottie:$lottieVersion")
}