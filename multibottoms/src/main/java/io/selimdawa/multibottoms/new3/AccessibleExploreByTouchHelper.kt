package io.selimdawa.multibottoms.new3

import android.graphics.Rect
import android.os.Bundle
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.customview.widget.ExploreByTouchHelper
import kotlin.math.roundToInt

class AccessibleExploreByTouchHelper(
    private val host: SmoothBottomBar,
    private val onClickAction: (id: Int) -> Unit
) : ExploreByTouchHelper(host) {

    override fun getVisibleVirtualViews(virtualViewIds: MutableList<Int>) {
        for (i in host.items.indices) {
            virtualViewIds.add(i)
        }
    }

    override fun getVirtualViewAt(x: Float, y: Float): Int {
        host.items.forEachIndexed { index, item ->
            if (item.rect.contains(x, y)) return index
        }
        return INVALID_ID
    }

    override fun onPopulateNodeForVirtualView(
        virtualViewId: Int,
        node: AccessibilityNodeInfoCompat
    ) {
        val item = host.items[virtualViewId]
        node.className = BottomBarItem::class.java.name
        node.contentDescription = item.contentDescription
        node.isClickable = true
        node.isFocusable = true
        node.isScreenReaderFocusable = true

        node.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK)
        node.isSelected = host.itemActiveIndex == virtualViewId

        val bounds = Rect(
            item.rect.left.roundToInt(),
            item.rect.top.roundToInt(),
            item.rect.right.roundToInt(),
            item.rect.bottom.roundToInt()
        )
        
        val screenRect = Rect(bounds)
        val location = IntArray(2)
        host.getLocationOnScreen(location)
        screenRect.offset(location[0], location[1])
        
        node.setBoundsInScreen(screenRect)
    }

    override fun onPerformActionForVirtualView(
        virtualViewId: Int,
        action: Int,
        arguments: Bundle?
    ): Boolean {
        if (action == AccessibilityNodeInfoCompat.ACTION_CLICK) {
            onClickAction(virtualViewId)
            return true
        }
        return false
    }
}