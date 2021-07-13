package org.xpathqs.log.style

object StyleFactory {
    const val SELECTOR_NAME = "selector_name"
    const val XPATH = "xpath"
    const val KEYWORD = "keyword"
    const val ARG = "arg"
    const val TEST_TITLE = "test_title"

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
}