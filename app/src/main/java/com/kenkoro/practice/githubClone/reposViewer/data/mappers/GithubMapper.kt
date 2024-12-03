package com.kenkoro.practice.githubClone.reposViewer.data.mappers

import com.kenkoro.practice.githubClone.reposViewer.data.networking.dto.UserInfoDto
import com.kenkoro.practice.githubClone.reposViewer.domain.UserInfo

fun UserInfoDto.toUserInfo(): UserInfo {
  return UserInfo(login = login)
}