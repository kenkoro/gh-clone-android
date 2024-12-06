package com.kenkoro.practice.githubClone.core.navigation

sealed class Screen(val route: String) {
  data object Auth : Screen("auth_screen")

  data object ReposList : Screen("repos_list_screen")

  data object RepoDetails : Screen("repo_details_screen")
}