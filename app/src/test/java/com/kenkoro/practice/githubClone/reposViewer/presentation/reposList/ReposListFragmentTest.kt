package com.kenkoro.practice.githubClone.reposViewer.presentation.reposList

import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kenkoro.practice.githubClone.core.domain.util.NetworkError
import com.kenkoro.practice.githubClone.core.domain.util.Result
import com.kenkoro.practice.githubClone.launchFragmentInHiltContainer
import com.kenkoro.practice.githubClone.reposViewer.domain.AppRepository
import com.kenkoro.practice.githubClone.reposViewer.domain.Repo
import com.kenkoro.projects.githubClone.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ReposListFragmentTest {
  private val testDispatcher = StandardTestDispatcher()
  private val oneRepoResponse =
    Result.Success(
      listOf(
        Repo(id = 1, name = "Repo's name", description = null, language = null),
      ),
    )

  @get:Rule
  val hiltRule = HiltAndroidRule(this)

  @Inject
  lateinit var appRepository: AppRepository

  @Before
  fun setUp() {
    hiltRule.inject()
    every { appRepository.defaultDispatcher } returns testDispatcher
  }

  @Test
  fun shouldBeInflated_WhenCreated() {
    launchFragmentInHiltContainer<ReposListFragment> {
      val root = requireActivity().findViewById<ConstraintLayout>(R.id.mainRoot)
      assert(root.isShown)
    }
  }

  @Test
  fun shouldShowReposListScreen_WhenInitialLoadIsSuccessful() {
    val expectedNumberOfRepos = 1
    coEvery { appRepository.getRepositories() } returns oneRepoResponse

    launchFragmentInHiltContainer<ReposListFragment> {
      testDispatcher.scheduler.advanceUntilIdle()
      requireActivity().plainReposAssertion(expectedNumberOfRepos)
    }
  }

  @Test
  fun shouldShowWarningScreen_WhenInitialLoadIsFailed() {
    val failedResponse = Result.Error(NetworkError.RequiresAuthentication)
    coEvery { appRepository.getRepositories() } returns failedResponse

    launchFragmentInHiltContainer<ReposListFragment> {
      testDispatcher.scheduler.advanceUntilIdle()
      with(requireActivity()) {
        val expectedWarningTitle = getString(R.string.warning_container_other_error_title)
        val warningContainer = findViewById<RelativeLayout>(R.id.rlWarningContainer)
        val retryButton = findViewById<AppCompatButton>(R.id.btnRetry)
        val actualWarningTitle = warningContainer.findViewById<TextView>(R.id.tvWarningTitle).text

        assert(warningContainer.isShown and retryButton.isShown)
        assertEquals(expectedWarningTitle, actualWarningTitle)
      }
    }
  }

  @Test
  fun shouldShowEmptyReposListScreen_WhenInitialLoadIsSuccessful() {
    val emptyResponse = Result.Success(emptyList<Repo>())
    coEvery { appRepository.getRepositories() } returns emptyResponse

    launchFragmentInHiltContainer<ReposListFragment> {
      testDispatcher.scheduler.advanceUntilIdle()
      with(requireActivity()) {
        val expectedWarningTitle = getString(R.string.warning_container_empty_title)
        val warningContainer = findViewById<RelativeLayout>(R.id.rlWarningContainer)
        val retryButton = findViewById<AppCompatButton>(R.id.btnRetry)
        val actualWarningTitle = warningContainer.findViewById<TextView>(R.id.tvWarningTitle).text

        assert(warningContainer.isShown and retryButton.isShown)
        assertEquals(expectedWarningTitle, actualWarningTitle)
      }
    }
  }

  @Test
  fun shouldShowReposListScreen_WhenUserRetries() {
    val expectedNumberOfRepos = 1
    val firstFailedResponse = Result.Error(NetworkError.RequiresAuthentication)
    coEvery { appRepository.getRepositories() } returnsMany
      listOf(
        firstFailedResponse,
        oneRepoResponse,
      )

    launchFragmentInHiltContainer<ReposListFragment> {
      testDispatcher.scheduler.advanceUntilIdle()
      with(requireActivity()) {
        val warningContainer = findViewById<RelativeLayout>(R.id.rlWarningContainer)
        val retryButton = findViewById<AppCompatButton>(R.id.btnRetry)

        assert(warningContainer.isShown and retryButton.isShown)

        retryButton.performClick()

        testDispatcher.scheduler.advanceUntilIdle()
        plainReposAssertion(expectedNumberOfRepos)
      }
    }
  }

  @Ignore("Not yet implemented :)")
  @Test
  fun shouldGoToRepoDetailsScreen_WhenARepoWasClicked() {}

  private fun FragmentActivity.plainReposAssertion(expectedNumberOfRepos: Int) {
    val reposList = findViewById<RecyclerView>(R.id.rvRepos)
    val loadingProgressBar = findViewById<ProgressBar>(R.id.pbLoadingBar)

    assert(reposList.isShown and loadingProgressBar.isGone)
    assertEquals(expectedNumberOfRepos, reposList.adapter?.itemCount)
  }
}