@file:OptIn(ExperimentalCoroutinesApi::class)

package com.kenkoro.practice.githubClone.reposViewer.presentation.reposList

import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kenkoro.practice.githubClone.HiltTestActivity
import com.kenkoro.practice.githubClone.core.domain.util.Result
import com.kenkoro.practice.githubClone.launchFragmentInHiltContainer
import com.kenkoro.practice.githubClone.reposViewer.domain.AppRepository
import com.kenkoro.practice.githubClone.reposViewer.domain.Repo
import com.kenkoro.projects.githubClone.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ReposListFragmentTest {
  private val testDispatcher = StandardTestDispatcher()

  @get:Rule
  val hiltRule = HiltAndroidRule(this)

  @Inject
  lateinit var appRepository: AppRepository

  private fun HiltTestActivity.isReposListShown(): Boolean {
    val reposList = findViewById<RecyclerView>(R.id.rvRepos)
    val loadingProgressBar = findViewById<ProgressBar>(R.id.pbLoadingBar)
    val warningContainer = findViewById<RelativeLayout>(R.id.rlWarningContainer)
    val retryButton = findViewById<AppCompatButton>(R.id.btnRetry)

    return reposList.isShown and
      loadingProgressBar.isGone and
      warningContainer.isGone and
      retryButton.isGone
  }

  @Before
  fun setUp() {
    hiltRule.inject()
    Dispatchers.setMain(testDispatcher)
  }

  @Test
  fun shouldBeInflated_WhenCreated() {
    launchFragmentInHiltContainer<ReposListFragment> {
      val root = requireActivity().findViewById<ConstraintLayout>(R.id.mainRoot)
      assert(root.isShown)
    }
  }

  @Test
  fun shouldShowReposList_WhenInitialLoadIsSuccessful() {
    val successfulResponse = Result.Success(
      listOf(
        Repo(id = 1, name = "Repo's name", description = null, language = null),
      )
    )
    coEvery { appRepository.getRepositories() } returns successfulResponse

    launchFragmentInHiltContainer<ReposListFragment> {
      requireActivity().findViewById<>()
    }
  }
}