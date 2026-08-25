package io.selimdawa.multibottoms.new3

import android.view.Menu
import androidx.annotation.IdRes
import androidx.core.view.get
import androidx.core.view.size
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.ui.NavigationUI
import java.lang.ref.WeakReference

class NavigationComponentHelper {

    companion object {

        fun setupWithNavController(
            menu: Menu,
            smoothBottomBar: SmoothBottomBar,
            navController: NavController,
        ) {
            smoothBottomBar.onItemSelectedListener = OnItemSelectedListener { pos ->
                NavigationUI.onNavDestinationSelected(menu[pos], navController)
            }

            val weakReference = WeakReference(smoothBottomBar)

            navController.addOnDestinationChangedListener(object :
                NavController.OnDestinationChangedListener {
                override fun onDestinationChanged(
                    controller: NavController,
                    destination: NavDestination,
                    arguments: android.os.Bundle?
                ) {
                    val view = weakReference.get() ?: run {
                        navController.removeOnDestinationChangedListener(this)
                        return
                    }

                    for (h in 0 until menu.size) {
                        val menuItem = menu[h]
                        if (matchDestination(destination, menuItem.itemId)) {
                            menuItem.isChecked = true
                            view.itemActiveIndex = h
                        }
                    }
                }
            })
        }

        fun matchDestination(destination: NavDestination, @IdRes destId: Int): Boolean {
            var curr: NavDestination? = destination
            while (curr != null) {
                if (curr.id == destId) return true
                curr = curr.parent
            }
            return false
        }
    }
}