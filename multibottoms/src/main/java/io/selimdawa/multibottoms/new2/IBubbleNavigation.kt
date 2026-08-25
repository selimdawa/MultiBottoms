package io.selimdawa.multibottoms.new2

import android.graphics.Typeface
import io.selimdawa.multibottoms.new2.listener.BubbleNavigationChangeListener

interface IBubbleNavigation {
    fun setNavigationChangeListener(navigationChangeListener: BubbleNavigationChangeListener?)
    fun setTypeface(typeface: Typeface?)
    fun getCurrentActiveItemPosition(): Int
    fun setCurrentActiveItem(position: Int)
    fun setBadgeValue(position: Int, value: String?)
}
