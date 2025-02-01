@file:OptIn(ExperimentalCoroutinesApi::class)

package com.kenkoro.practice.githubClone.reposViewer.presentation.auth

import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kenkoro.practice.githubClone.core.domain.util.NetworkError
import com.kenkoro.practice.githubClone.core.domain.util.Result
import com.kenkoro.practice.githubClone.core.navigation.Screen
import com.kenkoro.practice.githubClone.createTestableGraph
import com.kenkoro.practice.githubClone.fragment
import com.kenkoro.practice.githubClone.launchFragmentInHiltContainer
import com.kenkoro.practice.githubClone.reposViewer.domain.AppRepository
import com.kenkoro.practice.githubClone.reposViewer.domain.UserInfo
import com.kenkoro.practice.githubClone.reposViewer.presentation.reposList.util.NetworkErrorMessageProvider
import com.kenkoro.projects.githubClone.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.shadows.ShadowDialog
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class AuthFragmentTest {
  private val testDispatcher = StandardTestDispatcher()

  @get:Rule
  val hiltRule = HiltAndroidRule(this)

  @Inject
  lateinit var appRepository: AppRepository

  @Inject
  lateinit var errorMessageProvider: NetworkErrorMessageProvider

  @Before
  fun setUp() {
    hiltRule.inject()
    every { appRepository.defaultDispatcher } returns testDispatcher
  }

  private fun TextInputEditText.typeText(text: String) {
    setText(text)
    setSelection(text.length)
  }

  @Test
  fun shouldBeInflated_WhenCreated() {
    launchFragmentInHiltContainer<AuthFragment> {
      val root = requireActivity().findViewById<ConstraintLayout>(R.id.mainRoot)
      assert(root.isShown)
    }
  }

  @Test
  fun shouldGoToReposListScreen_WhenGivenTokenIsValid() {
    val expectedRoute = Screen.ReposList.route
    val successfulResponse = Result.Success(UserInfo(login = "login"))
    coEvery { appRepository.signIn(any()) } returns successfulResponse

    launchFragmentInHiltContainer<AuthFragment> {
      val navController =
        TestNavHostController(requireContext()).createTestableGraph(Screen.Auth.route) {
          fragment(route = Screen.Auth.route)
          fragment(route = Screen.ReposList.route)
        }
      Navigation.setViewNavController(requireView(), navController)
      with(requireActivity()) {
        val input = findViewById<TextInputEditText>(R.id.tietAuthToken)
        val authButton = findViewById<RelativeLayout>(R.id.btnAuth)

        input.typeText("valid token")
        authButton.performClick()

        testDispatcher.scheduler.advanceUntilIdle()
        coVerify(exactly = 1) { appRepository.signIn(any()) }
        assertEquals(expectedRoute, findNavController().currentDestination?.route)
      }
    }
  }

  @Test
  fun shouldShowError_WhenGivenTokenIsInvalid() {
    val expectedRoute = Screen.Auth.route
    val failedResponse = Result.Error(NetworkError.RequiresAuthentication)
    coEvery { appRepository.signIn(any()) } returns failedResponse

    launchFragmentInHiltContainer<AuthFragment> {
      val expectedErrorMessage = requireContext().getString(R.string.auth_et_helper)
      val navController =
        TestNavHostController(requireContext()).createTestableGraph(Screen.Auth.route) {
          fragment(route = Screen.Auth.route)
        }
      Navigation.setViewNavController(requireView(), navController)
      with(requireActivity()) {
        val authButton = findViewById<RelativeLayout>(R.id.btnAuth)
        val inputLayout = findViewById<TextInputLayout>(R.id.tilAuthToken)

        authButton.performClick()

        testDispatcher.scheduler.advanceUntilIdle()
        val errorDialog = ShadowDialog.getLatestDialog()
        coVerify(exactly = 1) { appRepository.signIn(any()) }
        assertEquals(expectedRoute, findNavController().currentDestination?.route)
        assertEquals(expectedErrorMessage, inputLayout.error)
        assert(errorDialog.isShowing)
      }
    }
  }
}