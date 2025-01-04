package com.kenkoro.practice.githubClone.reposViewer

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentFactoryHolderViewModel
import androidx.fragment.testing.manifest.R
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.kenkoro.practice.githubClone.HiltTestActivity

/**
 * A nice and fine helper function to launch a fragment with its view when you're dealing with
 * hilt, [see source.](https://github.com/android/architecture-samples/blob/dev-hilt/app/src/androidTest/java/com/example/android/architecture/blueprints/todoapp/HiltExt.kt)
 *
 * @param fragmentArgs a bundle to passed into fragment
 * @param themeResId a style resource id to be set to the host activity's theme
 * @sample com.kenkoro.practice.githubClone.reposViewer.presentation.auth.AuthFragmentTest.shouldStayOnAuth_WhenTheresNoTokenProvided
 */
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
  fragmentArgs: Bundle? = null,
  @StyleRes themeResId: Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
  crossinline action: Fragment.() -> Unit = {},
) {
  val startActivityIntent =
    Intent.makeMainActivity(
      ComponentName(
        ApplicationProvider.getApplicationContext(),
        HiltTestActivity::class.java,
      ),
    ).putExtra(
      "androidx.fragment.app.testing.FragmentScenario" +
        ".EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
      themeResId,
    )

  ActivityScenario.launch<HiltTestActivity>(startActivityIntent).onActivity { activity ->
    val fragment: Fragment =
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