package com.kenkoro.practice.githubClone.reposViewer.data.networking

import com.kenkoro.practice.githubClone.core.data.networking.safeCall
import com.kenkoro.practice.githubClone.core.domain.util.NetworkError
import com.kenkoro.practice.githubClone.core.domain.util.Result
import com.kenkoro.practice.githubClone.core.domain.util.map
import com.kenkoro.practice.githubClone.reposViewer.data.mappers.toUserInfo
import com.kenkoro.practice.githubClone.reposViewer.data.networking.dto.UserInfoDto
import com.kenkoro.practice.githubClone.reposViewer.domain.Readme
import com.kenkoro.practice.githubClone.reposViewer.domain.Repo
import com.kenkoro.practice.githubClone.reposViewer.domain.RepoDetails
import com.kenkoro.practice.githubClone.reposViewer.domain.ReposViewerRepository
import com.kenkoro.practice.githubClone.reposViewer.domain.UserInfo

class RemoteReposViewerRepository(
  private val githubApi: GithubApi,
) : ReposViewerRepository {
  override suspend fun getRepositories(): Result<List<Repo>, NetworkError> {
    TODO("Not yet implemented")
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
    return safeCall<UserInfoDto> {
      githubApi.signIn(token)
    }.map { response ->
      response.toUserInfo()
    }
  }
}