package io.selimdawa.multibottoms.new2.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.ColorInt

object ViewUtils {
    @ColorInt
    fun getThemeAccentColor(context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(androidx.appcompat.R.attr.colorAccent, value, true)
        return value.data
    }

    fun updateDrawableColor(drawable: Drawable?, @ColorInt color: Int) {
        drawable?.setTint(color)
    }
}
