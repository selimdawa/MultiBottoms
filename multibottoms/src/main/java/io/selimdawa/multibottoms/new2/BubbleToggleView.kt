package io.selimdawa.multibottoms.new2

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.TransitionDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import io.selimdawa.multibottoms.R
import io.selimdawa.multibottoms.new2.util.ViewUtils

class BubbleToggleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val bubbleToggleItem = BubbleToggleItem()
    private var isActive = false
    private lateinit var iconView: ImageView
    private lateinit var titleView: TextView
    private var badgeView: TextView? = null

    private var animationDuration = 300
    private var showShapeAlways = false
    private var maxTitleWidth = 0f
    private var measuredTitleWidth = 0f

    init {
        setup(context, attrs)
    }

    private fun setup(context: Context, attrs: AttributeSet?) {
        val res = context.resources

        var icon = AppCompatResources.getDrawable(context, R.drawable.default_icon)
        var shape = ContextCompat.getDrawable(context, R.drawable.transition_background_drawable)
        var shapeColor = Int.MIN_VALUE
        var colorActive = ViewUtils.getThemeAccentColor(context)
        var colorInactive = ContextCompat.getColor(context, R.color.default_inactive_color)
        var title = "Title"
        var titleSize = res.getDimension(R.dimen.default_nav_item_text_size)
        maxTitleWidth = res.getDimension(R.dimen.default_nav_item_title_max_width)
        var iconWidth = res.getDimension(R.dimen.default_icon_size)
        var iconHeight = res.getDimension(R.dimen.default_icon_size)
        var internalPadding = res.getDimension(R.dimen.default_nav_item_padding).toInt()
        var titlePadding = res.getDimension(R.dimen.default_nav_item_text_padding).toInt()
        var badgeTextSize = res.getDimension(R.dimen.default_nav_item_badge_text_size)
        var badgeBackgroundColor =
            ContextCompat.getColor(context, R.color.default_badge_background_color)
        var badgeTextColor = ContextCompat.getColor(context, R.color.default_badge_text_color)
        var badgeText: String? = null

        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.BubbleToggleView, 0, 0)
            try {
                icon = ta.getDrawable(R.styleable.BubbleToggleView_bt_icon) ?: icon
                iconWidth = ta.getDimension(R.styleable.BubbleToggleView_bt_iconWidth, iconWidth)
                iconHeight = ta.getDimension(R.styleable.BubbleToggleView_bt_iconHeight, iconHeight)
                shape = ta.getDrawable(R.styleable.BubbleToggleView_bt_shape) ?: shape
                shapeColor = ta.getColor(R.styleable.BubbleToggleView_bt_shapeColor, shapeColor)
                showShapeAlways =
                    ta.getBoolean(R.styleable.BubbleToggleView_bt_showShapeAlways, false)
                title = ta.getString(R.styleable.BubbleToggleView_bt_title) ?: title
                titleSize = ta.getDimension(R.styleable.BubbleToggleView_bt_titleSize, titleSize)
                colorActive = ta.getColor(R.styleable.BubbleToggleView_bt_colorActive, colorActive)
                colorInactive =
                    ta.getColor(R.styleable.BubbleToggleView_bt_colorInactive, colorInactive)
                isActive = ta.getBoolean(R.styleable.BubbleToggleView_bt_active, false)
                animationDuration = ta.getInteger(R.styleable.BubbleToggleView_bt_duration, 300)
                internalPadding = ta.getDimension(
                    R.styleable.BubbleToggleView_bt_padding, internalPadding.toFloat()
                ).toInt()
                titlePadding = ta.getDimension(
                    R.styleable.BubbleToggleView_bt_titlePadding, titlePadding.toFloat()
                ).toInt()
                badgeTextSize =
                    ta.getDimension(R.styleable.BubbleToggleView_bt_badgeTextSize, badgeTextSize)
                badgeBackgroundColor = ta.getColor(
                    R.styleable.BubbleToggleView_bt_badgeBackgroundColor, badgeBackgroundColor
                )
                badgeTextColor =
                    ta.getColor(R.styleable.BubbleToggleView_bt_badgeTextColor, badgeTextColor)
                badgeText = ta.getString(R.styleable.BubbleToggleView_bt_badgeText)
            } finally {
                ta.recycle()
            }
        }

        bubbleToggleItem.apply {
            this.icon = icon
            this.shape = shape
            this.title = title
            this.titleSize = titleSize
            this.titlePadding = titlePadding
            this.shapeColor = shapeColor
            this.colorActive = colorActive
            this.colorInactive = colorInactive
            this.iconWidth = iconWidth
            this.iconHeight = iconHeight
            this.internalPadding = internalPadding
            this.badgeText = badgeText
            this.badgeBackgroundColor = badgeBackgroundColor
            this.badgeTextColor = badgeTextColor
            this.badgeTextSize = badgeTextSize
        }

        gravity = Gravity.CENTER
        setPadding(internalPadding, internalPadding, internalPadding, internalPadding)

        createBubbleItemView(context)
        setInitialState(isActive)
    }

    private fun createBubbleItemView(context: Context) {
        iconView = ImageView(context).apply {
            id = generateViewId()
            layoutParams = LayoutParams(
                bubbleToggleItem.iconWidth.toInt(), bubbleToggleItem.iconHeight.toInt()
            ).apply {
                addRule(CENTER_VERTICAL, TRUE)
            }
            setImageDrawable(bubbleToggleItem.icon)
        }

        titleView = TextView(context).apply {
            layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    addRule(CENTER_VERTICAL, TRUE)
                    addRule(END_OF, iconView.id)
                }
            isSingleLine = true
            setTextColor(bubbleToggleItem.colorActive)
            text = bubbleToggleItem.title
            setTextSize(TypedValue.COMPLEX_UNIT_PX, bubbleToggleItem.titleSize)
            setPadding(bubbleToggleItem.titlePadding, 0, bubbleToggleItem.titlePadding, 0)
            visibility = GONE
        }

        addView(iconView)
        addView(titleView)

        // Measure title width
        titleView.measure(0, 0)
        measuredTitleWidth = titleView.measuredWidth.toFloat().coerceAtMost(maxTitleWidth)

        updateBadge(context)
        setInitialState(isActive)
    }

    private fun updateBadge(context: Context) {
        badgeView?.let { removeView(it) }
        val text = bubbleToggleItem.badgeText ?: return

        badgeView = TextView(context).apply {
            layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    addRule(ALIGN_TOP, iconView.id)
                    addRule(ALIGN_END, iconView.id)
                }
            isSingleLine = true
            setTextColor(bubbleToggleItem.badgeTextColor)
            this.text = text
            setTextSize(TypedValue.COMPLEX_UNIT_PX, bubbleToggleItem.badgeTextSize)
            gravity = Gravity.CENTER

            val drawable = ContextCompat.getDrawable(context, R.drawable.badge_background_white)
            ViewUtils.updateDrawableColor(drawable, bubbleToggleItem.badgeBackgroundColor)
            background = drawable

            val badgePadding =
                context.resources.getDimension(R.dimen.default_nav_item_badge_padding).toInt()
            setPadding(badgePadding, 0, badgePadding, 0)

            measure(0, 0)
            if (measuredWidth < measuredHeight) {
                width = measuredHeight
            }
        }
        addView(badgeView)
    }

    fun setInitialState(isActive: Boolean) {
        background = bubbleToggleItem.shape
        this.isActive = isActive

        if (isActive) {
            ViewUtils.updateDrawableColor(iconView.drawable, bubbleToggleItem.colorActive)
            titleView.visibility = VISIBLE
            (background as? TransitionDrawable)?.startTransition(0) ?: run {
                if (!showShapeAlways && bubbleToggleItem.shapeColor != Int.MIN_VALUE) {
                    ViewUtils.updateDrawableColor(
                        bubbleToggleItem.shape, bubbleToggleItem.shapeColor
                    )
                }
            }
        } else {
            ViewUtils.updateDrawableColor(iconView.drawable, bubbleToggleItem.colorInactive)
            titleView.visibility = GONE
            if (!showShapeAlways) {
                (background as? TransitionDrawable)?.resetTransition() ?: run { background = null }
            }
        }
    }

    fun toggle() {
        if (!isActive) activate() else deactivate()
    }

    fun activate() {
        ViewUtils.updateDrawableColor(iconView.drawable, bubbleToggleItem.colorActive)
        isActive = true
        titleView.visibility = VISIBLE

        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = animationDuration.toLong()
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                titleView.width = (measuredTitleWidth * value).toInt()
            }
            start()
        }

        (background as? TransitionDrawable)?.startTransition(animationDuration) ?: run {
            if (!showShapeAlways && bubbleToggleItem.shapeColor != Int.MIN_VALUE) {
                ViewUtils.updateDrawableColor(bubbleToggleItem.shape, bubbleToggleItem.shapeColor)
            }
            background = bubbleToggleItem.shape
        }
    }

    fun deactivate() {
        ViewUtils.updateDrawableColor(iconView.drawable, bubbleToggleItem.colorInactive)
        isActive = false

        ValueAnimator.ofFloat(1f, 0f).apply {
            duration = animationDuration.toLong()
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                titleView.width = (measuredTitleWidth * value).toInt()
                if (value <= 0f) titleView.visibility = GONE
            }
            start()
        }

        (background as? TransitionDrawable)?.reverseTransition(animationDuration) ?: run {
            if (!showShapeAlways) background = null
        }
    }

    fun isActive() = isActive

    fun setTitleTypeface(typeface: Typeface?) {
        titleView.typeface = typeface
    }

    fun updateMeasurements(maxWidth: Int) {
        val lp = titleView.layoutParams as? LayoutParams
        val horizontalMargins = (lp?.leftMargin ?: 0) + (lp?.rightMargin ?: 0)
        val newTitleWidth =
            maxWidth - (paddingLeft + paddingRight) - horizontalMargins - bubbleToggleItem.iconWidth.toInt() + titleView.paddingLeft + titleView.paddingRight

        if (newTitleWidth > 0 && newTitleWidth < measuredTitleWidth) {
            measuredTitleWidth = titleView.measuredWidth.toFloat()
        }
    }

    fun setBadgeText(value: String?) {
        bubbleToggleItem.badgeText = value
        updateBadge(context)
    }
}
