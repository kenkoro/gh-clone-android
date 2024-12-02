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
}

dependencies {
  ksp(libs.hilt.android.compiler)
  implementation(libs.bundles.dagger.hilt)
  implementation(libs.squareup.retrofit2)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.constraintlayout)
  implementation(libs.androidx.navigation.fragment.ktx)
  implementation(libs.androidx.navigation.ui.ktx)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}