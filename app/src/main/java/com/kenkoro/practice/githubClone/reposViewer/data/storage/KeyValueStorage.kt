package com.kenkoro.practice.githubClone.reposViewer.data.storage

import android.content.Context
import com.kenkoro.projects.githubClone.R
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class KeyValueStorage(
  private val context: Context,
) {
  private val sharedPref =
    context.getSharedPreferences(
      context.getString(
        R.string.pat_preference,
      ),
      Context.MODE_PRIVATE,
    )
  private val mutex = Mutex()

  @Volatile
  var authToken: String? = null
    private set

  suspend fun saveToken(authToken: String) {
    this.authToken = authToken
    mutex.withLock {
      with(sharedPref.edit()) {
        putString(context.getString(R.string.saved_token_key), authToken)
        apply()
      }
    }
  }

  fun retrieveToken(): String {
    return if (authToken != null) {
      authToken ?: EMPTY_TOKEN
    } else {
      sharedPref.getString(context.getString(R.string.saved_token_key), EMPTY_TOKEN)
        ?: EMPTY_TOKEN
    }
  }

  companion object {
    private const val EMPTY_TOKEN = ""
  }
}