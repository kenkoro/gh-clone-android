package com.kenkoro.practice.githubClone

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.get
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kenkoro.practice.githubClone.core.navigation.Screen
import com.kenkoro.practice.githubClone.reposViewer.data.storage.KeyValueStorage
import com.kenkoro.projects.githubClone.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {
  @get:Rule
  val hiltRule = HiltAndroidRule(this)

  @Inject
  lateinit var keyValueStorage: KeyValueStorage

  @Before
  fun setUp() {
    hiltRule.inject()
  }

  @Test
  fun `should be inflated when created`() {
    val scenario = ActivityScenario.launch(MainActivity::class.java)

    scenario.moveToState(Lifecycle.State.CREATED)

    scenario.onActivity { activity ->
      val root = activity.findViewById<ConstraintLayout>(R.id.mainRoot)
      assert(root.isShown)
    }
  }

  @Test
  fun `should configure the graph when created`() {
    val configuration =
      mapOf(
        "Auth Screen" to Screen.Auth.route,
        "Repos List Screen" to Screen.ReposList.route,
        "Repo Details Screen" to Screen.RepoDetails.route,
      )
    val scenario = ActivityScenario.launch(MainActivity::class.java)

    scenario.moveToState(Lifecycle.State.CREATED)

    scenario.onActivity { activity ->
      val navController = activity.navController()
      val graph = navController.graph

      configuration.forEach { (expectedLabel, route) ->
        assertEquals(expectedLabel, graph[route].label)
      }
    }
  }

  @Test
  fun `should start on the auth screen when no token stored`() {
    every { keyValueStorage.retrieveToken() } returns ""
    val expectedRoute = Screen.Auth.route
    val scenario = ActivityScenario.launch(MainActivity::class.java)

    scenario.moveToState(Lifecycle.State.CREATED)

    scenario.onActivity { activity ->
      val navController = activity.navController()

      verify(exactly = 1) { keyValueStorage.retrieveToken() }
      assertEquals(expectedRoute, navController.currentDestination?.route)
    }
  }

  @Test
  fun `should start on the repos-list screen when a token found`() {
    every { keyValueStorage.retrieveToken() } returns "token"
    val expectedRoute = Screen.ReposList.route
    val scenario = ActivityScenario.launch(MainActivity::class.java)

    scenario.moveToState(Lifecycle.State.CREATED)

    scenario.onActivity { activity ->
      val navController = activity.navController()

      verify(exactly = 1) { keyValueStorage.retrieveToken() }
      assertEquals(expectedRoute, navController.currentDestination?.route)
    }
  }

  private fun MainActivity.navController(): NavController {
    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
    return navHostFragment.navController
  }
}