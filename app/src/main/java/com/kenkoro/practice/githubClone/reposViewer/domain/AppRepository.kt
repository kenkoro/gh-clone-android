package com.kenkoro.practice.githubClone.reposViewer.domain

import com.kenkoro.practice.githubClone.core.domain.util.NetworkError
import com.kenkoro.practice.githubClone.core.domain.util.Result
import kotlinx.coroutines.CoroutineDispatcher

interface AppRepository {
  val defaultDispatcher: CoroutineDispatcher

  suspend fun getRepositories(): Result<List<Repo>, NetworkError>

  suspend fun getRepository(repoId: String): Result<RepoDetails, NetworkError>

  suspend fun getRepositoryReadme(
    ownerName: String,
    repositoryName: String,
    branchName: String,
  ): Result<Readme, NetworkError>

  suspend fun signIn(token: String): Result<UserInfo, NetworkError>
}