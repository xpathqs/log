package org.xpathqs.log.style

operator fun String.plus(s: StyledBlock)
    = StyledString(
        arrayListOf(
            StyledBlock(this),
            s
        )
    )

fun String.toStyledString(): StyledString {
    return StyledString(this)
}
