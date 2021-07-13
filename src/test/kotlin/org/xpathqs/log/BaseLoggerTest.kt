package org.xpathqs.log

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.xpathqs.SomeClass
import org.xpathqs.TestLog
import org.xpathqs.log.abstracts.ILogRestrictions
import org.xpathqs.log.message.NullMessage
import org.xpathqs.log.printers.StreamLogPrinter
import org.xpathqs.log.printers.args.NoArgsProcessor
import org.xpathqs.log.printers.body.BodyProcessorImpl
import org.xpathqs.log.printers.body.HierarchyBodyProcessor
import org.xpathqs.log.restrictions.NoRestrictions
import org.xpathqs.log.restrictions.source.ForAllSource
import org.xpathqs.log.restrictions.value.IncludeTags
import org.xpathqs.log.restrictions.RestrictionRule
import org.xpathqs.log.restrictions.source.ExcludePackage
import org.xpathqs.log.restrictions.source.IncludePackage
import org.xpathqs.log.restrictions.value.LogLevelLessThan
import org.xpathqs.log.restrictions.value.NoRestrictValues
import java.io.ByteArrayOutputStream
import java.io.PrintStream


internal class BaseLoggerTest {

    var log = BaseLogger()
    var baos = ByteArrayOutputStream()

    @BeforeEach
    fun beforeEach() {
        log = BaseLogger()
        baos = ByteArrayOutputStream()
    }

    @Test
    fun init() {
        assertAll {
            assertThat(log.root.body)
                .isEmpty()

            assertThat(log.root.attributes.size)
                .isEqualTo(0)

            assertThat(log.root.parent)
                .isInstanceOf(NullMessage::class)

            assertThat(log.root.parent.messages)
                .hasSize(0)
        }
    }

    @Test
    fun addMsg() {
        val log = BaseLogger()

        log.info("test")

        val msgs = log.root.messages
        assertAll {
            assertThat(msgs)
                .hasSize(1)

            val msg = msgs.first()
            assertThat(msg.body)
                .isEqualTo("test")
        }
    }

    @Test
    fun addCoupleMsg() {
        log.info("test1")
        log.info("test2")

        val msgs = log.root.messages
        assertAll {
            assertThat(msgs)
                .hasSize(2)

            val msg1 = msgs.first()
            val msg2 = msgs.last()

            assertThat(msg1.body)
                .isEqualTo("test1")

            assertThat(msg2.body)
                .isEqualTo("test2")
        }
    }

    @Test
    fun addAction() {
        log.info("test1")
        log.action("test2") {
            log.info("test3")
        }

        val msgs = log.root.messages
        assertThat(msgs)
            .hasSize(2)
    }

    @Test
    fun test1() {
        var log = getLog()
        log.action("test") {
            log.action("test1") {
                log.info("hellow_1_1")
                log.debug("hellow_1_2")
                log.debug("hellow_1_3")
                log.action("test2") {
                    log.info("hellow_2_1")
                    log.debug("hellow_2_2")
                    log.debug("hellow_2_3")
                    log.action("test3") {
                        log.info("hellow_2_1")
                        log.debug("hellow_2_2")
                        log.debug("hellow_2_3")
                    }
                }
                log.debug("hellow_1_4")
                log.trace("hellow_1_5")
            }
            log.debug("end of all")
        }

        println(baos.toString())
        log.root
    }

    @Test
    fun restrictionIncludeTagForAll() {
        var log = getLog(
            RestrictionRule(
                IncludeTags( "debug")
            )
        )

        log.info("test1")
        log.info("test2")
        log.debug("test3")
        log.trace("test4")

        assertThat(baos.toString())
            .isEqualTo(" test3\n")
    }

    @Test
    fun restrictionLogLevel() {
        var log = getLog(
            RestrictionRule(
                LogLevelLessThan(1)
            )
        )

        log.action("test1") {
            log.info("test2")
        }

        println(baos.toString())

        assertThat(baos.toString())
            .isEqualTo(" test1\n")
    }

    @Test
    fun hierarchyOut() {
        var log = getLog()

        log.info("test1")
        log.action("test2") {
            log.trace("test3")
        }

        println(baos)

        assertThat(baos.toString())
            .isEqualTo(
                " test1\n" +
                      " test2\n" +
                      "     test3\n"
            )
    }

    @Test
    fun restrictionIncludeTwoTagForAll() {
        var log = getLog(
            RestrictionRule(
                IncludeTags( "debug", "trace")
            )
        )

        log.info("test1")
        log.info("test2")
        log.debug("test3")
        log.trace("test4")

        assertThat(baos.toString())
            .isEqualTo(" test3\n test4\n")
    }

    @Test
    fun restrictionIncludeTestPackage() {
        getLog(
            RestrictionRule(
                NoRestrictValues(),
                IncludePackage("org.xpathqs.log")
            )
        )

        TestLog.debug("test 1")
        TestLog.info("test 2")

        val cls = SomeClass()
        cls.someLog1()
        cls.someLog2()

        assertThat(baos.toString())
            .isEqualTo(" test 1\n test 2\n")
    }

    @Test
    fun restrictionExcludeTestPackage() {
        getLog(
            RestrictionRule(
                NoRestrictValues(),
                ExcludePackage("org.xpathqs.log")
            )
        )

        TestLog.debug("test 1")
        TestLog.info("test 2")

        val cls = SomeClass()
        cls.someLog1()

        assertThat(baos.toString())
            .isEqualTo(" debug1\n trace2\n")
    }

    private fun getLog(
        restrictions: ILogRestrictions = NoRestrictions()
    ): BaseLogger {
        val res = BaseLogger(
            Logger(
                streamPrinter = StreamLogPrinter(
                    argsProcessor =
                        NoArgsProcessor(),
                    bodyProcessor =
                        HierarchyBodyProcessor(
                            BodyProcessorImpl()
                        ),
                    writer = PrintStream(baos)
                ),
                restrictions = restrictions
            )
        )

        TestLog.log = res
        return res
    }
}