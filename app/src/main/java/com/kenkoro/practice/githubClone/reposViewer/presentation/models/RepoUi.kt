package com.kenkoro.practice.githubClone.reposViewer.presentation.models

import androidx.annotation.ColorRes

data class RepoUi(
  val id: Int,
  val name: String,
  val description: String?,
  val language: String,
  @ColorRes val colorRes: Int,
)