package org.xpathqs.log.style

import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute

data class Style(
    val textColor: Int? = null,
    val backgroundColor: Int? = null,
    val bold: Boolean = false,
    val italic: Boolean = false,
    val underline: Boolean = false
) {
    val attributes: Array<Attribute>
        get() {
            val attrs = ArrayList<Attribute>()

            if(textColor != null) {
                attrs.add(Attribute.TEXT_COLOR(textColor))
            }
            if(backgroundColor != null) {
                attrs.add(Attribute.BACK_COLOR(backgroundColor))
            }
            if(bold) {
                attrs.add(Attribute.BOLD())
            }
            if(underline) {
                attrs.add(Attribute.UNDERLINE())
            }
            if(italic) {
                attrs.add(Attribute.ITALIC())
            }
            return attrs.toTypedArray()
        }

    fun apply(source: String, default: Style? = null): String {
        val attrs = ArrayList<Attribute>()
        if(default != null) {
            attrs.addAll(default.attributes)
        }
        attrs.addAll(attributes)
        return Ansi.colorize(source, *attrs.toTypedArray())
    }
}
