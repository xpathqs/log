package org.xpathqs.log.style

data class StyledBlock(
    val value: String,
    val style: String = ""
) {
    operator fun plus(s: String): StyledString
        = plus(StyledBlock(s))

    operator fun plus(s: StyledBlock): StyledString
            = StyledString(arrayListOf(this, s))

    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StyledBlock

        if (value != other.value) return false
        if (style != other.style) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + style.hashCode()
        return result
    }

}
