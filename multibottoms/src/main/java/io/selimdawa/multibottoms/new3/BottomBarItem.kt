package io.selimdawa.multibottoms.new3

import android.graphics.RectF
import android.graphics.drawable.Drawable

data class BottomBarItem(
    val title: String,
    val contentDescription: String,
    val icon: Drawable,
    val rect: RectF = RectF(),
    val targetRect: RectF = RectF(),
    var alpha: Int = 0
)
