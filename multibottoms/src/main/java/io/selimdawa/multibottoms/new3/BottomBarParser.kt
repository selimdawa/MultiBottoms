package io.selimdawa.multibottoms.new3

import android.content.Context
import android.content.res.XmlResourceParser
import androidx.annotation.XmlRes
import androidx.core.content.ContextCompat

internal class BottomBarParser(private val context: Context, @XmlRes res: Int) {

    private val parser: XmlResourceParser = context.resources.getXml(res)

    fun parse(): List<BottomBarItem> = buildList {
        var eventType: Int
        do {
            eventType = parser.next()
            if (eventType == XmlResourceParser.START_TAG && parser.name == ITEM_TAG) {
                add(getTabConfig(parser))
            }
        } while (eventType != XmlResourceParser.END_DOCUMENT)
    }

    private fun getTabConfig(parser: XmlResourceParser): BottomBarItem {
        var itemText = ""
        var itemDrawableRes = 0
        var contentDescription = ""

        for (i in 0 until parser.attributeCount) {
            when (parser.getAttributeName(i)) {
                ICON_ATTRIBUTE -> itemDrawableRes = parser.getAttributeResourceValue(i, 0)
                TITLE_ATTRIBUTE -> {
                    val resId = parser.getAttributeResourceValue(i, 0)
                    itemText =
                        if (resId != 0) context.getString(resId) else parser.getAttributeValue(i)
                }

                CONTENT_DESCRIPTION_ATTRIBUTE -> {
                    val resId = parser.getAttributeResourceValue(i, 0)
                    contentDescription =
                        if (resId != 0) context.getString(resId) else parser.getAttributeValue(i)
                }
            }
        }

        val drawable = ContextCompat.getDrawable(context, itemDrawableRes)
            ?: throw IllegalArgumentException("Item icon cannot be null!")

        return BottomBarItem(
            title = itemText,
            contentDescription = contentDescription.ifEmpty { itemText },
            icon = drawable
        )
    }

    companion object {
        private const val ITEM_TAG = "item"
        private const val ICON_ATTRIBUTE = "icon"
        private const val TITLE_ATTRIBUTE = "title"
        private const val CONTENT_DESCRIPTION_ATTRIBUTE = "contentDescription"
    }
}