package com.bosha.moviesdemo.ui.onboarding

import android.content.Context
import android.view.View
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

interface OnboardingScreen {

    fun setFragment(fragment: Fragment)

    fun clearRef()

    fun showOnboardingIfNeed(onShow: () -> View, onSkip: () -> View?): View?

    class Base : OnboardingScreen {
        private var fragmentRef = WeakReference<Fragment>(null)
        private val fragment get() = checkNotNull(fragmentRef.get())

        override fun setFragment(fragment: Fragment) {
            fragmentRef = WeakReference(fragment)
        }

        private fun isFirstAppLaunch() = fragment.requireActivity()
            .getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE)
            .getBoolean(KEY_IS_FIRST_LAUNCH, true)

        override fun showOnboardingIfNeed(onShow: () -> View, onSkip: () -> View?): View? {
            return if (isFirstAppLaunch().not()) {
                onSkip()
            } else {
                appWasLaunched()
                onShow()
            }
        }

        override fun clearRef() = fragmentRef.clear()

        private fun appWasLaunched() {
            fragmentRef.get()?.requireActivity()
                ?.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE)?.edit {
                    putBoolean(KEY_IS_FIRST_LAUNCH, false)
                }
        }

        private companion object {
            const val KEY_PREF = "key_prefs_first_launch"
            const val KEY_IS_FIRST_LAUNCH = "key_prefs_first_launch"
        }
    }
}