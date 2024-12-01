package com.kenkoro.practice.githubClone.reposViewer.presentation.models

data class ReadmeUi(
  val fileName: String,
  val content: DisplayableReadmeContent,
)

data class DisplayableReadmeContent(
  val value: String,
  val encoded: String,
)