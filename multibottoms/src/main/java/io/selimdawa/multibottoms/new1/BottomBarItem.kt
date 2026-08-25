package io.selimdawa.multibottoms.new1

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt

data class BottomBarItem(
    val index: Int,
    val text: String,
    val textSize: Float,
    @param:ColorInt val textColor: Int,
    @param:ColorInt val iconColor: Int,
    val drawable: Drawable,
    val type: ReadableBottomBar.ItemType,
)