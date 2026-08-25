package io.selimdawa.multibottoms.new2

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.os.BundleCompat
import androidx.core.util.size
import io.selimdawa.multibottoms.R
import io.selimdawa.multibottoms.new2.listener.BubbleNavigationChangeListener

class BubbleNavigationConstraintView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), View.OnClickListener, IBubbleNavigation {

    enum class DisplayMode { SPREAD, INSIDE, PACKED }

    private var bubbleNavItems: MutableList<BubbleToggleView>? = null
    private var navigationChangeListener: BubbleNavigationChangeListener? = null
    private var currentActiveItemPosition = 0
    private var loadPreviousState = false
    private var displayMode = DisplayMode.SPREAD
    private var currentTypeface: Typeface? = null
    private var pendingBadgeUpdate: SparseArray<String>? = null

    init {
        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.BubbleNavigationConstraintView, 0, 0)
            try {
                val mode = ta.getInteger(R.styleable.BubbleNavigationConstraintView_bnc_mode, 0)
                if (mode in DisplayMode.entries.indices) {
                    displayMode = DisplayMode.entries[mode]
                }
            } finally {
                ta.recycle()
            }
        }
        post { updateChildNavItems() }
    }

    override fun onSaveInstanceState(): Parcelable {
        return Bundle().apply {
            putParcelable("superState", super.onSaveInstanceState())
            putInt("current_item", currentActiveItemPosition)
            putBoolean("load_prev_state", true)
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var superState = state
        if (state is Bundle) {
            currentActiveItemPosition = state.getInt("current_item")
            loadPreviousState = state.getBoolean("load_prev_state")
            superState = BundleCompat.getParcelable(state, "superState", Parcelable::class.java)
        }
        super.onRestoreInstanceState(superState)
    }

    private fun getChainTypeFromMode(mode: DisplayMode): Int = when (mode) {
        DisplayMode.SPREAD -> ConstraintSet.CHAIN_SPREAD
        DisplayMode.INSIDE -> ConstraintSet.CHAIN_SPREAD_INSIDE
        DisplayMode.PACKED -> ConstraintSet.CHAIN_PACKED
    }

    private fun updateChildNavItems() {
        val items = mutableListOf<BubbleToggleView>()
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is BubbleToggleView) {
                items.add(view)
            } else {
                Log.w(TAG, "Cannot have child bubbleNavItems other than BubbleToggleView")
                return
            }
        }

        if (items.size < MIN_ITEMS) {
            Log.w(TAG, "The bubbleNavItems list should have at least $MIN_ITEMS items")
        } else if (items.size > MAX_ITEMS) {
            Log.w(TAG, "The bubbleNavItems list should not have more than $MAX_ITEMS items")
        }

        bubbleNavItems = items
        setClickListenerForItems()
        setInitialActiveState()
        updateMeasurementForItems()
        createChains()

        currentTypeface?.let { setTypeface(it) }

        pendingBadgeUpdate?.let { pending ->
            for (i in 0 until pending.size) {
                setBadgeValue(pending.keyAt(i), pending.valueAt(i))
            }
            pending.clear()
        }
    }

    private fun createChains() {
        val items = bubbleNavItems ?: return
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)

        val chainIdsList = IntArray(items.size)
        val chainWeightList = FloatArray(items.size)

        items.forEachIndexed { i, item ->
            val id = item.id
            chainIdsList[i] = id
            chainWeightList[i] = 0.0f
            constraintSet.connect(id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)
            constraintSet.connect(id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)
        }

        constraintSet.createHorizontalChain(
            id, ConstraintSet.LEFT,
            id, ConstraintSet.RIGHT,
            chainIdsList, chainWeightList,
            getChainTypeFromMode(displayMode)
        )
        constraintSet.applyTo(this)
    }

    private fun setInitialActiveState() {
        val items = bubbleNavItems ?: return
        var foundActiveElement = false

        if (!loadPreviousState) {
            items.forEachIndexed { i, item ->
                if (item.isActive() && !foundActiveElement) {
                    foundActiveElement = true
                    currentActiveItemPosition = i
                } else {
                    item.setInitialState(false)
                }
            }
        } else {
            items.forEach { it.setInitialState(false) }
        }

        if (!foundActiveElement && currentActiveItemPosition < items.size) {
            items[currentActiveItemPosition].setInitialState(true)
        }
    }

    private fun updateMeasurementForItems() {
        val items = bubbleNavItems ?: return
        if (items.isNotEmpty()) {
            val calculatedEachItemWidth = (measuredWidth - (paddingRight + paddingLeft)) / items.size
            items.forEach { it.updateMeasurements(calculatedEachItemWidth) }
        }
    }

    private fun setClickListenerForItems() {
        bubbleNavItems?.forEach { it.setOnClickListener(this) }
    }

    private fun getItemPositionById(id: Int): Int {
        return bubbleNavItems?.indexOfFirst { it.id == id } ?: -1
    }

    override fun setNavigationChangeListener(navigationChangeListener: BubbleNavigationChangeListener?) {
        this.navigationChangeListener = navigationChangeListener
    }

    override fun setTypeface(typeface: Typeface?) {
        bubbleNavItems?.forEach { it.setTitleTypeface(typeface) } ?: run { currentTypeface = typeface }
    }

    override fun getCurrentActiveItemPosition() = currentActiveItemPosition

    override fun setCurrentActiveItem(position: Int) {
        val items = bubbleNavItems ?: run {
            currentActiveItemPosition = position
            return
        }
        if (currentActiveItemPosition == position) return
        if (position in items.indices) {
            items[position].performClick()
        }
    }

    override fun setBadgeValue(position: Int, value: String?) {
        bubbleNavItems?.let { items ->
            if (position in items.indices) {
                items[position].setBadgeText(value)
            }
        } ?: run {
            if (pendingBadgeUpdate == null) pendingBadgeUpdate = SparseArray()
            pendingBadgeUpdate?.put(position, value)
        }
    }

    override fun onClick(v: View) {
        val changedPosition = getItemPositionById(v.id)
        val items = bubbleNavItems ?: return
        if (changedPosition >= 0) {
            if (changedPosition == currentActiveItemPosition) return

            items.getOrNull(currentActiveItemPosition)?.toggle()
            items.getOrNull(changedPosition)?.toggle()
            currentActiveItemPosition = changedPosition

            navigationChangeListener?.onNavigationChanged(v, currentActiveItemPosition)
        } else {
            Log.w(TAG, "Selected id not found! Cannot toggle")
        }
    }

    companion object {
        private const val TAG = "BNCView"
        private const val MIN_ITEMS = 2
        private const val MAX_ITEMS = 5
    }
}
