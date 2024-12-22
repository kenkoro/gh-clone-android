package com.kenkoro.practice.githubClone.reposViewer.presentation.auth

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kenkoro.practice.githubClone.GithubCloneApp
import com.kenkoro.practice.githubClone.core.navigation.Screen
import com.kenkoro.practice.githubClone.reposViewer.createTestableGraph
import com.kenkoro.practice.githubClone.reposViewer.destination
import com.kenkoro.practice.githubClone.reposViewer.launchFragmentInHiltContainer
import com.kenkoro.projects.githubClone.R
import com.kenkoro.projects.githubClone.databinding.AuthFragmentBinding
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthFragmentTest {
  private lateinit var app: GithubCloneApp
  private lateinit var navController: NavController

  @Before
  fun setUp() {
    app = ApplicationProvider.getApplicationContext()
    navController =
      TestNavHostController(app).createTestableGraph(Screen.Auth.route) {
        destination(route = Screen.Auth.route)
      }
  }

  @Test
  fun shouldStayOnAuth_WhenTheresNoTokenProvided() {
    launchFragmentInHiltContainer<AuthFragment>(themeResId = R.style.Theme_GithubApp) {
      val binding = AuthFragmentBinding.inflate(layoutInflater)
      Navigation.setViewNavController(requireView(), navController)

      binding.btnAuth.performClick()

      assertEquals(Screen.Auth.route, navController.currentDestination?.route)
    }
  }
}