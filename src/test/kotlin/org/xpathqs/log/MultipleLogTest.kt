package org.xpathqs.log

import org.junit.jupiter.api.Test
import org.xpathqs.log.printers.StreamLogPrinter
import org.xpathqs.log.restrictions.RestrictionRule
import org.xpathqs.log.restrictions.value.IncludeTags
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class MultipleLogTest {
    var baos1 = ByteArrayOutputStream()
    var baos2 = ByteArrayOutputStream()

    @Test
    fun test1() {
        val log1 = Logger(
            streamPrinter = StreamLogPrinter(
                writer = PrintStream(baos1)
            )
        )
        val log2 = Logger(
            streamPrinter =
                StreamLogPrinter(
                    writer = PrintStream(baos2)
                ),
            restrictions = listOf(
                RestrictionRule(
                    IncludeTags( "debug")
                )
            )
        )
        var log = BaseLogger(
            arrayListOf(log1, log2)
        )

        log.info("test1")
        log.debug("test2")

        println(baos1.toString())
        println()
        println()
        println(baos2.toString())
    }

}