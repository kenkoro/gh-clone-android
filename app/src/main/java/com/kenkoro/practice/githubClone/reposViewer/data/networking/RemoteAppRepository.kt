package com.kenkoro.practice.githubClone.reposViewer.data.networking

import com.kenkoro.practice.githubClone.core.data.networking.safeCall
import com.kenkoro.practice.githubClone.core.domain.util.NetworkError
import com.kenkoro.practice.githubClone.core.domain.util.Result
import com.kenkoro.practice.githubClone.core.domain.util.map
import com.kenkoro.practice.githubClone.core.domain.util.onSuccess
import com.kenkoro.practice.githubClone.reposViewer.data.mappers.toRepo
import com.kenkoro.practice.githubClone.reposViewer.data.mappers.toUserInfo
import com.kenkoro.practice.githubClone.reposViewer.data.networking.dto.RepoDto
import com.kenkoro.practice.githubClone.reposViewer.data.networking.dto.UserInfoDto
import com.kenkoro.practice.githubClone.reposViewer.data.storage.KeyValueStorage
import com.kenkoro.practice.githubClone.reposViewer.domain.AppRepository
import com.kenkoro.practice.githubClone.reposViewer.domain.Readme
import com.kenkoro.practice.githubClone.reposViewer.domain.Repo
import com.kenkoro.practice.githubClone.reposViewer.domain.RepoDetails
import com.kenkoro.practice.githubClone.reposViewer.domain.UserInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class RemoteAppRepository(
  private val githubApi: GithubApi,
  private val keyValueStorage: KeyValueStorage,
) : AppRepository {
  override val defaultDispatcher: CoroutineDispatcher
    get() = Dispatchers.IO

  override suspend fun getRepositories(): Result<List<Repo>, NetworkError> {
    val token = keyValueStorage.retrieveToken()
    return safeCall<List<RepoDto>> {
      githubApi.getRepositories(
        token = token,
        options =
          mapOf(
            "affiliation" to "owner",
            "per_page" to "10",
            "sort" to "updated",
          ),
      )
    }.map { response ->
      response.map { it.toRepo() }
    }
  }

  override suspend fun getRepository(repoId: String): Result<RepoDetails, NetworkError> {
    TODO("Not yet implemented")
  }

  override suspend fun getRepositoryReadme(
    ownerName: String,
    repositoryName: String,
    branchName: String,
  ): Result<Readme, NetworkError> {
    TODO("Not yet implemented")
  }

  override suspend fun signIn(token: String): Result<UserInfo, NetworkError> {
    val result =
      safeCall<UserInfoDto> {
        githubApi.signIn(token)
      }.map { response ->
        response.toUserInfo()
      }

    result.onSuccess { keyValueStorage.saveToken(token) }
    return result
  }
}