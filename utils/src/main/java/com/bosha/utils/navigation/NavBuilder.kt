package com.bosha.utils.navigation

import android.util.Log
import androidx.annotation.IdRes
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bosha.utils.R

fun <T : FragmentActivity> T.navigate(
    navController: NavController,
    navParameters: NavBuilder.() -> Unit
) {
    NavBuilder(navController)
        .apply(navParameters)
        .navigate()
}

fun <T : FragmentActivity> T.navigate(@IdRes hostId: Int, navParameters: NavBuilder.() -> Unit) {
    NavBuilder(findNavController(hostId))
        .apply(navParameters)
        .navigate()
}

/**
 * Use parent or application navigation graph
 */
fun <T : Fragment> T.navigate(navParameters: NavBuilder.() -> Unit) {
    NavBuilder(findNavController())
        .apply(navParameters)
        .navigate()
}

/**
 * Use some inner navigation graph
 */
fun <T : Fragment> T.navigate(@IdRes hostId: Int, navParameters: NavBuilder.() -> Unit) {
    val controller =
        (childFragmentManager.findFragmentById(hostId) as NavHostFragment).navController
    NavBuilder(controller)
        .apply(navParameters)
        .navigate()
}

  /** @sample
   *  navigate(R.id.nav_host_fragment_container) {
   *      target = NavCommand(Screens.SEARCH) { "Go to the search fragment from the main screen" }
   *          .setArgs("Marvel")
   *      options {
   *           launchSingleTop = true
   *      }
   *      extras {
   *           addSharedElement(view, "tag")
   *      }
   *   }
   * */
class NavBuilder(
    private val navController: NavController
) {
    /**
     * animate specific transition behavior
     */
    private var extras: FragmentNavigator.Extras? = null

    /**
     * simple options like transition animation or popup settings
     */
    private var options: NavOptions? = null

    var target: NavCommand? = null
        set(value) {
            require(value != null && value.url.isNotEmpty())
            field = value
        }

    fun extras(builder: FragmentNavigator.Extras.Builder.() -> Unit) {
        extras = FragmentNavigator.Extras.Builder()
            .apply(builder)
            .build()
    }

    fun options(optionsBuilder: NavOptionsBuilder.() -> Unit) {
        options = navOptions {
            anim {
                enter = R.anim.slide_in_left
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
            optionsBuilder()
        }
    }

    @Throws(java.lang.IllegalArgumentException::class)
    internal fun navigate() {
        requireNotNull(target) { "Target url shouldn't be null" }
        val target = target ?: return

        val deepLink = "${NavConstants.SCHEME}${target.url}".toUri()

        navController.navigate(
            deepLink,
            options,
            extras
        )
    }
}