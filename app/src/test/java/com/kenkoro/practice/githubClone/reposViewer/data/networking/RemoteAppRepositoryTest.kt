package com.kenkoro.practice.githubClone.reposViewer.data.networking

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kenkoro.practice.githubClone.core.domain.util.NetworkError
import com.kenkoro.practice.githubClone.core.domain.util.Result
import com.kenkoro.practice.githubClone.reposViewer.data.mappers.toRepo
import com.kenkoro.practice.githubClone.reposViewer.data.networking.dto.RepoDto
import com.kenkoro.practice.githubClone.reposViewer.data.storage.KeyValueStorage
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class RemoteAppRepositoryTest {
  @get:Rule
  val hiltRule = HiltAndroidRule(this)

  @Inject
  lateinit var githubApi: GithubApi

  @Inject
  lateinit var keyValueStorage: KeyValueStorage

  @Before
  fun setUp() {
    hiltRule.inject()
  }

  @Test
  fun `should get repos when the api call was successful`() =
    runTest {
      val repos =
        listOf(
          RepoDto(
            id = 1,
            name = "Repo #1",
            description = null,
            language = null,
          ),
          RepoDto(
            id = 2,
            name = "Repo #2",
            description = null,
            language = null,
          ),
        )
      val expected = Result.Success(repos.map { it.toRepo() })
      val token = "pat"
      val successfulResponse = Response.success(repos)
      every { keyValueStorage.retrieveToken() } returns token
      coEvery { githubApi.getRepositories(token, any()) } returns successfulResponse
      val appRepository = RemoteAppRepository(githubApi, keyValueStorage)

      val actual = appRepository.getRepositories()

      testScheduler.advanceUntilIdle()
      assertEquals(expected, actual)
    }

  @Test
  fun `should get an error when the api call was a failure`() =
    runTest {
      val expected = Result.Error(NetworkError.ServerError)
      val token = "pat"
      val body = ResponseBody.create(null, "")
      val failedResponse = Response.error<List<RepoDto>>(500, body)
      every { keyValueStorage.retrieveToken() } returns token
      coEvery { githubApi.getRepositories(token, any()) } returns failedResponse
      val appRepository = RemoteAppRepository(githubApi, keyValueStorage)

      val actual = appRepository.getRepositories()

      assertEquals(expected, actual)
    }
}