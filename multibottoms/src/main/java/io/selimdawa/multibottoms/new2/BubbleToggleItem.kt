package io.selimdawa.multibottoms.new2

import android.graphics.Color
import android.graphics.drawable.Drawable

internal class BubbleToggleItem {
    var icon: Drawable? = null
    var shape: Drawable? = null
    var title: String = ""
    var colorActive: Int = Color.BLUE
    var colorInactive: Int = Color.BLACK
    var shapeColor: Int = Int.MIN_VALUE
    var badgeText: String? = null
    var badgeTextColor: Int = Color.WHITE
    var badgeBackgroundColor: Int = Color.BLACK
    var titleSize: Float = 0f
    var badgeTextSize: Float = 0f
    var iconWidth: Float = 0f
    var iconHeight: Float = 0f
    var titlePadding: Int = 0
    var internalPadding: Int = 0
}
