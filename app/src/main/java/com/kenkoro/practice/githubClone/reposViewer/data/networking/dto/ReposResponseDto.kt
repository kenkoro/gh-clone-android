package com.kenkoro.practice.githubClone.reposViewer.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReposResponseDto(
  val data: List<RepoDto>,
)