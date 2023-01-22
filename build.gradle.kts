// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("org.jetbrains.kotlin.android") version ("1.7.10") apply false       /*xx  Kotlin version  ( project ) */
    id("com.android.application") version ("7.3.0") apply false
    id("com.android.library") version ("7.3.0") apply false
}

buildscript {
    val kotlin_version by extra("1.8.0")

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        val nav_version = "2.5.2"

        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.43.2")
    }
    repositories {
        mavenCentral()
    }
}
