package com.kenkoro.practice.githubClone.reposViewer.domain

data class Repo(
  val id: Int,
  val name: String,
  val description: String?,
  val language: String?,
)