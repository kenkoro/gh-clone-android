@file:Suppress("UnstableApiUsage")

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
  alias(libs.plugins.google.dagger.hilt.android)
  alias(libs.plugins.google.devtools.ksp)
  alias(libs.plugins.jetbrains.kotlin.serialization)
  alias(libs.plugins.jlleitschuh.gradle.ktlint)
}

subprojects {
  apply(plugin = "org.jlleitschuh.gradle.ktlint")

  configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    android.set(true)
    verbose.set(true)
  }
}

android {
  namespace = "com.kenkoro.projects.githubClone"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.kenkoro.projects.githubClone"
    minSdk = 21
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
      buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
    }
    debug {
      buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
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
    buildConfig = true
  }
  testOptions {
    unitTests {
      // See https://developer.android.com/reference/tools/gradle-api/8.3/com/android/build/api/dsl/UnitTestOptions#isIncludeAndroidResources()
      isIncludeAndroidResources = true
      all { test ->
        test.systemProperties["robolectric.logging.enabled"] = "true"
      }
    }
  }
}

dependencies {
  ksp(libs.hilt.android.compiler)
  implementation(libs.bundles.dagger.hilt)
  implementation(libs.bundles.network)
  implementation(libs.bundles.androidx)
  implementation(libs.material)
  debugImplementation(libs.androidx.fragment.testing.manifest)
  debugImplementation(libs.androidx.fragment.ktx)
  testImplementation(libs.bundles.testing)
  androidTestImplementation(libs.bundles.android.testing)
}