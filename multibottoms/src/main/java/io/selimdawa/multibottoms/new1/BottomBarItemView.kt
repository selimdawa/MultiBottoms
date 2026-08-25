package io.selimdawa.multibottoms.new1

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import io.selimdawa.multibottoms.R


class BottomBarItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val layoutView = LayoutInflater.from(context).inflate(R.layout.layout_bottombar_item, this, true)
    private val textView = layoutView.findViewById<AppCompatTextView>(R.id.textView)
    private val imageView = layoutView.findViewById<AppCompatImageView>(R.id.imageView)

    private var animatedView: View? = null

    fun setText(text: String) {
        textView.text = text
    }

    fun setIconDrawable(drawable: Drawable) {
        imageView.setImageDrawable(drawable)
    }

    fun setItemType(itemType: ReadableBottomBar.ItemType) {
        animatedView = when (itemType) {
            ReadableBottomBar.ItemType.Text -> textView
            ReadableBottomBar.ItemType.Icon -> imageView
        }
        animatedView?.visibility = INVISIBLE
        animatedView?.bringToFront()
    }

    fun setTabColor(tabColor: Int) {
        textView.setBackgroundColor(tabColor)
        imageView.setBackgroundColor(tabColor)
    }

    fun setTextSize(textSize: Float) {
        textView.textSize = textSize
    }

    fun setTextColor(textColor: Int) {
        textView.setTextColor(textColor)
    }

    fun setIconColor(iconColor: Int) {
        imageView.setColorFilter(iconColor)
    }

    fun select() {
        animatedView?.apply {
            translationY = height.toFloat()
            visibility = VISIBLE
            animate()
                .translationY(0f)
                .setDuration(ReadableBottomBar.ANIMATION_DURATION)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        }
    }

    fun deselect() {
        animatedView?.animate()
            ?.translationY(height.toFloat())
            ?.setDuration(ReadableBottomBar.ANIMATION_DURATION)
            ?.setInterpolator(AccelerateDecelerateInterpolator())
            ?.withEndAction { animatedView?.visibility = INVISIBLE }
            ?.start()
    }
}