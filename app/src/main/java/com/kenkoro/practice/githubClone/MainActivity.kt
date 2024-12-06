package com.kenkoro.practice.githubClone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.kenkoro.practice.githubClone.core.navigation.Screen
import com.kenkoro.practice.githubClone.reposViewer.presentation.auth.AuthFragment
import com.kenkoro.practice.githubClone.reposViewer.presentation.reposList.ReposListFragment
import com.kenkoro.projects.githubClone.R
import com.kenkoro.projects.githubClone.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setupNavigation()
  }

  private fun setupNavigation() {
    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
    val navController = navHostFragment.navController
    navController.graph =
      navController.createGraph(
        startDestination = Screen.Auth.route,
      ) {
        fragment<AuthFragment>(route = Screen.Auth.route) { label = "Auth Screen" }
        fragment<ReposListFragment>(route = Screen.ReposList.route) { label = "Repos List Screen" }
      }
  }
}