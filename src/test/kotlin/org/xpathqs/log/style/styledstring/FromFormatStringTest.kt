package org.xpathqs.log.style.styledstring

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.xpathqs.log.style.StyleFactory
import org.xpathqs.log.style.StyledBlock
import org.xpathqs.log.style.StyledString

internal class FromFormatStringTest {

    @Test
    fun blankString() {
        assertThat(
            StyledString.fromFormatString("", "")
        ).isEqualTo(
            StyledString()
        )
    }

    @Test
    fun styleStringWithOneArg() {
        assertThat(
            StyledString.fromFormatString(
                "hey %${StyleFactory.SELECTOR_NAME}, there",
                StyleFactory.SELECTOR_NAME,
                "Form.s1"
            )
        ).isEqualTo(
            StyledString(
                arrayListOf(
                    StyledBlock("hey "),
                    StyledBlock(
                        "Form.s1",
                        StyleFactory.SELECTOR_NAME
                    ),
                    StyledBlock(", there")
                )
            )
        )
    }

    @Test
    fun styleStringWithTwoArg() {
        assertThat(
            StyledString.fromFormatString(
                "hey %${StyleFactory.SELECTOR_NAME}, there %${StyleFactory.SELECTOR_NAME}",
                StyleFactory.SELECTOR_NAME,
                "Form.s1",
                "Form.s2"
            )
        ).isEqualTo(
            StyledString(
                arrayListOf(
                    StyledBlock("hey "),
                    StyledBlock(
                        "Form.s1",
                        StyleFactory.SELECTOR_NAME
                    ),
                    StyledBlock(", there "),
                    StyledBlock(
                        "Form.s2",
                        StyleFactory.SELECTOR_NAME
                    )
                )
            )
        )
    }

    @Test
    fun styleStringWithDifferentStyles() {
        assertThat(
            StyledString.fromFormatString(
                "hey %${StyleFactory.SELECTOR_NAME}, there %${StyleFactory.ARG}",
                arrayListOf(
                    StyleFactory.SELECTOR_NAME,
                    StyleFactory.ARG
                ),
                "Form.s1",
                "Form.s2"
            )
        ).isEqualTo(
            StyledString(
                arrayListOf(
                    StyledBlock("hey "),
                    StyledBlock(
                        "Form.s1",
                        StyleFactory.SELECTOR_NAME
                    ),
                    StyledBlock(", there "),
                    StyledBlock(
                        "Form.s2",
                        StyleFactory.ARG
                    )
                )
            )
        )
    }
}