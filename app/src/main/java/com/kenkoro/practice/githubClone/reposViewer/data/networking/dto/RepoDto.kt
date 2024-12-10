package com.kenkoro.practice.githubClone.reposViewer.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class RepoDto(
  val id: Int,
  val name: String,
  val description: String?,
  val language: String?,
)