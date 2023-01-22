plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
}

android {
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }

    compileSdk = 33

    defaultConfig {
        applicationId = "cash.homework"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }

        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        viewBinding = true
    }

    dataBinding {
        android.buildFeatures.dataBinding = true
    }
}

dependencies {
    // Google Material Design
    implementation("com.google.android.material:material:1.7.0")

    // Core
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")

    // UI
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Architecture
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    // Nav
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")

    // Data Binding Runtime (req by  DataBindFragment base class' DB parameter
    implementation("androidx.databinding:databinding-runtime:7.3.0")

    // Dependency Injection - Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    implementation("androidx.test.ext:junit-ktx:1.1.3")
    implementation("com.google.firebase:protolite-well-known-types:18.0.0")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")

    // Network
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")



    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:+")
    implementation("com.squareup.moshi:moshi-adapters:+")


    // Network Debug
    implementation("com.localebro:okhttpprofiler:1.0.8")

    // Picasso - Cached Media Retrieval
    implementation("com.squareup.picasso:picasso:2.71828") {
        exclude(module = "support-annotations")
        exclude(module = "exifinterface")
    }


    // For gifs
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.17")

    // ===================== //
    //      [ Testing ]      //
    // ===================== //
    testImplementation("org.robolectric:robolectric:4.7.3")
    kaptTest("com.google.dagger:hilt-android-compiler:2.44")

    // Google truth for assertion
    testImplementation("com.google.truth:truth:1.1.3")

    // Testing - JUnit
    testImplementation("junit:junit:4.13.2")

    // Testing - Coroutines
    // For runBlockingTest, CoroutineDispatcher etc
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")

    // Testing - Hilt
    testImplementation("com.google.dagger:hilt-android-testing:2.44")
}