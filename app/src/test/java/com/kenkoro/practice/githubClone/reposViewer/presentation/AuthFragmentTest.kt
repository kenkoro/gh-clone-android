package com.kenkoro.practice.githubClone.reposViewer.presentation

import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.textfield.TextInputEditText
import com.kenkoro.practice.githubClone.core.domain.util.Result
import com.kenkoro.practice.githubClone.core.navigation.Screen
import com.kenkoro.practice.githubClone.launchFragmentInHiltContainer
import com.kenkoro.practice.githubClone.reposViewer.domain.AppRepository
import com.kenkoro.practice.githubClone.reposViewer.domain.UserInfo
import com.kenkoro.practice.githubClone.reposViewer.presentation.auth.AuthFragment
import com.kenkoro.practice.githubClone.reposViewer.presentation.reposList.util.NetworkErrorMessageProvider
import com.kenkoro.projects.githubClone.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.coVerify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class AuthFragmentTest {
  @get:Rule
  val hiltRule = HiltAndroidRule(this)

  @Inject
  lateinit var appRepository: AppRepository

  @Inject
  lateinit var errorMessageProvider: NetworkErrorMessageProvider

  @Before
  fun setUp() {
    hiltRule.inject()
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

  @Ignore("Implement a testable nav controller")
  @Test
  fun shouldGoToReposListScreen_WhenGivenTokenIsValid() {
    val expectedRoute = Screen.ReposList.route
    val successfulResponse = Result.Success(UserInfo(login = "login"))
    coEvery { appRepository.signIn(any()) } returns successfulResponse

    launchFragmentInHiltContainer<AuthFragment> {
      val input = requireActivity().findViewById<TextInputEditText>(R.id.tietAuthToken)
      val authButton = requireActivity().findViewById<RelativeLayout>(R.id.btnAuth)
      input.typeText("valid token")
      authButton.performClick()

      coVerify(exactly = 1) { appRepository.signIn(any()) }
      assertEquals(expectedRoute, findNavController().currentDestination?.route)
    }
  }

  @Ignore("Not yet implemented")
  @Test
  fun shouldShowError_WhenTokenIsEmpty() {
  }

  @Ignore("Not yet implemented")
  @Test
  fun shouldShowError_WhenTokenIsInvalid() {
  }
}