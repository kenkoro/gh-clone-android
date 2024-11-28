package com.kenkoro.practice.githubClone.reposViewer.domain

data class Readme(
  val fileName: String,
  val content: DisplayableReadmeContent,
)

data class DisplayableReadmeContent(
  val value: String,
  val encoded: String,
)