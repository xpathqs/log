package org.xpathqs.log

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.xpathqs.log.message.NullMessage
import org.xpathqs.log.printers.NoLogPrinter


internal class TcLoggerTest {

    var log = TcLogger()

    @BeforeEach
    fun beforeEach() {
        log = TcLogger()
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
        log.info("test")

        val msgs = log.root.messages
        assertAll {
            assertThat(msgs)
                .hasSize(1)

            val msg = msgs.first()
            assertThat(msg.body)
                .isEqualTo("test")

            assertThat(msg.attributes)
                .hasSize(4)
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
        var log = TcLogger()

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

        log.root
    }
}