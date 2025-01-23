package com.kenkoro.practice.githubClone.reposViewer.data.storage

import android.content.Context
import com.kenkoro.projects.githubClone.R
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class KeyValueStorage(
  private val appContext: Context,
) {
  private val sharedPref =
    appContext.getSharedPreferences(
      appContext.getString(
        R.string.pat_preference,
      ),
      Context.MODE_PRIVATE,
    )
  private val mutex = Mutex()

  @Volatile
  var authToken: String? = null
    private set

  /**
   * Saves a Github PAT locally. It's not recommended to use it on the UI thread.
   *
   * @sample com.kenkoro.practice.githubClone.reposViewer.data.networking.RemoteAppRepository.signIn
   */
  suspend fun saveToken(authToken: String) {
    this.authToken = authToken
    mutex.withLock {
      with(sharedPref.edit()) {
        putString(appContext.getString(R.string.saved_token_key), authToken)
        apply()
      }
    }
  }

  fun retrieveToken(): String {
    return if (authToken != null) {
      authToken ?: EMPTY_TOKEN
    } else {
      sharedPref.getString(appContext.getString(R.string.saved_token_key), EMPTY_TOKEN)
        ?: EMPTY_TOKEN
    }
  }

  companion object {
    private const val EMPTY_TOKEN = ""
  }
}