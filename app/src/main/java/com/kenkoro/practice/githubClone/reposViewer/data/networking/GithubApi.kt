package com.kenkoro.practice.githubClone.reposViewer.data.networking

import com.kenkoro.practice.githubClone.reposViewer.data.networking.dto.UserInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GithubApi {
  @GET("user")
  suspend fun signIn(
    @Header("Authorization") token: String,
  ): Response<UserInfoDto>
}