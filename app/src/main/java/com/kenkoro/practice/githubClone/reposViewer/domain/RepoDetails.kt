package com.kenkoro.practice.githubClone.reposViewer.domain

data class RepoDetails(
  val name: String,
  val htmlUrl: String,
  val license: License,
  val stars: Int,
  val forks: Int,
  val watchers: Int,
)