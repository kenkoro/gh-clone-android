plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.jetbrains.kotlin.android) apply false
  alias(libs.plugins.google.dagger.hilt.android) apply false
  alias(libs.plugins.google.devtools.ksp) apply false
  alias(libs.plugins.jetbrains.kotlin.serialization) apply false
  alias(libs.plugins.jlleitschuh.gradle.ktlint) apply false
}