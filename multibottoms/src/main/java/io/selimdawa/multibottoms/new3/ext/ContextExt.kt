package io.selimdawa.multibottoms.new3.ext

import android.content.Context
import kotlin.math.roundToInt

internal fun Context.d2p(dp: Float): Float =
    (dp * resources.displayMetrics.density).roundToInt().toFloat()
