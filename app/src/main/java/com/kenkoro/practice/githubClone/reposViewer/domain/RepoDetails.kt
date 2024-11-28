package com.kenkoro.practice.githubClone.reposViewer.domain

data class RepoDetails(
  val name: String,
  val htmlUrl: DisplayableHtmlUrl,
  val license: License,
  val stars: Int,
  val forks: Int,
  val watchers: Int,
)

data class DisplayableHtmlUrl(
  val value: String,
  val formatted: String,
)