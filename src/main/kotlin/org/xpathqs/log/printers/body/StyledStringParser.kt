package org.xpathqs.log.printers.body

import org.xpathqs.log.style.StyledBlock
import org.xpathqs.log.style.StyledString

internal class StyledStringParser(
    val source: String,
    val styles: Collection<String>,
    val args: ArrayList<String>
) {
    var curStyle = ""
    var prevPos = 0
    var argCounter = 0
    var curStylePos = 0

    fun parse(): StyledString {
        val blocks = ArrayList<StyledBlock>()
        nextStyle()
        while(hasStyleArg()) {
            blocks.add(
                StyledBlock(
                    source.substring(prevPos, curStylePos)
                )
            )
            blocks.add(
                StyledBlock(
                    args[argCounter++],
                    curStyle
                )
            )

            prevPos = curStylePos + curStyle.length + 1
            nextStyle()

            if(!hasStyleArg() && prevPos < source.length) {
                blocks.add(
                    StyledBlock(
                        source.substring(prevPos, source.length)
                    )
                )
            }
        }

        return StyledString(blocks)
    }

    fun nextStyle() {
        val next = findNextStyle()
        if(next != null) {
            val styleArg = "%$next"
            val pos = source.indexOf(styleArg, prevPos)
            if(pos >= 0) {
                curStyle = next
                curStylePos = pos
                return
            }
        }
        curStylePos = -1
    }

    fun findNextStyle() =
        styles.sortedBy {
            val index = source.indexOf("%$it", prevPos)
            if(index == -1) Integer.MAX_VALUE else index
        }.firstOrNull()

    fun hasStyleArg() = curStylePos >= 0
}