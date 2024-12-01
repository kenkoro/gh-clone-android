package com.kenkoro.practice.githubClone.reposViewer.presentation.models

import com.kenkoro.practice.githubClone.reposViewer.domain.License

data class RepoDetailsUi(
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