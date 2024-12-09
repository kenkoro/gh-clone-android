package com.kenkoro.practice.githubClone.reposViewer.data.networking

import com.kenkoro.practice.githubClone.reposViewer.data.networking.dto.ReposResponseDto
import com.kenkoro.practice.githubClone.reposViewer.data.networking.dto.UserInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface GithubApi {
  @GET("user")
  suspend fun signIn(
    @Header("Authorization") token: String,
  ): Response<UserInfoDto>

  @GET("user/repos")
  suspend fun getRepositories(
    @Header("Authorization") token: String,
    @QueryMap options: Map<String, String>,
  ): Response<ReposResponseDto>
}