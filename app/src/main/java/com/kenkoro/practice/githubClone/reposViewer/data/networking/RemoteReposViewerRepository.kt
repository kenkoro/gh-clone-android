package com.kenkoro.practice.githubClone.reposViewer.data.networking

import com.kenkoro.practice.githubClone.core.domain.util.NetworkError
import com.kenkoro.practice.githubClone.core.domain.util.Result
import com.kenkoro.practice.githubClone.reposViewer.domain.Readme
import com.kenkoro.practice.githubClone.reposViewer.domain.Repo
import com.kenkoro.practice.githubClone.reposViewer.domain.RepoDetails
import com.kenkoro.practice.githubClone.reposViewer.domain.ReposViewerRepository
import com.kenkoro.practice.githubClone.reposViewer.domain.UserInfo

class RemoteReposViewerRepository : ReposViewerRepository {
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
    TODO("Not yet implemented")
  }
}