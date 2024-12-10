package com.kenkoro.practice.githubClone.reposViewer.presentation.models

import androidx.annotation.ColorRes
import com.kenkoro.practice.githubClone.reposViewer.domain.Repo

data class RepoUi(
  val id: Int,
  val name: String,
  val description: String?,
  val language: String?,
  @ColorRes val colorRes: Int,
)

fun Repo.toRepoUi(
  @ColorRes colorRes: Int,
): RepoUi {
  return RepoUi(
    id = id,
    name = name,
    description = description,
    language = language,
    colorRes = colorRes,
  )
}