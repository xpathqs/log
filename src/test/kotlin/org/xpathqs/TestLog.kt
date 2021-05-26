package org.xpathqs

import org.xpathqs.log.TcLogger
import org.xpathqs.log.annotations.LoggerBridge

@LoggerBridge
object TestLog {
    var log: TcLogger = TcLogger()

    fun info(msg: String) {
        log.info(msg)
    }

    fun trace(msg: String) {
        log.trace(msg)
    }

    fun debug(msg: String) {
        log.debug(msg)
    }
}