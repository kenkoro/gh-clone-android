package com.kenkoro.practice.githubClone.reposViewer.data.storage

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kenkoro.projects.githubClone.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class KeyValueStorageTest {
  @get:Rule
  val hiltRule = HiltAndroidRule(this)

  private lateinit var keyValueStorage: KeyValueStorage
  private lateinit var app: HiltTestApplication
  private lateinit var context: Context

  @Before
  fun setUp() {
    hiltRule.inject()
    app = ApplicationProvider.getApplicationContext()
    context = app.applicationContext
    keyValueStorage = KeyValueStorage(context)
  }

  @Test
  fun `should save and retrieve a pat`() =
    runTest {
      val expectedToken = "pat"
      val expectedCachedToken = "pat"

      keyValueStorage.saveToken(expectedToken)
      val actualToken = keyValueStorage.retrieveToken()

      testScheduler.advanceUntilIdle()
      assertEquals(expectedToken, actualToken)
      assertEquals(expectedCachedToken, keyValueStorage.authToken)
    }

  @Test
  fun `should retrieve a pat from shared pref`() =
    runTest {
      val expectedToken = "pat"
      val expectedCachedToken = null
      val sharedPref =
        context.getSharedPreferences(
          context.getString(
            R.string.pat_preference,
          ),
          Context.MODE_PRIVATE,
        )
      val mutex = Mutex()
      mutex.withLock {
        with(sharedPref.edit()) {
          putString(context.getString(R.string.saved_token_key), expectedToken)
          apply()
        }
      }

      val actualToken = keyValueStorage.retrieveToken()

      testScheduler.advanceUntilIdle()
      assertEquals(expectedToken, actualToken)
      assertEquals(expectedCachedToken, keyValueStorage.authToken)
    }

  @Test
  fun `should retrieve an empty pat when there were no savings`() {
    val expectedToken = ""

    val actualToken = keyValueStorage.retrieveToken()

    assertEquals(expectedToken, actualToken)
  }
}