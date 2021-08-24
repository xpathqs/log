package org.xpathqs.log.style

object StyleFactory {
    const val SELECTOR_NAME = "selector_name"
    const val XPATH = "xpath"
    const val KEYWORD = "keyword"
    const val ARG = "arg"
    const val TEST_TITLE = "test_title"
    const val ERROR = "error"
    const val WARNING = "warning"
    const val RESULT = "result"

    fun selectorName(s: String): StyledBlock
        = StyledBlock(s, SELECTOR_NAME)

    fun xpath(s: String): StyledBlock
            = StyledBlock(s, XPATH)

    fun text(s: String): StyledBlock
        = StyledBlock(s)

    fun keyword(s: String): StyledBlock
            = StyledBlock(s, KEYWORD)

    fun arg(s: String): StyledBlock
            = StyledBlock(s, ARG)

    fun testTitle(s: String): StyledBlock
            = StyledBlock(s, TEST_TITLE)

    fun error(s: String): StyledBlock
            = StyledBlock(s, ERROR)

    fun warning(s: String): StyledBlock
            = StyledBlock(s, WARNING)

    fun result(s: String): StyledBlock
            = StyledBlock(s, RESULT)
}