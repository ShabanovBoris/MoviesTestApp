package com.bosha.core.navigation

import androidx.annotation.IdRes
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.bosha.utils.navigation.NavCommand
import com.bosha.utils.navigation.NavConstants

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
 * Use application navigation graph
 */
fun <T : Fragment> T.navigate(navParameters: NavBuilder.() -> Unit) {
    NavBuilder(requireActivity().findNavController(R.id.nav_host_fragment_container))
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

      /**
       * Set the empty setter options{} for get animation by default
       */
    fun options(optionsBuilder: NavOptionsBuilder.() -> Unit) {
        options = navOptions {
            anim {
                enter = com.bosha.core.R.anim.slide_in_right
                exit = com.bosha.core.R.anim.slide_out_left
                popEnter = android.R.anim.slide_in_left
                popExit = android.R.anim.slide_out_right
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