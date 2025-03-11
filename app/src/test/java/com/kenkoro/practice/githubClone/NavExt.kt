package com.kenkoro.practice.githubClone

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.testing.TestNavHostController

/**
 * Creates an isolated graph for testing, use this when you've built your navigation
 * from code directly, not from an .xml file. It automatically calls [TestNavHostController.setGraph].
 * See official [documentation on this part as well.](https://developer.android.com/guide/navigation/testing)
 *
 * @param startDestination a start destination as a string
 * @param startDestinationArgs a start destination's extra arguments
 * @param builder a block for building a new testable graph
 */
internal fun TestNavHostController.createTestableGraph(
  startDestination: String,
  startDestinationArgs: Bundle? = null,
  builder: NavGraphBuilder.() -> Unit,
): NavController {
  val graph =
    NavGraphBuilder(
      provider = navigatorProvider,
      startDestination = startDestination,
      route = null,
    ).apply(builder).build()
  setGraph(graph, startDestinationArgs)

  return this
}

internal fun NavGraphBuilder.fragment(route: String) {
  addDestination(
    NavDestination(route).apply {
      this.route = route
    },
  )
}