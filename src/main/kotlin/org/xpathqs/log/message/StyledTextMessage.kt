package org.xpathqs.log.message

import org.xpathqs.log.style.Style
import org.xpathqs.log.style.StyledString
import org.xpathqs.log.style.Stylesheet

class StyledTextMessage(
    private val msg: StyledString,
    var stylesheet: Stylesheet,
    var defaultStyle: Style? = null
) : Message()
{
    override val attributes: LogAttributes
        get() = HashMap()

    override val body: String
        get() = msg.toString(stylesheet, defaultStyle)

    override fun toString() = msg.toString()

    fun wrapString(str: String) = StyledTextMessage(
        StyledString(str),
        stylesheet,
        defaultStyle
    ).body
}