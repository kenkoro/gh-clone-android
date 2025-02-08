package com.kenkoro.practice.githubClone.reposViewer.data.networking

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kenkoro.practice.githubClone.core.domain.util.NetworkError
import com.kenkoro.practice.githubClone.core.domain.util.Result
import com.kenkoro.practice.githubClone.reposViewer.data.mappers.toRepo
import com.kenkoro.practice.githubClone.reposViewer.data.mappers.toUserInfo
import com.kenkoro.practice.githubClone.reposViewer.data.networking.dto.RepoDto
import com.kenkoro.practice.githubClone.reposViewer.data.networking.dto.UserInfoDto
import com.kenkoro.practice.githubClone.reposViewer.data.storage.KeyValueStorage
import com.kenkoro.practice.githubClone.reposViewer.domain.AppRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
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

  private lateinit var appRepository: AppRepository

  @Inject
  lateinit var githubApi: GithubApi

  @Inject
  lateinit var keyValueStorage: KeyValueStorage

  @Before
  fun setUp() {
    hiltRule.inject()
    appRepository = RemoteAppRepository(githubApi, keyValueStorage)
  }

  @Test
  fun `should get repos when the get repos call was successful`() =
    runTest {
      val listOfDto =
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
      val expected = Result.Success(listOfDto.map { it.toRepo() })
      val token = "pat"
      val successfulResponse = Response.success(listOfDto)
      every { keyValueStorage.retrieveToken() } returns token
      coEvery { githubApi.getRepositories(token, any()) } returns successfulResponse

      val actual = appRepository.getRepositories()

      testScheduler.advanceUntilIdle()
      verify(exactly = 1) { keyValueStorage.retrieveToken() }
      assertEquals(expected, actual)
    }

  @Test
  fun `should get an error when the get repos call was a failure`() =
    runTest {
      val expected = Result.Error(NetworkError.ServerError)
      val token = "pat"
      val emptyBody = ResponseBody.create(null, "")
      val failedResponse = Response.error<List<RepoDto>>(500, emptyBody)
      every { keyValueStorage.retrieveToken() } returns token
      coEvery { githubApi.getRepositories(token, any()) } returns failedResponse

      val actual = appRepository.getRepositories()

      testScheduler.advanceUntilIdle()
      verify(exactly = 1) { keyValueStorage.retrieveToken() }
      assertEquals(expected, actual)
    }

  @Test
  fun `should authenticate when the sign in call was successful`() =
    runTest {
      val dto = UserInfoDto(login = "username")
      val expected = Result.Success(dto.toUserInfo())
      val token = "pat"
      val successfulResponse = Response.success(dto)
      coEvery { githubApi.signIn(token) } returns successfulResponse

      val actual = appRepository.signIn(token)

      assertEquals(expected, actual)
      coVerify(exactly = 1) { keyValueStorage.saveToken(token) }
    }

  @Test
  fun `should get an error when the sign in call was a failure`() =
    runTest {
      val expected = Result.Error(NetworkError.ServerError)
      val token = "pat"
      val emptyBody = ResponseBody.create(null, "")
      val failedResponse = Response.error<UserInfoDto>(500, emptyBody)
      coEvery { githubApi.signIn(token) } returns failedResponse

      val actual = appRepository.signIn(token)

      assertEquals(expected, actual)
      coVerify(inverse = true) { keyValueStorage.saveToken(token) }
    }

  @Ignore("Not yet implemented :)")
  @Test
  fun `should get a repo when the get a repo call was successful`() {}

  @Ignore("Not yet implemented :)")
  @Test
  fun `should get a repo's readme when the get a repo's readme call was successful`() {}

  @Ignore("Not yet implemented :)")
  @Test
  fun `should get an error when the get a repo call was a failure`() {}

  @Ignore("Not yet implemented :)")
  @Test
  fun `should get an error when the get a repo's readme call was a failure`() {}
}