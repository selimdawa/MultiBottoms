package io.selimdawa.multibottoms.new3

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.Menu
import android.view.MotionEvent
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.animation.DecelerateInterpolator
import android.widget.PopupMenu
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.FontRes
import androidx.annotation.XmlRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import io.selimdawa.multibottoms.R
import io.selimdawa.multibottoms.new3.ext.d2p

class SmoothBottomBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.SmoothBottomBarStyle,
) : View(context, attrs, defStyleAttr) {

    private var currentIconTint = itemIconTintActive
    private var indicatorLocation = barSideMargins
    private var _iconBackgroundColor: Int = Color.TRANSPARENT
    private val iconBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = _iconBackgroundColor
    }

    @Dimension
    private var _iconBackgroundPadding: Float = context.d2p(DEFAULT_ICON_BG_PADDING)

    var iconBackgroundColor: Int
        @ColorInt get() = _iconBackgroundColor
        set(@ColorInt value) {
            _iconBackgroundColor = value
            iconBackgroundPaint.color = value
            invalidate()
        }

    var iconBackgroundPadding: Float
        @Dimension get() = _iconBackgroundPadding
        set(@Dimension value) {
            _iconBackgroundPadding = value
            invalidate()
        }

    private val rect = RectF()
    private val iconBackgroundRect = RectF()
    private var cachedTextHeight: Float = 0f
    internal var items = emptyList<BottomBarItem>()

    @ColorInt
    private var _barBackgroundColor = Color.WHITE

    @ColorInt
    private var _barIndicatorColor = DEFAULT_INDICATOR_COLOR.toColorInt()

    @Dimension
    private var _barIndicatorRadius = context.d2p(DEFAULT_CORNER_RADIUS)

    @Dimension
    private var _barSideMargins = context.d2p(DEFAULT_SIDE_MARGIN)

    @Dimension
    private var _barCornerRadius = context.d2p(DEFAULT_BAR_CORNER_RADIUS)

    private var _barCorners = DEFAULT_BAR_CORNERS

    @Dimension
    private var _itemPadding = context.d2p(DEFAULT_ITEM_PADDING)

    @Dimension
    private var _itemSpacing = context.d2p(DEFAULT_ITEM_SPACING)

    private var _itemAnimDuration = DEFAULT_ANIM_DURATION

    @Dimension
    private var _itemIconSize = context.d2p(DEFAULT_ICON_SIZE)

    @Dimension
    private var _itemIconMargin = context.d2p(DEFAULT_ICON_MARGIN)

    @ColorInt
    private var _itemIconTint = DEFAULT_TINT.toColorInt()

    @ColorInt
    private var _itemIconTintActive = Color.WHITE

    @ColorInt
    private var _itemTextColor = Color.WHITE

    @ColorInt
    private var _itemBadgeColor = Color.RED

    @Dimension
    private var _itemTextSize = context.d2p(DEFAULT_TEXT_SIZE)

    @FontRes
    private var _itemFontFamily: Int = INVALID_RES

    @XmlRes
    private var _itemMenuRes: Int = INVALID_RES

    private var _itemActiveIndex: Int = 0

    lateinit var menu: Menu

    private val badges = HashSet<Int>()

    var barBackgroundColor: Int
        @ColorInt get() = _barBackgroundColor
        set(@ColorInt value) {
            _barBackgroundColor = value
            paintBackground.color = value
            invalidate()
        }

    var barIndicatorColor: Int
        @ColorInt get() = _barIndicatorColor
        set(@ColorInt value) {
            _barIndicatorColor = value
            paintIndicator.color = value
            invalidate()
        }

    var barIndicatorRadius: Float
        @Dimension get() = _barIndicatorRadius
        set(@Dimension value) {
            _barIndicatorRadius = value
            invalidate()
        }

    var barSideMargins: Float
        @Dimension get() = _barSideMargins
        set(@Dimension value) {
            _barSideMargins = value
            invalidate()
        }

    var barCornerRadius: Float
        @Dimension get() = _barCornerRadius
        set(@Dimension value) {
            _barCornerRadius = value
            invalidate()
        }

    var barCorners: Int
        get() = _barCorners
        set(value) {
            _barCorners = value
            invalidate()
        }

    var itemTextSize: Float
        @Dimension get() = _itemTextSize
        set(@Dimension value) {
            _itemTextSize = value
            paintText.textSize = value
            invalidate()
        }

    var itemTextColor: Int
        @ColorInt get() = _itemTextColor
        set(@ColorInt value) {
            _itemTextColor = value
            paintText.color = value
            invalidate()
        }

    var itemBadgeColor: Int
        @ColorInt get() = _itemBadgeColor
        set(@ColorInt value) {
            _itemBadgeColor = value
            badgePaint.color = value
            invalidate()
        }

    var itemPadding: Float
        @Dimension get() = _itemPadding
        set(@Dimension value) {
            _itemPadding = value
            requestLayout()
        }

    var itemSpacing: Float
        @Dimension get() = _itemSpacing
        set(@Dimension value) {
            _itemSpacing = value
            requestLayout()
        }

    var itemAnimDuration: Long
        get() = _itemAnimDuration
        set(value) {
            _itemAnimDuration = value
        }

    var itemIconSize: Float
        @Dimension get() = _itemIconSize
        set(@Dimension value) {
            _itemIconSize = value
            invalidate()
        }

    var itemIconMargin: Float
        @Dimension get() = _itemIconMargin
        set(@Dimension value) {
            _itemIconMargin = value
            invalidate()
        }

    var itemIconTint: Int
        @ColorInt get() = _itemIconTint
        set(@ColorInt value) {
            _itemIconTint = value
            invalidate()
        }

    var itemIconTintActive: Int
        @ColorInt get() = _itemIconTintActive
        set(@ColorInt value) {
            _itemIconTintActive = value
            invalidate()
        }

    var itemFontFamily: Int
        @FontRes get() = _itemFontFamily
        set(@FontRes value) {
            _itemFontFamily = value
            if (value != INVALID_RES) {
                paintText.typeface = ResourcesCompat.getFont(context, value)
                invalidate()
            }
        }

    var itemMenuRes: Int
        @XmlRes get() = _itemMenuRes
        set(value) {
            _itemMenuRes = value
            if (value != INVALID_RES) {
                val popupMenu = PopupMenu(context, null)
                popupMenu.inflate(value)
                this.menu = popupMenu.menu
                items = BottomBarParser(context, value).parse()
                requestLayout()
            }
        }

    var itemActiveIndex: Int
        get() = _itemActiveIndex
        set(value) {
            if (_itemActiveIndex == value) return
            _itemActiveIndex = value
            applyItemActiveIndex()
        }

    var onItemSelectedListener: OnItemSelectedListener? = null
    var onItemReselectedListener: OnItemReselectedListener? = null
    var onItemSelected: ((Int) -> Unit)? = null
    var onItemReselected: ((Int) -> Unit)? = null

    private val paintBackground = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = barBackgroundColor
    }

    private val paintIndicator = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = barIndicatorColor
    }

    private val badgePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = itemBadgeColor
    }

    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = itemTextColor
        textSize = itemTextSize
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
    }

    private val exploreByTouchHelper: AccessibleExploreByTouchHelper

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.SmoothBottomBar, defStyleAttr, 0,
        )
        try {
            iconBackgroundColor = typedArray.getColor(
                R.styleable.SmoothBottomBar_iconBackgroundColor, iconBackgroundColor
            )
            iconBackgroundPadding = typedArray.getDimension(
                R.styleable.SmoothBottomBar_iconBackgroundPadding, iconBackgroundPadding
            )
            barBackgroundColor =
                typedArray.getColor(R.styleable.SmoothBottomBar_backgroundColor, barBackgroundColor)
            barIndicatorColor =
                typedArray.getColor(R.styleable.SmoothBottomBar_indicatorColor, barIndicatorColor)
            barIndicatorRadius = typedArray.getDimension(
                R.styleable.SmoothBottomBar_indicatorRadius, barIndicatorRadius
            )
            barSideMargins =
                typedArray.getDimension(R.styleable.SmoothBottomBar_sideMargins, barSideMargins)
            barCornerRadius =
                typedArray.getDimension(R.styleable.SmoothBottomBar_cornerRadius, barCornerRadius)
            barCorners = typedArray.getInteger(R.styleable.SmoothBottomBar_corners, barCorners)
            itemPadding =
                typedArray.getDimension(R.styleable.SmoothBottomBar_itemPadding, itemPadding)
            itemSpacing =
                typedArray.getDimension(R.styleable.SmoothBottomBar_itemSpacing, itemSpacing)
            itemTextColor =
                typedArray.getColor(R.styleable.SmoothBottomBar_textColor, itemTextColor)
            itemTextSize =
                typedArray.getDimension(R.styleable.SmoothBottomBar_textSize, itemTextSize)
            itemIconSize =
                typedArray.getDimension(R.styleable.SmoothBottomBar_iconSize, itemIconSize)
            itemIconMargin =
                typedArray.getDimension(R.styleable.SmoothBottomBar_iconMargin, itemIconMargin)
            itemIconTint = typedArray.getColor(R.styleable.SmoothBottomBar_iconTint, itemIconTint)
            itemBadgeColor =
                typedArray.getColor(R.styleable.SmoothBottomBar_badgeColor, itemBadgeColor)
            itemIconTintActive =
                typedArray.getColor(R.styleable.SmoothBottomBar_iconTintActive, itemIconTintActive)
            itemActiveIndex =
                typedArray.getInt(R.styleable.SmoothBottomBar_activeItem, itemActiveIndex)
            itemFontFamily =
                typedArray.getResourceId(R.styleable.SmoothBottomBar_itemFontFamily, itemFontFamily)
            itemAnimDuration =
                typedArray.getInt(R.styleable.SmoothBottomBar_duration, itemAnimDuration.toInt())
                    .toLong()
            itemMenuRes = typedArray.getResourceId(R.styleable.SmoothBottomBar_menu, itemMenuRes)
        } finally {
            typedArray.recycle()
        }

        exploreByTouchHelper = AccessibleExploreByTouchHelper(this) { onClickAction(it) }
        ViewCompat.setAccessibilityDelegate(this, exploreByTouchHelper)
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateItemBounds()
        if (items.isNotEmpty()) {
            items.forEachIndexed { index, item ->
                item.alpha = if (index == itemActiveIndex) OPAQUE else TRANSPARENT
                item.rect.set(item.targetRect)
            }
            indicatorLocation = items[itemActiveIndex].rect.left
            currentIconTint = itemIconTintActive
            invalidate()
        }
    }

    private fun calculateItemBounds() {
        if ((items.isEmpty()) || (width == 0) || (height == 0)) return

        val totalSpacing = if (items.size > 1) itemSpacing * (items.size - 1) else 0f
        val totalAvailableWidth = width - (barSideMargins * 2) - totalSpacing
        val inactiveItemWidth = itemIconSize + (itemPadding * 2)
        val activeItemWidth = totalAvailableWidth - (inactiveItemWidth * (items.size - 1))

        var lastX = barSideMargins
        val isRTL = layoutDirection == LAYOUT_DIRECTION_RTL
        val itemsToLayout = if (isRTL) items.asReversed() else items

        itemsToLayout.forEachIndexed { i, item ->
            val actualIndex = if (isRTL) items.size - 1 - i else i
            val currentWidth =
                if (actualIndex == itemActiveIndex) activeItemWidth else inactiveItemWidth
            item.targetRect.apply {
                left = lastX
                top = 0f
                right = currentWidth + lastX
                bottom = height.toFloat()
            }
            lastX += currentWidth + itemSpacing
        }

        cachedTextHeight = (paintText.descent() + paintText.ascent()) / 2
    }

    fun setBadge(pos: Int) {
        if (badges.add(pos)) invalidate()
    }

    fun removeBadge(pos: Int) {
        if (badges.remove(pos)) invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = width.toFloat()
        val h = height.toFloat()

        if (barCornerRadius > 0) {
            val radius = minOf(barCornerRadius, h / 2)
            canvas.drawRoundRect(0f, 0f, w, h, radius, radius, paintBackground)
            if (barCorners != ALL_CORNERS) {
                if ((barCorners and TOP_LEFT_CORNER) == 0) {
                    canvas.drawRect(0f, 0f, w / 2, h / 2, paintBackground)
                }
                if ((barCorners and TOP_RIGHT_CORNER) == 0) {
                    canvas.drawRect(w / 2, 0f, w, h / 2, paintBackground)
                }
                if ((barCorners and BOTTOM_LEFT_CORNER) == 0) {
                    canvas.drawRect(0f, h / 2, w / 2, h, paintBackground)
                }
                if ((barCorners and BOTTOM_RIGHT_CORNER) == 0) {
                    canvas.drawRect(w / 2, h / 2, w, h, paintBackground)
                }
            }
        } else {
            canvas.drawRect(0f, 0f, w, h, paintBackground)
        }

        val activeItem = items[itemActiveIndex]
        rect.apply {
            left = activeItem.rect.left
            top = activeItem.rect.centerY() - (itemIconSize / 2) - itemPadding
            right = activeItem.rect.right
            bottom = activeItem.rect.centerY() + (itemIconSize / 2) + itemPadding
        }
        canvas.drawRoundRect(rect, barIndicatorRadius, barIndicatorRadius, paintIndicator)

        val isRTL = layoutDirection == LAYOUT_DIRECTION_RTL
        val halfHeight = height / 2f
        val halfIcon = itemIconSize / 2f

        items.forEachIndexed { index, item ->
            val textLength = paintText.measureText(item.title)
            val alphaProgress = item.alpha / OPAQUE.toFloat()
            val textOffset = (textLength / 2f) * alphaProgress
            val centerX = item.rect.centerX()

            item.icon.mutate()
            val finalOffset = if (isRTL) textOffset else -textOffset
            item.icon.setBounds(
                ((centerX + finalOffset) - halfIcon).toInt(),
                (halfHeight - halfIcon).toInt(),
                ((centerX + finalOffset) + halfIcon).toInt(),
                (halfHeight + halfIcon).toInt()
            )

            if (index != itemActiveIndex) drawIconBackground(item, canvas)
            tintAndDrawIcon(item, index, canvas)

            if (badges.contains(index)) {
                canvas.drawCircle(
                    (centerX + finalOffset) - halfIcon, halfHeight - halfIcon, 10f, badgePaint
                )
            }

            paintText.alpha = item.alpha
            val labelX = if (isRTL) centerX - ((itemIconSize / 2) + itemIconMargin)
            else centerX + ((itemIconSize / 2) + itemIconMargin)
            canvas.drawText(item.title, labelX, item.rect.centerY() - cachedTextHeight, paintText)
        }
    }

    private fun drawIconBackground(item: BottomBarItem, canvas: Canvas) {
        if (iconBackgroundColor == Color.TRANSPARENT) return
        val half = (itemIconSize / 2f) + _iconBackgroundPadding
        iconBackgroundRect.apply {
            left = item.rect.centerX() - half
            top = (height / 2f) - half
            right = item.rect.centerX() + half
            bottom = (height / 2f) + half
        }
        canvas.drawRoundRect(iconBackgroundRect, half, half, iconBackgroundPaint)
    }

    private fun tintAndDrawIcon(item: BottomBarItem, index: Int, canvas: Canvas) {
        DrawableCompat.setTint(
            item.icon, if (index == itemActiveIndex) currentIconTint else itemIconTint
        )
        item.icon.draw(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            items.forEachIndexed { i, item ->
                if (item.rect.contains(event.x, event.y)) {
                    onClickAction(i)
                    return true
                }
            }
        }
        return (event.action == MotionEvent.ACTION_DOWN) || super.onTouchEvent(event)
    }

    override fun dispatchHoverEvent(event: MotionEvent): Boolean =
        exploreByTouchHelper.dispatchHoverEvent(event) || super.dispatchHoverEvent(event)

    private fun onClickAction(viewId: Int) {
        exploreByTouchHelper.invalidateVirtualView(viewId)
        if (viewId != itemActiveIndex) {
            itemActiveIndex = viewId
            onItemSelected?.invoke(viewId)
            onItemSelectedListener?.onItemSelect(viewId)
        } else {
            onItemReselected?.invoke(viewId)
            onItemReselectedListener?.onItemReselect(viewId)
        }
        exploreByTouchHelper.sendEventForVirtualView(viewId, AccessibilityEvent.TYPE_VIEW_CLICKED)
    }

    private fun applyItemActiveIndex() {
        if (items.isEmpty()) return
        val itemBounds = items.map { RectF(it.rect) }
        calculateItemBounds()

        items.forEachIndexed { index, item ->
            animateAlpha(item, if (index == itemActiveIndex) OPAQUE else TRANSPARENT)
        }

        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = itemAnimDuration
            interpolator = DecelerateInterpolator()
            addUpdateListener { anim ->
                val p = anim.animatedValue as Float
                items.forEachIndexed { i, item ->
                    val s = itemBounds[i]
                    val t = item.targetRect
                    item.rect.apply {
                        left = s.left + ((t.left - s.left) * p)
                        top = s.top + ((t.top - s.top) * p)
                        right = s.right + ((t.right - s.right) * p)
                        bottom = s.bottom + ((t.bottom - s.bottom) * p)
                    }
                }
                invalidate()
            }
            start()
        }

        ValueAnimator.ofObject(ArgbEvaluator(), itemIconTint, itemIconTintActive).apply {
            duration = itemAnimDuration
            addUpdateListener { currentIconTint = it.animatedValue as Int }
            start()
        }
    }

    private fun animateAlpha(item: BottomBarItem, to: Int) {
        ValueAnimator.ofInt(item.alpha, to).apply {
            duration = itemAnimDuration
            addUpdateListener {
                item.alpha = it.animatedValue as Int
                invalidate()
            }
            start()
        }
    }

    fun setupWithNavController(menu: Menu, navController: NavController) =
        NavigationComponentHelper.setupWithNavController(menu, this, navController)

    fun setupWithNavController(navController: NavController) {
        setupWithNavController(this.menu, navController)
        Navigation.setViewNavController(this, navController)
    }

    fun setSelectedItem(pos: Int) {
        itemActiveIndex = pos
        try {
            NavigationUI.onNavDestinationSelected(menu[pos], findNavController())
        } catch (_: Exception) {
            // Log or handle
        }
    }

    @Suppress("unused")
    fun setOnItemSelectedListener(listener: (Int) -> Unit) {
        onItemSelectedListener = OnItemSelectedListener { pos ->
            listener(pos)
            true
        }
    }

    @Suppress("unused")
    fun setOnItemReselectedListener(listener: (Int) -> Unit) {
        onItemReselectedListener = OnItemReselectedListener { pos -> listener(pos) }
    }

    companion object {
        private const val INVALID_RES = -1
        private const val DEFAULT_INDICATOR_COLOR = "#2DFFFFFF"
        private const val DEFAULT_TINT = "#C8FFFFFF"
        private const val DEFAULT_ICON_BG_PADDING = 6f

        private const val TOP_LEFT_CORNER = 1
        private const val TOP_RIGHT_CORNER = 2
        private const val BOTTOM_RIGHT_CORNER = 4
        private const val BOTTOM_LEFT_CORNER = 8
        private const val ALL_CORNERS = 15

        private const val DEFAULT_SIDE_MARGIN = 10f
        private const val DEFAULT_ITEM_PADDING = 10f
        private const val DEFAULT_ITEM_SPACING = 8f
        private const val DEFAULT_ANIM_DURATION = 200L
        private const val DEFAULT_ICON_SIZE = 18F
        private const val DEFAULT_ICON_MARGIN = 4F
        private const val DEFAULT_TEXT_SIZE = 11F
        private const val DEFAULT_CORNER_RADIUS = 20F
        private const val DEFAULT_BAR_CORNER_RADIUS = 0F
        private const val DEFAULT_BAR_CORNERS = TOP_LEFT_CORNER or TOP_RIGHT_CORNER

        private const val OPAQUE = 255
        private const val TRANSPARENT = 0
    }
}