package com.kenkoro.practice.githubClone

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.kenkoro.projects.githubClone.R

/**
 * A nice and fine helper function to launch a fragment with its view when you're dealing with
 * hilt, [see source.](https://github.com/android/architecture-samples/blob/dev-hilt/app/src/androidTest/java/com/example/android/architecture/blueprints/todoapp/HiltExt.kt)
 *
 * @param fragmentArgs a bundle to passed into fragment
 * @param themeResId a style resource id to be set to the host's activity theme
 * @param action runs a given action on the current activity's main thread
 */
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
  fragmentArgs: Bundle? = null,
  @StyleRes themeResId: Int = R.style.Theme_GithubApp,
  crossinline action: Fragment.() -> Unit = {},
) {
  val startActivityIntent =
    Intent.makeMainActivity(
      ComponentName(
        ApplicationProvider.getApplicationContext(),
        HiltTestActivity::class.java,
      ),
    ).putExtra(EXTRA_THEME, themeResId)

  ActivityScenario.launch<HiltTestActivity>(startActivityIntent).onActivity { activity ->
    val fragment =
      activity.supportFragmentManager.fragmentFactory.instantiate(
        Preconditions.checkNotNull(T::class.java.classLoader),
        T::class.java.name,
      )
    fragment.arguments = fragmentArgs
    activity.supportFragmentManager
      .beginTransaction()
      .add(android.R.id.content, fragment, "")
      .commitNow()

    fragment.action()
  }
}

const val EXTRA_THEME =
  "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY"