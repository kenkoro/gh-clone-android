package com.kenkoro.practice.githubClone

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.get
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kenkoro.practice.githubClone.core.navigation.Screen
import com.kenkoro.projects.githubClone.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
class MainActivityTest {
  @get:Rule
  val hiltRule = HiltAndroidRule(this)

  @Before
  fun setUp() {
    hiltRule.inject()
  }

  @Test
  fun shouldBeInflated_WhenCreated() {
    val scenario = ActivityScenario.launch(MainActivity::class.java)

    scenario.moveToState(Lifecycle.State.CREATED)

    scenario.onActivity { activity ->
      val root = activity.findViewById<ConstraintLayout>(R.id.mainRoot)
      assert(root.isShown)
    }
  }

  @Test
  fun shouldConfigureGraph_WhenCreated() {
    val configuration = mapOf(
      "Auth Screen" to Screen.Auth.route,
      "Repos List Screen" to Screen.ReposList.route,
      "Repo Details Screen" to Screen.RepoDetails.route,
    )
    val expectedStartDestination = Screen.Auth.route
    val scenario = ActivityScenario.launch(MainActivity::class.java)

    scenario.moveToState(Lifecycle.State.CREATED)

    scenario.onActivity { activity ->
      val navHostFragment =
        activity.supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
      val navController = navHostFragment.navController
      val graph = navController.graph

      assertEquals(expectedStartDestination, navController.currentDestination?.route)
      configuration.forEach { (expectedLabel, route) ->
        assertEquals(expectedLabel, graph[route].label)
      }
    }
  }
}