package com.kenkoro.practice.githubClone.reposViewer

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.get
import com.kenkoro.practice.githubClone.MainActivity
import com.kenkoro.practice.githubClone.core.navigation.Screen
import com.kenkoro.projects.githubClone.R
import com.kenkoro.projects.githubClone.databinding.ActivityMainBinding
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {
  @Test
  fun shouldBeBound_WhenFirstCreated() {
    val controller = Robolectric.buildActivity(MainActivity::class.java)

    controller.setup()

    val activity = controller.get()
    val binding = ActivityMainBinding.inflate(activity.layoutInflater)
    assertNotNull(binding)
  }

  @Test
  fun shouldConfigureGraph_WhenFirstCreated() {
    val controller = Robolectric.buildActivity(MainActivity::class.java)

    controller.setup()

    val navHostFragment =
      controller.get().supportFragmentManager.findFragmentById(
        R.id.navHostFragment,
      ) as NavHostFragment
    val graph = navHostFragment.navController.graph
    assertEquals("Auth Screen", graph[Screen.Auth.route].label)
    assertEquals("Repos List Screen", graph[Screen.ReposList.route].label)
    assertEquals("Repo Details Screen", graph[Screen.RepoDetails.route].label)
  }
}