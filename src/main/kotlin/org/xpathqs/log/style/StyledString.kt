package org.xpathqs.log.style

import org.xpathqs.log.printers.body.StyledStringParser

typealias Stylesheet = HashMap<String, Style>

class StyledString(
    private val strings: ArrayList<StyledBlock>
) {
    constructor(): this(ArrayList())
    constructor(vararg blocks: StyledBlock): this(arrayListOf(*blocks))
    constructor(s: StyledBlock): this(arrayListOf(s))
    constructor(s: String): this(StyledBlock(s))

    operator fun plus(s: String): StyledString {
        strings.add(StyledBlock(s))
        return this
    }

    operator fun plus(s: StyledBlock): StyledString {
        strings.add(s)
        return this
    }

    operator fun plus(s: StyledString): StyledString {
        strings.addAll(s.strings)
        return this
    }

    override fun toString(): String
        = strings.joinToString("")

    fun toString(
        sheet: Stylesheet,
        default: Style? = null
    ): String {
        val res = StringBuilder()
        strings.forEach {
            val style = sheet[it.style]
            if(it.style.isNotEmpty() && style != null) {
                res.append(style.apply(it.value, default))
            }
            else {
                if(default != null) {
                    res.append(
                        default.apply(it.value)
                    )
                } else {
                    res.append(it.value)
                }
            }
        }
        return res.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StyledString

        if (strings != other.strings) return false

        return true
    }

    override fun hashCode(): Int {
        return strings.hashCode()
    }

    companion object {
        val defaultStyles = Stylesheet().apply {
            this[StyleFactory.SELECTOR_NAME] = Style(textColor = 37, underline = true)
            this[StyleFactory.KEYWORD] = Style(bold = true)
            this[StyleFactory.XPATH] = Style(textColor = 241)
            this[StyleFactory.ARG] = Style(textColor = 202)
            this[StyleFactory.TEST_TITLE] = Style(textColor = 117, backgroundColor = 60)
        }

        fun fromFormatString(str: String, style: String, vararg args: String) =
            StyledStringParser(str, arrayListOf(style), arrayListOf(*args))
                .parse()

        fun fromFormatString(str: String, styles: Collection<String>, vararg args: String) =
            StyledStringParser(str, styles, arrayListOf(*args))
                .parse()

        fun fromDefaultFormatString(str: String, vararg args: String) =
            StyledStringParser(str, defaultStyles.keys, arrayListOf(*args))
                .parse()

        fun fromFormatString(str: String, styles: Stylesheet, vararg args: String) =
            StyledStringParser(str, styles.keys, arrayListOf(*args))
                .parse()
    }


}
