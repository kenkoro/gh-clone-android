package com.kenkoro.practice.githubClone.core.navigation

sealed class Screen(val route: String) {
  data object AuthScreen : Screen("auth_screen")
}