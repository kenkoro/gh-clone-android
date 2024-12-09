package com.kenkoro.practice.githubClone.reposViewer.data.mappers

import com.kenkoro.practice.githubClone.reposViewer.data.networking.dto.RepoDto
import com.kenkoro.practice.githubClone.reposViewer.data.networking.dto.UserInfoDto
import com.kenkoro.practice.githubClone.reposViewer.domain.Repo
import com.kenkoro.practice.githubClone.reposViewer.domain.UserInfo

fun UserInfoDto.toUserInfo(): UserInfo {
  return UserInfo(login = login)
}

fun RepoDto.toRepo(): Repo {
  return Repo(id, name, description, language)
}