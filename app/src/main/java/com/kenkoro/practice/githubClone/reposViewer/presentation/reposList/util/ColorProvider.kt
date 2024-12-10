package com.kenkoro.practice.githubClone.reposViewer.presentation.reposList.util

import android.content.Context
import com.kenkoro.projects.githubClone.R

class ColorProvider(
  private val appContext: Context,
) {
  fun getColor(language: String?): Int {
    return with(appContext) {
      when (language.lowercaseOrNull()) {
        getString(R.string.lang_color_js).lowercase() -> R.color.javascript
        getString(R.string.lang_color_java).lowercase() -> R.color.java
        getString(R.string.lang_color_kotlin).lowercase() -> R.color.kotlin
        getString(R.string.lang_color_css).lowercase() -> R.color.css
        getString(R.string.lang_color_c).lowercase() -> R.color.c
        else -> R.color.white
      }
    }
  }
}

fun String?.lowercaseOrNull(): String = this?.lowercase() ?: ""