package com.kenkoro.practice.githubClone.reposViewer.presentation.auth

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.textfield.TextInputEditText
import com.kenkoro.practice.githubClone.GithubCloneApp
import com.kenkoro.practice.githubClone.core.navigation.Screen
import com.kenkoro.practice.githubClone.reposViewer.createTestableGraph
import com.kenkoro.practice.githubClone.reposViewer.destination
import com.kenkoro.practice.githubClone.reposViewer.launchFragmentInHiltContainer
import com.kenkoro.projects.githubClone.R
import com.kenkoro.projects.githubClone.databinding.AuthFragmentBinding
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
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
        destination(route = Screen.ReposList.route)
      }
  }

  private fun TextInputEditText.typeText(text: String) {
    setText(text)
    setSelection(text.length)
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

  @Ignore("Configure Hilt with a mocked repository")
  @Test
  fun shouldGoToReposList_WhenProperTokenProvided() {
    launchFragmentInHiltContainer<AuthFragment>(themeResId = R.style.Theme_GithubApp) {
      val binding = AuthFragmentBinding.inflate(layoutInflater)
      Navigation.setViewNavController(requireView(), navController)

      with(binding) {
        tietAuthToken.typeText("token")
        btnAuth.performClick()
      }

      assertEquals(Screen.ReposList.route, navController.currentDestination?.route)
    }
  }
}